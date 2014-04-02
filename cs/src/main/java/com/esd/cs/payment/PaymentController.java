/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.payment;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.hesf.model.Accounts;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.PaymentExceptional;
import com.esd.hesf.model.PaymentType;
import com.esd.hesf.model.User;
import com.esd.hesf.service.AccountsService;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.PaymentExceptionalService;
import com.esd.hesf.service.PaymentService;
import com.esd.hesf.service.PaymentTypeService;
import com.esd.hesf.service.UserService;

@Controller
@RequestMapping(value = "/security/payment")
public class PaymentController {
	private static final Logger logger = LoggerFactory
			.getLogger(PaymentController.class);
	@Autowired
	private AuditService auditService;
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentTypeService paymentTypeService;
	@Autowired
	private AuditProcessStatusService auditProcessStatusService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private PaymentExceptionalService paymentExceptionalService;
	@Autowired
	private AuditParameterService auditParameterService;

	private DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * 转到企业账目列表页面
	 */
	@RequestMapping(value = "/list/{process}", method = RequestMethod.GET)
	public ModelAndView initAudit_list(
			@PathVariable(value = "process") Integer process,
			HttpServletRequest request, HttpSession session) {
		// String nowYear = (String) session.getAttribute(Constants.YEAR);
		String nowYear = CalendarUtil.getNowYear();
		request.setAttribute("nowYear", nowYear);
		request.setAttribute("process", process);
		return new ModelAndView("payment/payment_list");
	}

	/**
	 * 跳转到 缴款 页面
	 * 
	 * @param id
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{year}/{companyId}", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable(value = "year") String year,
			@PathVariable(value = "companyId") Integer companyId,
			HttpServletRequest request) {
		// 根据公司id和账目年限, 获得综合的账目对象(其中的id不准确-不要使用)
		Accounts accounts = accountsService
				.getByYearAndCompany(year, companyId);
		// 根据公司id和账目年限,获得已缴款总额
		BigDecimal alreadyPayment = paymentService.getEffPaid(year, companyId);
		if (alreadyPayment == null) {
			alreadyPayment = new BigDecimal("0.00");
		}
		Audit audit = accounts.getAudit();
		AuditParameter auditParameter = auditParameterService.getByYear(audit
				.getYear());
		Map<String, String> entity = new HashMap<String, String>();
		// 账目id
		// entity.put("id", String.valueOf(accounts.getId()));
		entity.put("companyId", String.valueOf(accounts.getCompany().getId()));
		entity.put("companyCode", accounts.getCompany().getCompanyCode());
		entity.put("companyTaxCode", accounts.getCompany().getCompanyTaxCode());
		entity.put("companyName", accounts.getCompany().getCompanyName());
		entity.put("year", accounts.getYear()); // 缴款年度
		entity.put("payAmount", accounts.getTotalMoney().toString()); // 应交款总数
		entity.put("alreadyPayAmount", alreadyPayment.toString()); // 已缴款总数
		entity.put("lessPayAmount",
				(accounts.getTotalMoney().subtract(alreadyPayment)).toString()); // 欠缴金额
		// entity.put("amountPayable", df.format(audit.getAmountPayable()));
		// entity.put("remainAmount", df.format(audit.getRemainAmount()));
		// entity.put("reductionAmount", df.format(audit.getReductionAmount()));
		// entity.put("actualAmount", df.format(audit.getActualAmount()));
		// entity.put("payAmount", df.format(audit.getPayAmount()));
		// entity.put("delayPayAmount", df.format(audit.getDelayPayAmount()));
		entity.put("remark", audit.getRemark());
		entity.put("companyEmpTotal",
				String.valueOf(audit.getCompanyEmpTotal()));
		entity.put("averageSalary",
				df.format(auditParameter.getAverageSalary()));
		entity.put("companyShouldTotal",
				String.valueOf(audit.getCompanyShouldTotal()));
		entity.put("companyAlreadyTotal",
				String.valueOf(audit.getCompanyAlreadyTotal()));
		entity.put("companyHandicapTotal",
				String.valueOf(audit.getCompanyHandicapTotal()));
		entity.put("companyPredictTotal",
				String.valueOf(audit.getCompanyPredictTotal()));
		return new ModelAndView("payment/payment_detail", "entity", entity);
	}

	@RequestMapping(value = "/pay/{id}", method = RequestMethod.GET)
	public ModelAndView payGet(@PathVariable(value = "id") Integer id) {
		logger.debug("paymentId:{}", id);
		Payment payment = paymentService.getByPrimaryKey(id);
		return new ModelAndView("payment/payment_detail_add", "entity", payment);
	}

	/**
	 * 获取确认
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/confirm/{id}", method = RequestMethod.GET)
	public ModelAndView confirmGet(@PathVariable(value = "id") Integer id,
			HttpSession session) {
		logger.debug("payment.id:{}", id);
		Payment payment = paymentService.getByPrimaryKey(id);
		return new ModelAndView("payment/payment_detail_confirm", "entity",
				payment);
	}

	// /**
	// * 获取有效的支付金额
	// *
	// * @return
	// */
	// private BigDecimal getEffPaid(String year, Integer companyId) {
	// PaginationRecordsAndNumber<Payment, Number> query = paymentService
	// .getPaymentRecords(year, companyId, 1, Integer.MAX_VALUE);
	// // 把以回单的费用相加获取所有已缴的金额
	// BigDecimal bd = new BigDecimal(0.00);
	// for (Payment pt : query.getRecords()) {
	// if (pt.getBillObsolete() == Boolean.TRUE) {
	// continue;
	// }
	// if (pt.getBillReturn() == Boolean.TRUE) {
	// bd = bd.add(pt.getPaymentMoney());
	// }
	// }
	// return bd;
	// }

