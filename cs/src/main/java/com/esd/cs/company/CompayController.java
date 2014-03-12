/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.company;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.esd.cs.common.ParameterController;
import com.esd.cs.payment.Payments;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;

@Controller
@RequestMapping(value = "/security/company")
public class CompayController {
	private static final Logger logger = LoggerFactory.getLogger(CompayController.class);

	@Autowired
	private CompanyService companyService;// 企业
	@Autowired
	private AuditService auditService;// 审核


	/**
	 * 转到基本档案页面 省直 中直 机关事业
	 * 
	 * @param property
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/{property}", method = RequestMethod.GET)
	public ModelAndView companyGet(@PathVariable(value = "property") String property, HttpServletRequest request) {
		logger.debug("companyProperty{}", property);
		// 续传用户类型
		request.setAttribute("companyProperty", property);
		logger.debug("JumpCompany:{},year:{}", property,ParameterController.getYear());
		return new ModelAndView("basicInfo/company_list");
	}

	/**
	 * 转到 增加企业信息 页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addCompany(HttpServletRequest request) {
		logger.debug("gotoaddCompany");
		return new ModelAndView("basicInfo/add_company");
	}

	/**
	 * 转到 查看企业信息 页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view_company(@PathVariable(value = "id") Integer id,HttpServletRequest request) {
		logger.debug("id{}", id);
		Company company = companyService.getByPrimaryKey(id);
		logger.debug("company{}", company);
		return new ModelAndView("basicInfo/view_company", "company", company);
	}

	/**
	 * 转到编辑企业页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editCompany(@PathVariable(value = "id") Integer id,HttpServletRequest request) {
		logger.debug("id{}", id);
		Company company = companyService.getByPrimaryKey(id);
		logger.debug("editCompany{}", company);

		return new ModelAndView("basicInfo/edit_company", "company", company);
	}

	/**
	 * 根据ID获取单位对象 续传ID
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/get_company", method = RequestMethod.POST)
	@ResponseBody
	public Object get_company(@RequestParam(value = "id") Integer id, HttpServletRequest request) {

		try {
			logger.debug("getByIdCompanyId:{}",id);
			Company company = companyService.getByPrimaryKey(id);
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
	 * 增加企业信息
	 * 
	 * @param company
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean add_company(Company company, HttpServletRequest request, HttpSession session) {
		logger.debug("addCompany:{}", company);
		try {
			if (company == null) {
				logger.error("addCompany:{}", "paramserror");
				return false;
			}
			boolean b = companyService.save(company,ParameterController.getYear());
			logger.debug("addCompanyResult:{}", b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addCompany:{}", "adderror");
		}
		return false;
	}

	/**
	 * 编辑企业信息
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Boolean edit_company(Company company) {
		logger.debug("editCompanyParams:{}", company);
		boolean b = companyService.update(company);
		logger.debug("editCompanyResult:{}", b);
		return b;
	}

	/**
	 * 删除单位控制器
	 * 
	 * @param idArr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteCompany(@RequestParam(value = "params[]") Integer idArr[], HttpServletRequest request) {
		logger.debug("deleteCompany:{}", idArr.toString());
		boolean b = true;
		for (int i = 0; i < idArr.length; i++) {
			Company c = companyService.getByPrimaryKey(idArr[i]);
			c.setIsActive(true);
			b = companyService.update(c);
			logger.debug("deleteCompanyID:{},Result:{}", idArr[i], b);
			if (b == false) {
				return b;
			}
		}
		logger.debug("deleteCompanyResults:{},paramsId:{}", b, idArr);
		return b;
	}

	/**
	 * 验证 组织机关代码证
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validate_companyOrganizationCode")
	@ResponseBody
	public Boolean validate_companyOrganizationCode(@RequestParam(value = "param") String param, HttpServletRequest request) {
		Company c = companyService.getCompanyByOrganizationCode(param);
		if (c == null) {
			logger.debug("validateCompanyOrganizationCode{} Result{}", param, "true");
			return true;
		} else {
			logger.debug("validateCompanyOrganizationCode{} Result{}", param, "false");
			return false;
		}
	}

	/**
	 * 根据企业id 获取企业信息
	 * @param companyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getinformation/{companyId}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getComapnmyInformation(@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {

		logger.debug("getComapnmyInformation:{}", companyId);
		List<Map<String, String>> list = null;
		Map<String, String> map;
		Company c = companyService.getByPrimaryKey(companyId);
		if (c != null) {
			map = new HashMap<String, String>();
			list = new ArrayList<Map<String, String>>();
			map.put("companyName", c.getCompanyName());// 企业名称
			map.put("companyId", String.valueOf(c.getId()));// 档案编码
			map.put("companyCode", c.getCompanyCode());// 档案编码
			map.put("companyTaxCode", c.getCompanyTaxCode());// 税务编码
			map.put("companyEconomyType", c.getCompanyEconomyType().getCompanyEconomyType());// 经济类型
			map.put("companyArea", c.getArea().getName());// 地区
			Audit audit= auditService.getByPrimaryKey(year, companyId);
			if(audit==null){
				logger.error("get_comapnmy_information:{}","null");
				return null;
			}
			map.put("companyEmpTotal",audit.getCompanyEmpTotal() + "");// 员工总人数
			map.put("workerHandicapTotal", companyService.getWorkerHandicapTotal(companyId,year)+ "");// 残疾职工总人数
			list.add(map);
			logger.debug("getComapnmyInformationResult:{}", list);
			return list;
		}

		return null;
	}
	
	/**
	 * 验证 档案编码
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validate_companyCode")
	@ResponseBody
	public Boolean validate_companyCode(@RequestParam(value = "param") String param, HttpServletRequest request) {

		if (companyService.getByCompanyCode(param)==null) {
			logger.debug("validate_companyCode:{},Result{}", param, "true");
			return true;
		} else {
			logger.debug("validate_companyCode:{},Result{}", param, "fasle");
			return false;
		}
	}

}
