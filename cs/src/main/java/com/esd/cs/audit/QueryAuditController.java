/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.audit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.esd.cs.company.CompanyParamModel;
import com.esd.cs.worker.QueryWorkerController;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.WorkerService;

@Controller
@RequestMapping(value = "/security/query/audit")
public class QueryAuditController {
	private static final Logger logger = LoggerFactory
			.getLogger(QueryWorkerController.class);

	@Autowired
	private AuditService auditService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private WorkerService workerService;

	/**
	 * 转到查询审核数据列表页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView queryAuditList(HttpServletRequest request,
			HttpSession session) {
		String nowYear = (String) session.getAttribute(Constants.YEAR);
		request.setAttribute("nowYear", nowYear);
		return new ModelAndView("query/audit");
	}

	/**
	 * 获取审核列表数据
	 * 
	 * @param page
	 * @param rows
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> provincePost(AuditParamModel params,
			HttpServletRequest request) {
		logger.debug("queryAuditParams:{}", params);

		Map<String, Object> entity = new HashMap<>();
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("year", params.getYear()); // 年度
			paramsMap.put("companyCode", params.getCompanyCode()); // 公司档案号
			paramsMap.put("companyTaxCode", params.getCompanyTaxCode()); // 公司税务编码
			paramsMap.put("companyOrganizationCode",
					params.getCompanyOrganizationCode()); // 组织机构代码证
			paramsMap.put("companyProperty", params.getCompanyProperty()); // 公司性质
			paramsMap.put("companyEconomyType", params.getCompanyEconomyType()); // 公司经济类型
			paramsMap.put("areaCode", params.getArea()); // 地区 对应地区 code
			paramsMap.put("minTotal", params.getCompanyEmpTotal_1()); // 查询范围中
			paramsMap.put("maxTotal", params.getCompanyEmpTotal_2()); // 查询范围中
			paramsMap.put("companyName", params.getCompanyName()); // 公司名称
			paramsMap.put("companyAddress", params.getCompanyAddress()); // 公司地址
			paramsMap.put("companyLegal", params.getCompanyLegal()); // 公司法人代表
			paramsMap.put("auditProcessStatus", params.getAuditProcessStatus()); // 流程状态
			paramsMap.put("paymentPerson", params.getPaymentPerson()); // 缴款人 id
			String overYear = params.getOverYear();// 超过几年未初审的公司
			if (StringUtils.isBlank(overYear)) {
				overYear = "0";
			}
			paramsMap.put("overYear", overYear);// 超过几年未初审的公司
			paramsMap.put("isExempt", Boolean.valueOf(params.getIsExempt())); // 是否免缴
																				// true免缴,
																				// false不免缴
			paramsMap.put("page", params.getPage()); // 分页--起始页
			paramsMap.put("pageSize", params.getRows());// 分页--返回量

			logger.debug("queryAuditParamsEx:{}", params);
			PaginationRecordsAndNumber<Audit, Number> query = auditService
					.getByMultiCondition(paramsMap);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<Audit> iterator = query.getRecords().iterator(); iterator
					.hasNext();) {
				Audit it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("companyId", it.getCompany().getId());// id
				map.put("companyCode", it.getCompany().getCompanyCode());// 企业档案编号
				map.put("companyTaxCode", it.getCompany().getCompanyTaxCode());// 税务编号
				map.put("companyName", it.getCompany().getCompanyName());// 企业名称
				map.put("companyLegal", it.getCompany().getCompanyLegal());
				map.put("companyAddress", it.getCompany().getCompanyAddress());
				map.put("companyPhone", it.getCompany().getCompanyPhone());// 流程状态
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("queryAuditResult:{}", total);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryAuditError{}", e.getMessage());
		}
		return entity;
	}

	/**
	 * 转到查询公司数据列表页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listforcompany", method = RequestMethod.GET)
	public ModelAndView queryAuditListForQueryer(HttpServletRequest request,
			HttpSession session) {
		return new ModelAndView("query/audit_for_queryer");
	}

	/**
	 * 获取公司列表数据
	 * 
	 * @param page
	 * @param rows
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listforcompany", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> provincePostForQueryer(CompanyParamModel params,
			HttpServletRequest request) {
		logger.debug("queryCompanyParams:{}", params);

		Map<String, Object> entity = new HashMap<>();
		try {
			Company t = new Company();
			t.setCompanyName(params.getCompanyName());
			t.setCompanyAddress(params.getCompanyAddress());
			if ("firstTime".equals(params.getFirstTime())) {
				params.setRows(0);// 分页--返回量
			}
			PaginationRecordsAndNumber<Company, Number> query = companyService
					.getPaginationRecords(t, params.getPage(), params.getRows());
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<Company> iterator = query.getRecords().iterator(); iterator
					.hasNext();) {
				Company it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("companyId", it.getId());// id
				map.put("companyName", it.getCompanyName());// 企业名称
				map.put("companyAddress", it.getCompanyAddress());
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("queryCompanyResult:{}", total);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryCompanyResult{}", e.getMessage());
		}
		return entity;
	}

	@RequestMapping(value = "/getFinalPrintInfo/{auditId}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getFinalPrintInfo(
			@PathVariable(value = "auditId") Integer auditId) {
		Map<String, Object> entity = new HashMap<String, Object>();
		Audit audit = auditService.getByPrimaryKey(auditId);
		Company company = companyService.getByPrimaryKey(audit.getCompany()
				.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("year", audit.getYear());
		params.put("companyId", company.getId());
		params.put("page", 1);
		params.put("pageSize", Integer.MAX_VALUE);
		PaginationRecordsAndNumber<Worker, Number> workers = workerService
				.getPaginationRecords(params);
		// 企业信息
		entity.put("companyName", company.getCompanyName());
		entity.put("companyProperty", company.getCompanyProperty()
				.getCompanyProperty());
		entity.put("companyManagement", company.getCompanyManagement());
		entity.put("companyLegal", company.getCompanyLegal());
		entity.put("companyAddress", company.getCompanyAddress());
		entity.put("companyPhone", company.getCompanyPhone());
		// 审核信息
		entity.put("year", audit.getYear()); // 审核年度
		entity.put("empTotal", audit.getCompanyEmpTotal()); // 员工总数
		entity.put("shouldTotal", audit.getCompanyShouldTotal());// 应安排数
		entity.put("alreadyTotal", audit.getCompanyAlreadyTotal());// 已安排数
		// 将已安排数由Integer转化为BigDecimal
		BigDecimal tempAlreadyTotal = new BigDecimal(
				audit.getCompanyAlreadyTotal());
		// 少安排人数,和0进行比较, 如果比0小, 则统一改成0.00, 否则正常显示差值
		BigDecimal lessTotal = ((audit.getCompanyShouldTotal()
				.subtract(tempAlreadyTotal)).compareTo(new BigDecimal("0"))) < 0 ? (new BigDecimal(
				"0.00")) : (audit.getCompanyShouldTotal()
				.subtract(tempAlreadyTotal));
		entity.put("lessTotal", lessTotal); // 尚缺残疾人数
		entity.put("payAmount", audit.getPayAmount()); // 应缴保障金金额
		// 所有残疾职工列表
		List<Map<String, Object>> workerList = new ArrayList<Map<String, Object>>();
		for (Worker worker : workers.getRecords()) {
			Map<String, Object> workerMap = new HashMap<String, Object>();
			workerMap.put("name", worker.getWorkerName());
			workerMap.put("gender", worker.getWorkerGender());
			// 计算年龄
			int age = Integer.parseInt(CalendarUtil.getNowYear())
					- Integer.parseInt(worker.getWorkerBirthYear());
			workerMap.put("age", age);
			workerMap.put("handicapCode", worker.getWorkerHandicapCode());
			workerMap.put("isRetired", worker.getIsRetired());
			workerList.add(workerMap);
		}
		entity.put("workerList", workerList);
		return entity;
	}
}
