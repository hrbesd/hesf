/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.audit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.cs.company.CompanyParamModel;
import com.esd.cs.worker.QueryWorkerController;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;

@Controller
@RequestMapping(value = "/security/query/audit")
public class QueryAuditController {
	private static final Logger logger = LoggerFactory
			.getLogger(QueryWorkerController.class);

	@Autowired
	private AuditService auditService;

	@Autowired
	private CompanyService companyService;

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
			Map<String, Object> paramsMap = getParams(params);
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
				map.put("companyName", it.getCompany().getCompanyName());// 企业名称
				map.put("companyContactPerson", it.getCompany()
						.getCompanyContactPerson()); // 联系人
				map.put("companyPhone", it.getCompany().getCompanyPhone());// 联系电话
				map.put("companyAddress", it.getCompany().getCompanyAddress());// 公司地址
				map.put("companyEmpTotal", it.getCompanyEmpTotal());	// 员工总数
				map.put("companyHandicapTotal", it.getCompanyHandicapTotal());
				map.put("auditProcessStatus", it.getAuditProcessStatus()
						.getAuditProcessStatus()); // 流程状态
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

	/**
	 * 批量导出审核信息
	 * 
	 * @param idArr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public String export(AuditParamModel params, Integer[] idArray,HttpServletRequest request) {
		boolean b = true;
		List<Audit> auditList = null;
		// 下载全部
		if ("yes".equals(params.getIsDownLoadAll())) {
			// 获得参数
			Map<String, Object> paramsMap = getParams(params);
			paramsMap.put("page", Constants.PAGE_START); // 分页--起始页
			paramsMap.put("pageSize", Integer.MAX_VALUE);// 分页--返回量
			auditList = new ArrayList<Audit>();
			for (Audit c : auditService.getByMultiCondition(paramsMap)
					.getRecords()) {
				auditList.add(c);
			}
		} else {
			// 下载选中的
			auditList = auditService.getByIds(idArray);
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
		b = PoiCreateExcel.createAuditExcel(exportPath, auditList);
		if (b) {
			String destPath = request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/temp/" + uuid + ".xls";
		}
		return FileDownloadPath;
	}

	/**
	 * 跳转到查看某公司的历史审核数据页面中
	 * 
	 * @param companyId
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/history/{companyId}/{year}", method = RequestMethod.GET)
	public ModelAndView queryAuditHistoryList(
			@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		return new ModelAndView("audit/history_list");
	}

	@RequestMapping(value = "/history")
	public Map<String, Object> queryHistoryList(HttpServletRequest request) {
		Integer companyId = Integer.parseInt(request.getParameter("companyId"));
		String year = request.getParameter("year");
		PaginationRecordsAndNumber<Audit, Number> history = auditService
				.getHistory(companyId, year);
		// 返回的结果集
		Map<String, Object> entity = new HashMap<String, Object>();

		// 装迭代起来的审核数据的list
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Iterator<Audit> iterator = history.getRecords().iterator(); iterator
				.hasNext();) {
			Audit it = iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id", it.getId());// id
			map.put("year", it.getYear()); // 审核年度
			map.put("companyEmpTotal", it.getCompanyEmpTotal());// 企业员工总人数
			map.put("companyHandicapTotal", it.getCompanyHandicapTotal()); // 残疾职工总人数
			map.put("companyShouldTotal", it.getCompanyShouldTotal()); // 应安排人数
			map.put("companyAlreadyTotal", it.getCompanyAlreadyTotal()); // 已安排人数
			map.put("amountPayable", it.getAmountPayable()); // 应缴金额
			map.put("reductionAmount", it.getReductionAmount()); // 减缴金额
			map.put("remainAmount", it.getRemainAmount()); // 上年度未缴金额
			map.put("complementAmount", it.getComplementAmount()); // 补缴金额
			map.put("delayPayAmount", it.getDelayPayAmount()); // 滞纳金
			map.put("isDelayPay", it.getIsDelayPay()); // 是否减免滞纳金
			map.put("payAmount", it.getPayAmount()); // 实缴总金额
			map.put("auditProcessStatus", it.getAuditProcessStatus()
					.getAuditProcessStatus()); // 审核状态
			list.add(map);
		}
		entity.put("rows", list);
		entity.put("total", history.getNumber());
		return entity;
	}

	/**
	 * 从AuditParamModel 参数对象中, 将各个属性字段取出, 放到map对象中
	 * 
	 * @param param
	 * @return
	 */
	private Map<String, Object> getParams(AuditParamModel params) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("year", params.getYear()); // 年度
		paramsMap.put("companyCode", params.getCompanyCode()); // 公司档案号
		paramsMap.put("companyTaxCode", params.getCompanyTaxCode()); // 公司税务编码
		paramsMap.put("companyOrganizationCode",
				params.getCompanyOrganizationCode()); // 组织机构代码证
		paramsMap.put("companyProperty", params.getCompanyProperty()); // 公司性质
		paramsMap.put("companyEconomyType", params.getCompanyEconomyType()); // 公司经济类型
		paramsMap.put("areaCode", params.getArea()); // 地区 对应地区 code
		paramsMap.put("minTotal", params.getCompanyEmpTotal_1()); // 最少职工人数
		paramsMap.put("maxTotal", params.getCompanyEmpTotal_2()); // 最多职工人数
		paramsMap.put("minHandicapTotal", params.getCompanyHandicapTotal_1());	// 最少残疾人数
		paramsMap.put("maxHandicapTotal", params.getCompanyHandicapTotal_2());	// 最多残疾人数
		paramsMap.put("companyName", params.getCompanyName()); // 公司名称
		paramsMap.put("companyAddress", params.getCompanyAddress()); // 公司地址
		paramsMap.put("companyContactPerson", params.getCompanyContactPerson()); // 公司联系人
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
		return paramsMap;
	}
}
