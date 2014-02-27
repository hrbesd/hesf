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

	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public ModelAndView addGet(@PathVariable(value = "id") Integer id) {
		logger.debug("id:{}", id);
		return new ModelAndView("payment/payment_detail_add", "id", id);
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
			pay.setAuditId(payment.getAuditId());
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
		query = paymentService.getPaymentRecord(id, 1, 999);
		entity.put("total", query.getNumber());
		List<Payments> list = new ArrayList<>();
		for (Iterator<Payment> iterator = query.getRecords().iterator(); iterator.hasNext();) {
			Payment it = (Payment) iterator.next();
			Payments p = new Payments();
			p.setId(it.getId());
			Date nowTime = it.getPaymentDate();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			String sDate = fmt.format(nowTime);
			p.setPaymentDate(sDate);
			p.setPaymentBill(it.getPaymentBill());
			p.setPaymentMoney(df.format(it.getPaymentMoney()));
			p.setPaymentPerson(it.getPaymentPerson().getUserRealName());
			p.setPaymentType(it.getPaymentType().getText());
			p.setRemark(it.getRemark());
			list.add(p);
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
