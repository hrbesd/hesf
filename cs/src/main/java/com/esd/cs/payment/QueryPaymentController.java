/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.payment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyProperty;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.User;
import com.esd.hesf.service.PaymentService;

/**
 * 缴款列表查询 controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/security/query/payment")
public class QueryPaymentController {

	private static final Logger log = LoggerFactory.getLogger(QueryPaymentController.class);
	private DecimalFormat format = new DecimalFormat("0.00");
	@Autowired
	private PaymentService pService;

	/**
	 * get方法, 跳转到列表页面
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listGet(HttpServletRequest req) {
		return new ModelAndView("query/payment");
	}

	/**
	 * post方法, 得到查询数据
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listPost(PaymentParamModel model) {
		log.debug(model.toString());
		// payment中的 审核对象
		Audit audit = new Audit();
		audit.setYear(model.getYear());
		// payment中的 公司对象
		Company company = new Company();
		company.setCompanyCode(model.getCompanyCode());
		company.setCompanyName(model.getCompanyName());
		if (model.getCompanyProperty() != null) {
			company.setCompanyProperty(new CompanyProperty(model.getCompanyProperty()));
		}
		if (model.getCompanyEconomyType() != null) {
			company.setCompanyEconomyType(new CompanyEconomyType(model.getCompanyEconomyType()));
		}
		if (model.getArea() != null) {
			company.setArea(new Area(model.getArea()));
		}
		// Payment对象
		Payment payment = new Payment();
		// 付款人对象放入其中
		if (model.getPaymentPerson() != null) {
			payment.setPaymentPerson(new User(model.getPaymentPerson()));
		}
		// 审核对象放入其中
		if (model.getYear() != null) {
			// payment.setAudit(audit);
		}
		// 公司对象放入其中
		payment.setPaymentCompany(company);
		// 返票?
		payment.setBillReturn(model.getBillReturn());
		// 作废票号?
		payment.setBillObsolete(model.getBillObsolete());
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("payment", payment);
		paramsMap.put("startDate", model.getStartDate());
		paramsMap.put("endDate", model.getEndDate());
		paramsMap.put("page", model.getPage()); // 分页--起始页
		paramsMap.put("pageSize", model.getRows());// 分页--返回量
		Map<String, Object> entity = new HashMap<String, Object>();
		// 查询结果
		PaginationRecordsAndNumber<Payment, Number> pgs = pService.getByMultiCondition(paramsMap);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 提取返回到前台显示的字段
		for (Iterator<Payment> iterator = pgs.getRecords().iterator(); iterator.hasNext();) {
			Payment p = iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("paymentDate", getStringDate(p.getPaymentDate()));// 缴款日期
			map.put("companyCode", p.getPaymentCompany().getCompanyCode());
			map.put("companyName", p.getPaymentCompany().getCompanyName());// 缴款公司
			map.put("paymentBill", p.getPaymentBill()); // 缴款票号
			String paymentMoney = String.valueOf(format.format(p.getPaymentMoney()));
			map.put("paymentMoney", paymentMoney); // 缴款金额
			map.put("paymentType", p.getPaymentType().getText()); // 缴款类型
			map.put("paymentPerson", p.getPaymentPerson().getUserRealName()); // 缴款操作人
			map.put("paymentExceptional", p.getPaymentExceptional().getPaymentExceptional()); // 缴款方式
			map.put("billReturn", p.getBillReturn()); // 是否返票了
			map.put("billFinance", p.getBillFinance()); // 是否财政
			map.put("billObsolet", p.getBillObsolete()); // 是否作废票据
			list.add(map);
		}
		Integer total = pgs.getNumber().intValue();
		entity.put("total", total);
		entity.put("rows", list);
		return entity;
	}

	private String getStringDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
