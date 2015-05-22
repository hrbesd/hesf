/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.payment;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.hesf.model.Area;
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

	private static final Logger logger = LoggerFactory
			.getLogger(QueryPaymentController.class);
	private DecimalFormat format = new DecimalFormat("0.00");
	@Autowired
	private PaymentService paymentService;

	/**
	 * get方法, 跳转到列表页面
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listGet(HttpServletRequest request, HttpSession session) {
		String nowYear = (String) session.getAttribute(Constants.YEAR);
		request.setAttribute("nowYear", nowYear);
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
	public Map<String, Object> listPost(PaymentParamModel model,
			HttpServletRequest request) {
		logger.debug(model.toString());
		// 提取模型中的参数, 放到map中
		Map<String, Object> paramsMap = getParams(model);
		paramsMap.put("page", model.getPage()); // 分页--起始页
		paramsMap.put("pageSize", model.getRows());// 分页--返回量
		// 查询结果
		PaginationRecordsAndNumber<Payment, Number> pgs = paymentService
				.getByMultiCondition(paramsMap);
		Map<String, Object> entity = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 提取返回到前台显示的字段
		for (Iterator<Payment> iterator = pgs.getRecords().iterator(); iterator
				.hasNext();) {
			Payment p = iterator.next();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", p.getId());
			map.put("paymentDate", getStringDate(p.getPaymentDate()));// 缴款日期
			map.put("companyCode", p.getPaymentCompany().getCompanyCode());
			map.put("companyName", p.getPaymentCompany().getCompanyName());// 缴款公司
			map.put("paymentBill", p.getPaymentBill()); // 缴款票号
			String paymentMoney = String.valueOf(format.format(p
					.getPaymentMoney()));
			map.put("paymentMoney", paymentMoney); // 缴款金额
			map.put("paymentType", p.getPaymentType().getText()); // 缴款类型
			map.put("paymentPerson", p.getPaymentPerson().getUserRealName()); // 缴款操作人
			map.put("paymentExceptional", p.getPaymentExceptional()
					.getPaymentExceptional()); // 缴款方式
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

	/**
	 * 批量导出缴款信息
	 * 
	 * @param idArr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public String export(PaymentParamModel params, Integer[] idArray,
			HttpServletRequest request) {
		logger.debug("exportPayment  params:" + params + ", idArray :"
				+ idArray);
		Boolean b = true;
		List<Payment> list = null;
		// 下载全部
		if ("yes".equals(params.getIsDownLoadAll())) {
			// 获得参数
			Map<String, Object> paramsMap = getParams(params);
			paramsMap.put("page", Constants.PAGE_START); // 分页--起始页
			// ******************************
			paramsMap.put("pageSize", Constants.PAGE_SIZE_MAX);// 分页--返回量

			list = new ArrayList<Payment>();
			for (Payment c : paymentService.getByMultiCondition(paramsMap)
					.getRecords()) {
				list.add(c);
			}
		} else {
			list = paymentService.getByIds(idArray);
		}
		String url = request.getServletContext().getRealPath("/");
		// 创建导出文件夹
		File downloadPath = new File(url + "temp");
		if (!(downloadPath.exists())) {
			downloadPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = downloadPath + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createPaymentExcel(exportPath, list);
		if (b) {
//			String destPath = request.getLocalAddr() + ":"
//					+ request.getLocalPort() + request.getContextPath();
//			FileDownloadPath = "http://" + destPath + "/temp/" + uuid + ".xls";
			FileDownloadPath = Constants.DOMAIN_NAME + uuid + ".xls";
		}
		logger.debug("ecportAuditResults:{},paramsId:{}", b, idArray);
		return FileDownloadPath;
	}

	/**
	 * 从PaymentParamModel 参数对象中, 将各个属性字段取出, 放到map对象中
	 * 
	 * @param param
	 * @return
	 */
	private Map<String, Object> getParams(PaymentParamModel model) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		// payment中的 公司对象
		Company company = new Company();
		company.setCompanyCode(model.getCompanyCode());
		company.setCompanyName(model.getCompanyName());
		if (model.getCompanyProperty() != null) {
			company.setCompanyProperty(new CompanyProperty(model
					.getCompanyProperty()));
		}
		if (model.getCompanyEconomyType() != null) {
			company.setCompanyEconomyType(new CompanyEconomyType(model
					.getCompanyEconomyType()));
		}
		if (model.getArea() != null) {
			company.setArea(new Area(model.getArea()));
		}
		// Payment对象
		Payment payment = new Payment();
		// 审核年度
		payment.setAuditYear(model.getYear());
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

		paramsMap.put("payment", payment);
		// 缴款金额区间
		if (model.getMinPaymentMoney() != null
				&& !"".equals(model.getMinPaymentMoney())) {
			paramsMap.put("minPaymentMoney",
					new BigDecimal(model.getMinPaymentMoney()));// 最低金额
		}
		if (model.getMaxPaymentMoney() != null
				&& !"".equals(model.getMaxPaymentMoney())) {
			paramsMap.put("maxPaymentMoney",
					new BigDecimal(model.getMaxPaymentMoney()));// 最高金额
		}
		// 区分省残联和地税的单位
		paramsMap.put("belongsType", model.getBelongsType());
		paramsMap.put("startDate", model.getStartDate());
		paramsMap.put("endDate", model.getEndDate());
		return paramsMap;
	}
}
