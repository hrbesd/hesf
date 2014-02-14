/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.company;

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
import com.esd.hesf.model.Company;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.CompanyPropertyService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.CompanyTypeService;

@Controller
@RequestMapping(value = "/security/company")
public class CompayController {
	private static final Logger logger = LoggerFactory.getLogger(CompayController.class);

	@Autowired
	private CompanyService companyService;// 企业
	@Autowired
	private CompanyTypeService companyTypeService;// 企业类型
	@Autowired
	private CompanyPropertyService companyPropertyService;// 企业性质
	@Autowired
	private CompanyEconomyTypeService companyEconomyTypeService;// 企业经济类型

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
		// 获取当前年份
		request.setAttribute("nowYear", CalendarUtil.getNowYear());
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
	public ModelAndView view_company(@PathVariable(value = "id") String id) {
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
	public ModelAndView editCompany(@PathVariable(value = "id") String id) {
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
			logger.debug("getByIdCompany");
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
	 * 获取省直企业页面数据
	 * 
	 * @param page
	 * @param rows
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> companyPost(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows, CompanyParamModel params, HttpServletRequest request) {

		logger.debug("getCompanyListParams:{}", params);
		Map<String, Object> entity = new HashMap<>();
		try {
			Company company = new Company();
			company.setCompanyProperty(companyPropertyService.getByPrimaryKey(params.getCompanyProperty()));//
			// 企业类型
			company.setCompanyCode(params.getCompanyCode());// 档案号
			company.setCompanyTaxCode(params.getCompanyTaxCode());// 税务编码
			company.setCompanyName(params.getCompanyName());// 企业名称
			company.setYear(params.getYear());// 年度
			PaginationRecordsAndNumber<Company, Number> query = null;
			query = companyService.getPaginationRecords(company, page, rows);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<Company> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				Company it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("documentcode", it.getCompanyCode());// 档案编码
				map.put("organizationcode", it.getCompanyTaxCode());// 税务编码
				map.put("name", it.getCompanyName());// 企业名称
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("getCompanyListResult:{}", list.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return entity;
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
		logger.debug("addCompany{}", company);
		try {

			if (company == null) {
				logger.error("addCompany{}", "paramserror");
				return false;
			}
			company.setYear(CalendarUtil.getNowYear());
			company.setCompanyCode(CompanyUtil.getCompanyCode());

			boolean b = companyService.save(company);
			logger.debug("addCompany{}", b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addCompany{}", "adderror");
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
	public boolean deleteCompany(@RequestParam(value = "params[]") String idArr[], HttpServletRequest request) {
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

}
