/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.documentation;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.worker.WorkerParamModel;
import com.esd.cs.worker.WorkerUtil;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.User;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.CompanyPropertyService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.CompanyTypeService;
import com.esd.hesf.service.UserService;
import com.esd.hesf.viewmodels.WorkerViewModel;

@Controller
@RequestMapping(value = "/security/print")
public class PrintCompany {
	private static final Logger logger = LoggerFactory.getLogger(PrintCompany.class);

	@Autowired
	private CompanyService companyService;// 企业
	@Autowired
	private AuditService auditService;// 审核对象
	@Autowired
	private UserService service;

	

	/**
	 * 转到打印列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView addCompany(HttpServletRequest request) {
		String s=CalendarUtil.getLastYear();
		// 获取当前年份
	request.setAttribute("nowYear", "2014");
		logger.debug("gotoPrintList:{}", "queryAudit");
		return new ModelAndView("documents/print_list");
	}

	/**
	 * 转到打印审核表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/audit/{companyId}/{year}", method = RequestMethod.GET)
	public ModelAndView audittab(@PathVariable(value = "companyId") String companyId, @PathVariable(value = "year") String year, HttpServletRequest request) {
		logger.debug("gotoPrint_audit companyId:{}", companyId);
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		Map<String, String> result = new HashMap<String, String>();

		Company company = companyService.getByPrimaryKey(companyId);
		if (company == null) {
			logger.error("getPrintAuditError:{}", "getCompanyNull");
			return null;
		}
		// 企业基本信息
		result.put("companyName", company.getCompanyName());// 企业名称

		result.put("companyBank", company.getCompanyBank());// 开户银行
		result.put("bankAccount", company.getCompanyBankAccount());// 银行账户
		result.put("zipCode", company.getCompanyZipCode());// 企业邮政编码
		result.put("contactPerson", company.getCompanyContactPerson());// 联系人
		result.put("taxCode", company.getCompanyTaxCode());// 税务编码
		result.put("legal", company.getCompanyLegal());// 企业法人
		result.put("address", company.getCompanyAddress());// 企业地址
		result.put("companyOrganizationCode", company.getCompanyOrganizationCode());// 组织机关代码证
		result.put("companyPhone", company.getCompanyPhone());// 电话
		// 残疾职工信息
		result.put("companyEmpTotal", company.getCompanyEmpTotal() + "");// 员工总数
		result.put("companyHandicapTotal", company.getCompanyHandicapTotal() + "");// 残疾员工总数
																					// 已录入数
		result.put("companyPredictTotal", company.getCompanyPredictTotal() + "");// 预计残疾人数
		result.put("companyShouldTotal", company.getCompanyShouldTotal() + "");// 应安排残疾人数
		result.put("companyAlreadyTotal", company.getCompanyAlreadyTotal() + "");// 已安排残疾人数
		// 保证金额度
		request.setAttribute("company", result);
		return new ModelAndView("documents/print_audit");
	}

	/**
	 * 获取打印审核信息
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/audit/{companyId}", method = RequestMethod.POST)
	@ResponseBody
	public Object workerList(@PathVariable(value = "companyId") String id, HttpServletRequest request) {

		Map<String, Object> entity = new HashMap<>();

		return entity;
	}

	/**
	 * 转到打印职工列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workerlist/{companyId}/{year}", method = RequestMethod.GET)
	public ModelAndView workerlist(@PathVariable(value = "companyId") String companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {
		logger.debug("gotoPrint_workerList,companyId:{}", companyId);
		request.setAttribute("companyId", companyId);
		Map<String, String> result = new HashMap<String, String>();
		Company company = companyService.getByPrimaryKey(companyId);
		if (company == null) {
			logger.error("getPrintAuditError:{}", "getCompanyNull");
			return null;
		}
		// 企业基本信息
		result.put("companyName", company.getCompanyName());// 企业名称
		result.put("taxCode", company.getCompanyTaxCode());// 税务编码
		result.put("companyOrganizationCode", company.getCompanyOrganizationCode());// 组织机关代码证
		result.put("year", company.getYear());// 年份
		request.setAttribute("company", result);
		return new ModelAndView("documents/print_workerList");
	}

	/**
	 * 转到打印催缴通知书页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notice/{companyId}/{year}", method = RequestMethod.GET)
	public ModelAndView notice(@PathVariable(value = "companyId") String companyId, @PathVariable(value = "year") String year, HttpServletRequest request) {

		logger.debug("gotoPrint_gotonotice,companyId:{},year:{}", companyId, year);
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		return new ModelAndView("documents/print_notice");
	}

	/**
	 * 获取催缴通知书页面数据
	 * 
	 * @param companyId
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notice/{companyId}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public Object get_company(@PathVariable(value = "companyId") String companyId, @PathVariable(value = "year") String year, HttpServletRequest request) {
		logger.debug("printNoticeParamsID:{},year:{}", companyId, year);
		try {
			logger.debug("getPrintNoticeInfo:{}", companyId);
			Map<String, String> result = new HashMap<String, String>();
			Company company = companyService.getByPrimaryKey(companyId);
			if (company == null) {
				logger.error("getPrintNoticeInfoError:{}", "getCompanyNull");
				return null;
			}
			// 企业基本信息
			result.put("companyName", company.getCompanyName());// 企业名称
			// 残疾职工信息
			result.put("companyEmpTotal", company.getCompanyEmpTotal() + "");// 员工总数
			result.put("companyHandicapTotal", company.getCompanyHandicapTotal() + "");// 残疾员工总数
																						// 已录入数
			result.put("companyPredictTotal", company.getCompanyPredictTotal() + "");// 预计残疾人数
			result.put("companyShouldTotal", company.getCompanyShouldTotal() + "");// 应安排残疾人数
			result.put("companyAlreadyTotal", company.getCompanyAlreadyTotal() + "");// 已安排残疾人数

			// 初审员
			Audit audit = auditService.getByPrimaryKey(year, company.getCompanyCode());
			if (audit == null) {
				logger.error("getPrintNoticeInfoError:{}", "getauditNull");
				return null;
			}
			User user = service.getByPrimaryKey(audit.getInitAuditUserId());
			// 复审员
			// 保证金额度
			logger.debug(" getcompany{}", company);
			request.setAttribute("company", company);
			return company;
		} catch (Exception e) {
			logger.error("获取企业对象发生错误");
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 检测单位当年度是否审核
	 * @param companyId
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detect/{companyId}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public Object detectaudit(@PathVariable(value = "companyId") String companyId, @PathVariable(value = "year") String year, HttpServletRequest request) {
		logger.debug("printNoticeParamsID:{},year:{}", companyId, year);
		try {
			Company company = companyService.getByPrimaryKey(companyId);
			if (company == null) {
				logger.error("getPrintNoticeInfoError:{}", "getCompanyNull");
				return false;
			}
			 Audit audit=auditService.getByPrimaryKey(year,company.getCompanyCode());
			 if(audit==null){
				 return false;
			 }
		} catch (Exception e) {
			logger.error("detectAuditError:{}",e.getMessage());
			 return false;
		}
		 logger.debug("detectAuditResult:{}","OK");
		 return true;
	}
	
	
	

}
