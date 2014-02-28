package com.esd.cs.payment;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.PaymentExceptional;
import com.esd.hesf.model.PaymentType;
import com.esd.hesf.model.User;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.PaymentService;
import com.esd.hesf.service.PaymentTypeService;
import com.esd.hesf.service.UserService;

@Controller
@RequestMapping(value = "/security/payment")
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
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

	private DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * 转到初审单位列表页面
	 */
	@RequestMapping(value = "/list/{process}", method = RequestMethod.GET)
	public ModelAndView initAudit_list(@PathVariable(value = "process") Integer process, HttpServletRequest request) {
		String nowYear = CalendarUtil.getLastYear();
		request.setAttribute("nowYear", nowYear);
		request.setAttribute("process", process);
		return new ModelAndView("payment/payment_list");
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable(value = "id") Integer id) {
		logger.debug("id:{}", id);
		Audit audit = auditService.getByPrimaryKey(id);
		return new ModelAndView("payment/payment_detail", "entity", audit);
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
	public ModelAndView confirmGet(@PathVariable(value = "id") Integer id, HttpSession session) {
		logger.debug("aduitId:{}", id);
		Payment payment = paymentService.getByPrimaryKey(id);
		return new ModelAndView("payment/payment_detail_confirm", "entity",payment);
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
		Boolean b = paymentService.update(payment);
		return b;
	}
	/**
	 * 获取新建缴款记录
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public ModelAndView addGet(@PathVariable(value = "id") Integer id, HttpSession session) {
		logger.debug("aduitId:{}", id);
		Payment payment = new Payment();
		Audit audit = auditService.getByPrimaryKey(id);
		payment.setAudit(audit);
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		payment.setPaymentPerson(user);
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
			//pay.setAudit(payment.getAuditId());
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
			PaymentType paymentType = paymentTypeService.getByPrimaryKey(Integer.valueOf(payment.getPaymentType()));
			pay.setPaymentType(paymentType);
			pay.setRemark(payment.getRemark());
			pay.setVersion(1);
			Company company = companyService.getByPrimaryKey(payment.getCompanyId());
			pay.setPaymentCompany(company);
			if (paymentService.save(pay) == true) {
				Audit audit = auditService.getByPrimaryKey(payment.getAuditId());
				AuditProcessStatus auditProcessStatus = auditProcessStatusService.getByPrimaryKey(Constants.PROCESS_STATIC_BFJK);
				audit.setAuditProcessStatus(auditProcessStatus);
				auditService.update(audit);
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/getPayments/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getPayment(@PathVariable(value = "id") Integer id, HttpSession session) {
		Map<String, Object> entity = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> query = null;
		query = paymentService.getPaymentRecord(id, 1, Integer.MAX_VALUE);
		entity.put("total", query.getNumber());
		List<Map<String,Object>> list = new ArrayList<>();
		for (Iterator<Payment> iterator = query.getRecords().iterator(); iterator.hasNext();) {
			Payment it = (Payment) iterator.next();
			Map<String,Object> map = new HashMap<>();
			map.put("id", it.getId());
			map.put("billPrintDate", CalendarUtil.dateFormat(it.getBillPrintDate()));
			map.put("paymentBill", it.getPaymentBill());
			map.put("paymentMoney", it.getPaymentMoney());
			map.put("billExchangeDate", CalendarUtil.dateFormat(it.getBillExchangeDate()));
			map.put("billReturn", it.getBillReturn());
			map.put("billFinance", it.getBillFinance());
			map.put("billObsolete", it.getBillObsolete());
			map.put("paymentExceptional", it.getPaymentExceptional());
			map.put("paymentType", it.getPaymentType());
			map.put("remark", it.getRemark());
			list.add(map);
		}
		entity.put("rows", list);
		return entity;
	}

	@RequestMapping(value = "/delPayment/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delPayment(@PathVariable(value = "id") Integer id, HttpSession session) {
		Boolean b = paymentService.delete(id);
		return b;
	}
}
