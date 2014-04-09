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
		BigDecimal alreadyPayment = paymentService.getEffPaid(null, year,
				companyId);
		if (alreadyPayment == null) {
			alreadyPayment = new BigDecimal("0.00");
		}
		Audit audit = accounts.getAudit();
		AuditParameter auditParameter = auditParameterService.getByYear(audit
				.getYear());
		Map<String, Object> entity = new HashMap<String, Object>();
		// 账目id
		// entity.put("id", String.valueOf(accounts.getId()));
		entity.put("companyId", String.valueOf(accounts.getCompany().getId()));
		entity.put("companyCode", accounts.getCompany().getCompanyCode());
		entity.put("companyTaxCode", accounts.getCompany().getCompanyTaxCode());
		entity.put("companyName", accounts.getCompany().getCompanyName());
		entity.put("auditYear", accounts.getAudit().getYear()); // 缴款审核年度
		entity.put("accountsYear", accounts.getYear()); // 缴款年度
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

		// 获得 此账目涵盖的审计年度s
		String[] auditYears = accountsService.getAuditYears(year, companyId);
		// if(auditYears!=null){
		// String years = "";
		// for(int i=0;i<auditYears.length;i++){
		// years += auditYears[i];
		// if(i!=auditYears.length-1){
		// years += ", ";
		// }
		// }
		// entity.put("auditYears", years);
		// }
		entity.put("auditYears", auditYears);
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

