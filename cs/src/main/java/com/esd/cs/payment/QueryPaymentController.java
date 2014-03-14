package com.esd.cs.payment;

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
import com.esd.hesf.model.Payment;
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
	public Map<String, Object> listPost(HttpServletRequest req) {
		Map<String, Object> entity = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> pgs = pService.getPaginationRecords(null, 1, 50);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Iterator<Payment> iterator = pgs.getRecords().iterator(); iterator.hasNext();) {
			Payment p = iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("paymentDate", getStringDate(p.getPaymentDate()));//缴款日期
			map.put("companyName",p.getPaymentCompany().getCompanyName());//缴款公司
			map.put("paymentMoney", p.getPaymentMoney());	//缴款金额
			map.put("paymentType",p.getPaymentType().getText()); //缴款类型
			map.put("paymentPerson",p.getPaymentPerson().getUserName());	//缴款操作人
			map.put("paymentExceptional", p.getPaymentExceptional().getPaymentExceptional());	//缴款方式
			map.put("billReturn", p.getBillReturn());	//是否返票了
			map.put("billFinance", p.getBillFinance());	//是否财政
			map.put("billObsolet", p.getBillObsolete());	//是否作废票据
			list.add(map);
		}
		Integer total = pgs.getNumber().intValue();
		entity.put("total", total);
		entity.put("rows", list);
		return entity;
	}

	private String getStringDate(Date date){
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