	// /**
	// * 获得支付记录总和
	// *
	// * @param payment
	// * @return
	// */
	// private BigDecimal getTotalMoney(Payment payment) {
	// String year = payment.getYear();
	// Integer companyId = payment.getPaymentCompany().getId();
	// List<Accounts> query = accountsService.getCompanyAccount(year,
	// companyId);
	// BigDecimal bd = new BigDecimal(0.00);
	// for (Accounts at : query) {
	// bd = bd.add(at.getTotalMoney());
	// }
	// return bd;
	// }

	/**
	 * 批量更新状态
	 * 
	 * @param payment
	 * @return
	 */
	private Boolean batchUpdateAuditStatus(Payment payment,
			Integer auditProcessStatus) {
		AuditProcessStatus aup = auditProcessStatusService
				.getByPrimaryKey(auditProcessStatus);
		String year = payment.getYear();
		Integer companyId = payment.getPaymentCompany().getId();
		List<Accounts> query = accountsService.getCompanyAccount(year,
				companyId);
		try {
			for (Accounts at : query) {
				// 更新审核表 即时审核状态,
				Audit audit = auditService.getByPrimaryKey(at.getAudit()
						.getId());
				audit.setAuditProcessStatus(aup);
				// 补缴年份
				audit.setSupplementYear(payment.getYear());
				auditService.update(audit);
				// 更新账目 即时审核状态
				at.setAuditProcessStatus(aup);
				accountsService.update(at);
			}
		} catch (Exception e) {
			logger.error("{}", e);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	// private Boolean batchUpdateAttachmentAuditStatus(Integer companyId,
	// String year, Integer status) {
	// AuditProcessStatus auditProcessStatus = auditProcessStatusService
	// .getByPrimaryKey(status);
	// List<Audit> list = new ArrayList<Audit>();
	// for (Audit a : list) {
	// // a.setPayAmount(new BigDecimal(0));// 设置实缴总金额为0
	// a.setAuditProcessStatus(auditProcessStatus);// 设置状态
	// a.setSupplementYear(year);// 设置补审年度
	// auditService.update(a);
	// }
	// return Boolean.TRUE;
	// }

	/**
	 * 确认缴款信息（返票--为true）
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ResponseBody
	public Boolean confirmPost(Payment payment, HttpSession session) {
		logger.debug(payment.toString());
		Payment queryPayment = paymentService.getByPrimaryKey(payment.getId());
		queryPayment.setBillExchangeDate(payment.getBillExchangeDate());// 回票时间
		queryPayment.setBillReturn(payment.getBillReturn());// 返票
		queryPayment.setBillFinance(payment.getBillFinance());// 财务
		queryPayment.setPaymentExceptional(payment.getPaymentExceptional());// 特殊情况
		queryPayment.setBillObsolete(payment.getBillObsolete());// 作费票据
		queryPayment.setRemark(payment.getRemark());// 备注
		Boolean b = paymentService.update(queryPayment);
		// 获取此缴款对应公司, 该缴款年度应缴金额总额
		Accounts accounts = accountsService.getByYearAndCompany(queryPayment
				.getYear(), queryPayment.getPaymentCompany().getId());
		BigDecimal paymentAmount = new BigDecimal("0.00");
		if (accounts != null) {
			paymentAmount = accounts.getTotalMoney();
		}
		// 获取此缴款对应公司, 该缴款年度实缴总金额(已回单的)
		// 把以回单的费用相加 = 所有已缴的金额
		BigDecimal alreadyPayments = paymentService.getEffPaid(queryPayment
				.getYear(), queryPayment.getPaymentCompany().getId());
		// 获取应缴金额和已缴对比相等 则修改状态为 已缴款
		// BigDecimal totalMoney = getTotalMoney(queryPayment);
		if (alreadyPayments.compareTo(paymentAmount) == 0) {
			// 批量更新相关联的审核表和账目表所处的 即时审核状态
			batchUpdateAuditStatus(queryPayment, Constants.PROCESS_STATIC_YJK);
			// // 更新,补缴年度,未审。未缴。部分缴款
			// batchUpdateAttachmentAuditStatus(queryPayment.getPaymentCompany()
			// .getId(), queryPayment.getYear(),
			// Constants.PROCESS_STATIC_YJK);
		} else {
			// 已缴金额大于0 ， 则修改状态为 部分缴款
			if (paymentAmount.compareTo(new BigDecimal("0.00")) > 0) {
				batchUpdateAuditStatus(queryPayment,
						Constants.PROCESS_STATIC_BFJK);
			}
		}

		return b;
	}

	public static void main(String[] args) {
		BigDecimal b1 = new BigDecimal("-0.01");
		BigDecimal b2 = new BigDecimal("0.00");
		System.out.println(b1.compareTo(b2));
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewGet(@PathVariable(value = "id") Integer id,
			HttpSession session) {
		logger.debug("aduitId:{}", id);
		Payment payment = paymentService.getByPrimaryKey(id);
		return new ModelAndView("payment/payment_detail_view", "entity",
				payment);
	}

	/**
	 * 跳转到新建缴款记录页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/add/{year}/{companyId}/{lessPayAmount}", method = RequestMethod.GET)
	public ModelAndView addGet(@PathVariable(value = "year") String year,
			@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "lessPayAmount") BigDecimal lessPayAmount,
			HttpSession session) {
		// 待缴金额(应缴-已缴)
		logger.debug("lessPayAmount{}", lessPayAmount);
		Payment payment = new Payment();
		Company company = companyService.getByPrimaryKey(companyId);
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		// 缴款 出账年份
		payment.setYear(year);
		// 设置缴款人
		payment.setPaymentPerson(user);
		// 设置缴款公司
		payment.setPaymentCompany(company);
		// PaginationRecordsAndNumber<Payment, Number> query = paymentService
		// .getPaymentRecords(audit., companyId, page,
		// pageSize)getPaymentRecordByAudit(id, 1, Integer.MAX_VALUE);
		// BigDecimal readyPayments = paymentService.getEffPaid(
		// accounts.getYear(), accounts.getCompany().getId());
		// if (readyPayments == null) {
		// readyPayments = new BigDecimal("0.00");
		// }
		// for (Payment pt : query.getRecords()) {
		// if (pt.getBillReturn() == Boolean.FALSE) {
		// readyPayments = readyPayments.add(pt.getPaymentMoney());
		// }
		// }
		// payment.setPaymentMoney(accounts.getTotalMoney()
		// .subtract(readyPayments));
		payment.setPaymentMoney(lessPayAmount);
		return new ModelAndView("payment/payment_detail_add", "entity", payment);
	}

	/**
	 * 保存 新建缴款记录
	 * 
	 * @param payment
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean outPost(Payment payment, HttpSession session) {
		logger.debug(payment.toString());
		// 保存缴款记录
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		payment.setUserId(userId);
		payment.setPaymentPerson(user);
		PaymentExceptional paymentExceptional = paymentExceptionalService
				.getByPrimaryKey(payment.getPaymentExceptional().getId());
		payment.setPaymentExceptional(paymentExceptional);
		Boolean b = paymentService.save(payment);
		return b;
	}

	/**
	 * 缴款
	 * 
	 * @param payment
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean confirmPost(Payments payment, HttpSession session) {
		logger.debug(payment.toString());

		// payment.setPaymentDate(payment.getPaymentDate());// 缴款时间
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		// Payment p = paymentService.getByPrimaryKey(payment.getId());
		if (payment.getId() == null) {
			Payment pay = new Payment();
			// pay.setAudit(payment.getAuditId());
			pay.setPaymentBill(payment.getPaymentBill());
			String date = payment.getPaymentDate();
			Date paymentDate = null;
			SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				paymentDate = timeformat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			pay.setPaymentDate(paymentDate);
			pay.setPaymentMoney(new BigDecimal(payment.getPaymentMoney()));
			pay.setPaymentPerson(user);
			PaymentType paymentType = paymentTypeService
					.getByPrimaryKey(Integer.valueOf(payment.getPaymentType()));
			pay.setPaymentType(paymentType);
			pay.setRemark(payment.getRemark());
			pay.setVersion(1);
			Company company = companyService.getByPrimaryKey(payment.getId());
			pay.setPaymentCompany(company);
			if (paymentService.save(pay) == true) {
				Audit audit = auditService
						.getByPrimaryKey(payment.getAuditId());
				AuditProcessStatus auditProcessStatus = auditProcessStatusService
						.getByPrimaryKey(Constants.PROCESS_STATIC_BFJK);
				audit.setAuditProcessStatus(auditProcessStatus);
				auditService.update(audit);
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取已缴款列表
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPayments/{year}/{companyId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPayment(
			@PathVariable(value = "year") String year,
			@PathVariable(value = "companyId") Integer companyId,
			HttpSession session) {
		// 获得账目对象
		Map<String, Object> entity = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> query = null;
		// 4个参数分别为: 缴款年限, 公司id, 起始页, 返回量
		query = paymentService.getPaymentRecords(year, companyId, 1,
				Integer.MAX_VALUE);
		entity.put("total", query.getNumber());
		List<Map<String, Object>> list = new ArrayList<>();
		for (Iterator<Payment> iterator = query.getRecords().iterator(); iterator
				.hasNext();) {
			Payment it = (Payment) iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id", it.getId());
			map.put("billPrintDate",
					CalendarUtil.dateFormat(it.getBillPrintDate()));
			map.put("paymentBill", it.getPaymentBill());
			map.put("paymentMoney", df.format(it.getPaymentMoney()));
			map.put("billExchangeDate",
					CalendarUtil.dateFormat(it.getBillExchangeDate()));
			map.put("billReturn", it.getBillReturn());
			map.put("billFinance", it.getBillFinance());
			map.put("billObsolete", it.getBillObsolete());
			map.put("paymentExceptional", it.getPaymentExceptional()
					.getPaymentExceptional());
			map.put("paymentType", it.getPaymentType().getText());
			map.put("userRealName", it.getPaymentPerson().getUserRealName());
			map.put("remark", it.getRemark());
			list.add(map);
		}
		entity.put("rows", list);
		return entity;
	}

	@RequestMapping(value = "/getBalance/{year}/{companyId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBalance(
			@PathVariable(value = "year") String year,
			@PathVariable(value = "companyId") Integer companyId,
			HttpServletRequest request) {
		// 根据公司id和账目年限, 获得综合的账目对象(其中的id不准确-不要使用)
		Accounts accounts = accountsService
				.getByYearAndCompany(year, companyId);
		// 根据公司id和账目年限,获得已缴款总额
		BigDecimal alreadyPayment = paymentService.getEffPaid(year, companyId);
		if (alreadyPayment == null) {
			alreadyPayment = new BigDecimal("0.00");
		}

		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("alreadyPayAmount", df.format(alreadyPayment));
		entity.put("lessPayAmount",
				df.format(accounts.getTotalMoney().subtract(alreadyPayment)));
		return entity;
	}

	@RequestMapping(value = "/delPayment/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delPayment(@PathVariable(value = "id") Integer id,
			HttpSession session) {
		Boolean b = paymentService.delete(id);
		return b;
	}

	// @RequestMapping(value = "/backAudit/{id}", method = RequestMethod.GET)
	// @ResponseBody
	// public Boolean backAudit(@PathVariable(value = "id") Integer id,
	// HttpSession session) {
	// Audit audit = auditService.getByPrimaryKey(id);
	// PaginationRecordsAndNumber<Payment, Number> query = paymentService
	// .getPaymentRecordByAudit(id, 1, Integer.MAX_VALUE);
	// if (query == null || query.getRecords().size() > 0) {
	// return false;
	// }
	// AuditProcessStatus auditProcessStatus = auditProcessStatusService
	// .getByPrimaryKey(Constants.PROCESS_STATIC_WFS);
	// audit.setAuditProcessStatus(auditProcessStatus);
	// Boolean b = auditService.update(audit);
	// return b;
	// }

	@RequestMapping(value = "/download")
	@ResponseBody
	public String downPaylist(HttpServletRequest request) {
		boolean b = true;
		// 每次导入量
		Integer pageSize = 99999;
		PaginationRecordsAndNumber<Payment, Number> prn = paymentService
				.getPaginationRecords(null, 1, pageSize);
		// 总条数
		int totalCount = (Integer) prn.getNumber();
		// 总页数
		int totalPages = totalCount % pageSize == 0 ? (totalCount / pageSize)
				: (totalCount / pageSize + 1);
		String url = request.getServletContext().getRealPath("/");

		// 创建导出文件夹
		File downPath = new File(url + "download");
		// 导出文件夹
		String exportFolder = downPath + File.separator + "payment";
		File paymentPath = new File(exportFolder);
		if (!(downPath.exists())) {
			downPath.mkdir();
		}
		if (!(paymentPath.exists())) {
			paymentPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		System.out.println("exportPath " + exportPath);
		File file = new File(exportPath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 循环向excel中导入文件
		int i = 0;
		do {
			i++;
			PaginationRecordsAndNumber<Payment, Number> eachPrn = paymentService
					.getPaginationRecords(null, i, pageSize);
			// 每一页数据循环导入其中
			List<Payment> plist = (List<Payment>) eachPrn.getRecords();
			b = paymentService.createPaymentExcel(exportPath, plist, i,
					pageSize);
			// 清空plist数据
			// plist = null;

		} while (i < totalPages);
		String FileDownloadPath = "";
		if (b) {
			String destPath = request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/download/payment/"
					+ uuid + ".xls";
		}
		return FileDownloadPath;
	}
}