//	/**
//	 * 批量更新状态
//	 * 
//	 * @param payment
//	 * @return
//	 */
//	private Boolean batchUpdateAuditStatus(Payment payment,
//			Integer auditProcessStatus) {
//		// AuditProcessStatus aup = auditProcessStatusService
//		// .getByPrimaryKey(auditProcessStatus);
//		// 1-如果缴款存在审核年份, 则只将 对应审核年份的账目更新状态
//		if (payment.getAuditYear() != null
//				&& !"".equals(payment.getAuditYear())) {
//			Audit audit = auditService.getByPrimaryKey(payment.getAuditYear(),
//					payment.getPaymentCompany().getId());
//			audit.setAuditProcessStatus(new AuditProcessStatus(
//					auditProcessStatus));
//			auditService.update(audit);
//			Accounts accounts = accountsService.getOneByCompanyAuditYear(
//					payment.getAuditYear(), payment.getYear(), payment
//							.getPaymentCompany().getId());
//			accounts.setAuditProcessStatus(new AuditProcessStatus(
//					auditProcessStatus));
//			return accountsService.update(accounts);
//		}
//
//		// 2-如果不存在审核年份, 则将所有未完成的账目单查询出来, 逐个更新
//		String year = payment.getYear();
//		Integer companyId = payment.getPaymentCompany().getId();
//		List<Accounts> query = accountsService.getCompanyAccount(year,
//				companyId);
//		try {
//			for (Accounts at : query) {
//				// 如果账单表已经标记为已交款或者达标, 则跳过
//				if (at.getAuditProcessStatus().getId()
//						.equals(Constants.PROCESS_STATIC_YJK)
//						|| at.getAuditProcessStatus().getId()
//								.equals(Constants.PROCESS_STATIC_OK)) {
//					continue;
//				}
//				// 更新审核表 即时审核状态,
//				Audit audit = auditService.getByPrimaryKey(at.getAudit()
//						.getId());
//				audit.setAuditProcessStatus(new AuditProcessStatus(
//						auditProcessStatus));
//				// 补缴年份
//				audit.setSupplementYear(payment.getYear());
//				auditService.update(audit);
//				// 更新账目 即时审核状态,对应审核年份
//				at.setAuditProcessStatus(new AuditProcessStatus(
//						auditProcessStatus));
//				accountsService.update(at);
//			}
//		} catch (Exception e) {
//			logger.error("{}", e);
//			return Boolean.FALSE;
//		}
//		return Boolean.TRUE;
//	}

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
		// 更新缴款明细
		paymentService.update(queryPayment);

		// 判断缴款明细是否作废, 如果作废的话, 则不执行下面的更新步骤
		if (queryPayment.getBillObsolete()) {
			return true;
		}
		/**
		 * 以下执行更新状态步骤
		 */
		// 1-如果缴款存在审核年份, 则只将 对应审核年份的账目更新状态
		Integer auditProcessStatus;
		if (queryPayment.getAuditYear() != null
				&& !"".equals(queryPayment.getAuditYear())) {
			// 1-1查找对应审核年份的已交款总金额, 来判断需要更新的状态
			BigDecimal alreadyPayment = paymentService.getEffPaid(queryPayment
					.getAuditYear(), queryPayment.getYear(), queryPayment
					.getPaymentCompany().getId());
			// 1-2找到对应的账目
			Accounts accounts = accountsService.getOneByCompanyAuditYear(
					queryPayment.getAuditYear(), queryPayment.getYear(), queryPayment
							.getPaymentCompany().getId());
			// 1-3应交款==已交款
			if (accounts.getTotalMoney().compareTo(alreadyPayment) == 0) {
				auditProcessStatus = Constants.PROCESS_STATIC_YJK;
				Audit audit = auditService.getByPrimaryKey(accounts.getAudit()
						.getId());
				audit.setAuditProcessStatus(new AuditProcessStatus(
						auditProcessStatus));
				auditService.update(audit);
				accounts.setAuditProcessStatus(new AuditProcessStatus(
						auditProcessStatus));
				accounts.setIsFinished(true);
				Boolean b2 = accountsService.update(accounts);
				return b2;
			} else {
				// 1-4应交款 ≠ 已交款
				auditProcessStatus = Constants.PROCESS_STATIC_BFJK;
				Audit audit = auditService.getByPrimaryKey(accounts.getAudit()
						.getId());
				audit.setAuditProcessStatus(new AuditProcessStatus(
						auditProcessStatus));
				auditService.update(audit);
				accounts.setAuditProcessStatus(new AuditProcessStatus(
						auditProcessStatus));
				accounts.setIsFinished(true);
				Boolean b2 = accountsService.update(accounts);
				return b2;
			}
		} else {
			// 2-如果不存在审核年份
			// 2-1 查找公司对应账目年份的已交款总金额, 来判断需要更新的状态
			BigDecimal alreadyPayment = paymentService.getEffPaid(null,
					queryPayment.getYear(), queryPayment.getPaymentCompany().getId());
			// 2-2查找该公司 该账目年度 应交款总额
			BigDecimal paymentAmount = accountsService.getCompanyAuditYear(
					null, queryPayment.getYear(), queryPayment.getPaymentCompany()
							.getId());
			// 2-3查找公司对应账目年限的所有账目列表
			List<Accounts> acList = accountsService.getCompanyAccount(
					queryPayment.getYear(), queryPayment.getPaymentCompany().getId());
			// 2-4 应交款==已交款
			if (paymentAmount.compareTo(alreadyPayment) == 0) {
				auditProcessStatus = Constants.PROCESS_STATIC_YJK;
				for (Accounts ac : acList) {
					// 2-4-1如果账单表已经标记为已交款或者达标, 则跳过--减少读取数据库的操作
					if (ac.getAuditProcessStatus().getId()
							.equals(Constants.PROCESS_STATIC_YJK)
							|| ac.getAuditProcessStatus().getId()
									.equals(Constants.PROCESS_STATIC_OK)) {
						continue;
					}
					// 2-4-2更新审核表状态
					Audit audit = ac.getAudit();
					audit.setAuditProcessStatus(new AuditProcessStatus(
							auditProcessStatus));
					// 设置补审年份
					audit.setSupplementYear(queryPayment.getYear());
					auditService.update(audit);
					// 2-4-3 更新账目
					ac.setAuditProcessStatus(new AuditProcessStatus(
							auditProcessStatus));
					// 账目缴款缴款完成
					ac.setIsFinished(true);
					accountsService.update(ac);
				}
				return true;
			} else {
				// 2-5 应交款 ≠ 已交款
				auditProcessStatus = Constants.PROCESS_STATIC_BFJK;
				for (Accounts ac : acList) {
					// 2-5-1如果账单表已经标记为已交款或者达标, 则跳过--减少读取数据库的操作;
					// 同时也可预防对更新为已交款的指定审核年度的账目状态进行更新
					if (ac.getAuditProcessStatus().getId()
							.equals(Constants.PROCESS_STATIC_YJK)
							|| ac.getAuditProcessStatus().getId()
									.equals(Constants.PROCESS_STATIC_OK)) {
						continue;
					}
					// 2-5-2更新审核表状态
					Audit audit = ac.getAudit();
					audit.setAuditProcessStatus(new AuditProcessStatus(
							auditProcessStatus));
					// 设置补审年份
					audit.setSupplementYear(queryPayment.getYear());
					auditService.update(audit);
					// 2-5-3 更新账目
					ac.setAuditProcessStatus(new AuditProcessStatus(
							auditProcessStatus));
					accountsService.update(ac);
				}
				return true;

			}
		}
	}

	// public static void main(String[] args) {
	// BigDecimal b1 = new BigDecimal("-0.01");
	// BigDecimal b2 = new BigDecimal("0.00");
	// // System.out.println(b1.compareTo(b2));
	// String s = "2012";
	// String[] ar = s.split(",");
	// // System.out.println(ar.length);
	// for (String t : ar) {
	// System.out.println(t);
	// }
	// }

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
	@RequestMapping(value = "/add/{auditYear}/{accountsYear}/{lessPayAmount}/{companyId}", method = RequestMethod.GET)
	public ModelAndView addGet(
			@PathVariable(value = "auditYear") String auditYear,
			@PathVariable(value = "accountsYear") String accountsYear,
			@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "lessPayAmount") String slessPayAmount,
			HttpSession session) {
		BigDecimal lessPayAmount = new BigDecimal(slessPayAmount);
		// 待缴金额(应缴-已缴)
		logger.debug("lessPayAmount:{},slessPayAmount:{}", lessPayAmount,
				slessPayAmount);
		Payment payment = new Payment();
		Company company = companyService.getByPrimaryKey(companyId);
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		// 审核年份
		if ("all".equals(auditYear)) {
			auditYear = "";
		}
		payment.setAuditYear(auditYear);
		// 缴款 出账年份
		payment.setYear(accountsYear);
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

		// // 根据公司id,账目年限,审核年份, 得到总应缴款数量
		// BigDecimal payAmount = accountsService.getCompanyAuditYear(auditYear,
		// accountsYear, companyId);
		// // 根据公司id,账目年限,审核年份, 得到已缴款数
		// BigDecimal alreadyPayment = paymentService.getEffPaid(auditYear,
		// accountsYear, companyId);
		// if (alreadyPayment == null) {
		// alreadyPayment = new BigDecimal("0.00");
		// }
		// BigDecimal lessPayment = payAmount.subtract(alreadyPayment);
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

	@RequestMapping(value = "/getBalance/{auditYear}/{accountsYear}/{companyId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getBalance(
			@PathVariable(value = "auditYear") String auditYear,
			@PathVariable(value = "accountsYear") String accountsYear,
			@PathVariable(value = "companyId") Integer companyId,
			HttpServletRequest request) {
		if ("all".equals(auditYear)) {
			auditYear = null;
		}
		// 根据公司id,账目年限,审核年份, 得到总应缴款数量
		BigDecimal payAmount = accountsService.getCompanyAuditYear(auditYear,
				accountsYear, companyId);
		// 根据公司id,账目年限,审核年份, 得到已缴款数
		BigDecimal alreadyPayment = paymentService.getEffPaid(auditYear,
				accountsYear, companyId);
		if (alreadyPayment == null) {
			alreadyPayment = new BigDecimal("0.00");
		}
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("payAmount", df.format(payAmount));
		entity.put("alreadyPayAmount", df.format(alreadyPayment));
		entity.put("lessPayAmount",
				df.format(payAmount.subtract(alreadyPayment)));
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
