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
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.PaymentExceptional;
import com.esd.hesf.model.PaymentType;
import com.esd.hesf.model.User;
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
	 * 转到初审单位列表页面
	 */
	@RequestMapping(value = "/list/{process}", method = RequestMethod.GET)
	public ModelAndView initAudit_list(
			@PathVariable(value = "process") Integer process,
			HttpServletRequest request, HttpSession session) {
		String nowYear = (String) session.getAttribute(Constants.YEAR);
		request.setAttribute("nowYear", nowYear);
		request.setAttribute("process", process);
		return new ModelAndView("payment/payment_list");
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		logger.debug("id:{}", id);
		Audit audit = auditService.getByPrimaryKey(id);
		AuditParameter auditParameter = auditParameterService.getByYear(audit
				.getYear());
		Map<String, String> entity = new HashMap<String, String>();
		entity.put("id", String.valueOf(audit.getId()));
		entity.put("companyId", String.valueOf(audit.getCompany().getId()));
		entity.put("companyCode", audit.getCompany().getCompanyCode());
		entity.put("companyTaxCode", audit.getCompany().getCompanyTaxCode());
		entity.put("companyName", audit.getCompany().getCompanyName());
		entity.put("year", audit.getYear());
		entity.put("amountPayable", df.format(audit.getAmountPayable()));
		entity.put("remainAmount", df.format(audit.getRemainAmount()));
		entity.put("reductionAmount", df.format(audit.getReductionAmount()));
		entity.put("actualAmount", df.format(audit.getActualAmount()));
		entity.put("payAmount", df.format(audit.getPayAmount()));
		entity.put("delayPayAmount", df.format(audit.getDelayPayAmount()));
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
		logger.debug("aduitId:{}", id);
		Payment payment = paymentService.getByPrimaryKey(id);
		return new ModelAndView("payment/payment_detail_confirm", "entity",
				payment);
	}

	/**
	 * 确认保存
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
		PaginationRecordsAndNumber<Payment, Number> query = paymentService
				.getPaymentRecordByAudit(queryPayment.getAudit().getId(), 1,
						Integer.MAX_VALUE);
		BigDecimal payments = new BigDecimal(0.00);
		for (Payment pt : query.getRecords()) {
			if (pt.getBillReturn() == Boolean.TRUE) {
				payments = payments.add(pt.getPaymentMoney());
			}
		}
		Audit audit = auditService.getByPrimaryKey(queryPayment.getAudit()
				.getId());
		if (payments.compareTo(audit.getPayAmount()) == 0) {
			AuditProcessStatus auditProcessStatus = auditProcessStatusService
					.getByPrimaryKey(Constants.PROCESS_STATIC_YJK);
			audit.setAuditProcessStatus(auditProcessStatus);
			auditService.update(audit);
			// 更新补缴年度
			Integer companyId = audit.getCompany().getId();
			String[] unAudits = companyService.getUnauditYearByCompany(
					companyId, audit.getYear());
			if (unAudits != null) {
				for (String year : unAudits) {
					Audit a = auditService.getByPrimaryKey(year, companyId);
					a.setPayAmount(new BigDecimal(0));// 设置实缴总金额为0
					a.setAuditProcessStatus(auditProcessStatus);// 设置为达标
					a.setSupplementYear(audit.getYear());// 设置补缴年度
					auditService.update(a);
				}
			}
		} else {
			if (payments.signum() > 0) {
				AuditProcessStatus auditProcessStatus = auditProcessStatusService
						.getByPrimaryKey(Constants.PROCESS_STATIC_BFJK);
				audit.setAuditProcessStatus(auditProcessStatus);
				auditService.update(audit);
			}
		}

		return b;
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
	 * 获取新建缴款记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public ModelAndView addGet(@PathVariable(value = "id") Integer id,
			HttpSession session) {
		logger.debug("aduitId:{}", id);
		Payment payment = new Payment();
		Audit audit = auditService.getByPrimaryKey(id);
		payment.setAudit(audit);
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		payment.setPaymentPerson(user);
		PaginationRecordsAndNumber<Payment, Number> query = paymentService
				.getPaymentRecordByAudit(id, 1, Integer.MAX_VALUE);
		BigDecimal readyPayments = new BigDecimal(0.00);
		for (Payment pt : query.getRecords()) {
			if (pt.getBillReturn() == Boolean.FALSE) {
				readyPayments = readyPayments.add(pt.getPaymentMoney());
			}
		}
		readyPayments = audit.getPayAmount().subtract(readyPayments);
		payment.setPaymentMoney(readyPayments);
		return new ModelAndView("payment/payment_detail_add", "entity", payment);
	}

	/**
	 * 插入新建缴款记录
	 * 
	 * @param payment
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean outPost(Payment payment, HttpSession session) {
		logger.debug(payment.toString());
		Audit audit = auditService.getByPrimaryKey(payment.getAudit().getId());
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		payment.setUserId(userId);
		payment.setPaymentPerson(user);
		payment.setPaymentCompany(audit.getCompany());
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

	@RequestMapping(value = "/getPayments/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPayment(
			@PathVariable(value = "id") Integer id, HttpSession session) {
		Map<String, Object> entity = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> query = null;
		query = paymentService
				.getPaymentRecordByAudit(id, 1, Integer.MAX_VALUE);
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

	@RequestMapping(value = "/getBalance/{auditId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBalance(
			@PathVariable(value = "auditId") Integer auditId) {
		Map<String, Object> entity = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> query = paymentService
				.getPaymentRecordByAudit(auditId, 1, Integer.MAX_VALUE);
		BigDecimal payments = new BigDecimal(0.00);
		BigDecimal readyPayments = new BigDecimal(0.00);
		for (Payment payment : query.getRecords()) {
			if (payment.getBillObsolete() == Boolean.TRUE) {
				continue;
			}
			if (payment.getBillReturn() == Boolean.FALSE) {
				readyPayments = readyPayments.add(payment.getPaymentMoney());
			} else {
				payments = payments.add(payment.getPaymentMoney());
			}
		}
		entity.put("readyPayments", df.format(readyPayments));
		entity.put("payments", df.format(payments));
		Audit audit = auditService.getByPrimaryKey(auditId);
		BigDecimal balance = audit.getPayAmount().subtract(payments);
		entity.put("balance", df.format(balance));
		return entity;
	}

	@RequestMapping(value = "/delPayment/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delPayment(@PathVariable(value = "id") Integer id,
			HttpSession session) {
		Boolean b = paymentService.delete(id);
		return b;
	}

	@RequestMapping(value = "/backAudit/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean backAudit(@PathVariable(value = "id") Integer id,
			HttpSession session) {
		Audit audit = auditService.getByPrimaryKey(id);
		PaginationRecordsAndNumber<Payment, Number> query = paymentService
				.getPaymentRecordByAudit(id, 1, Integer.MAX_VALUE);
		if (query == null || query.getRecords().size() > 0) {
			return false;
		}
		AuditProcessStatus auditProcessStatus = auditProcessStatusService
				.getByPrimaryKey(Constants.PROCESS_STATIC_WFS);
		audit.setAuditProcessStatus(auditProcessStatus);
		Boolean b = auditService.update(audit);
		return b;
	}

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
