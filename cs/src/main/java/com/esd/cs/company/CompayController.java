/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.company;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.AuditService;
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
	private AuditService auditService;// 审核
	@Autowired
	private CompanyPropertyService companyPropertyService;// 企业性质

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
		request.setAttribute("nowYear", CalendarUtil.getLastYear());
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
		
		Audit audit= auditService.getByPrimaryKey(CalendarUtil.getLastYear(), id);
		if(audit==null){
			logger.error("get_comapnmy_information:{}","null");
			return null;
		}
		Integer companyEmpTotal=0;
		Integer workerHandicapTotal=0;
		if(audit.getCompanyEmpTotal()!=null){
			companyEmpTotal=audit.getCompanyEmpTotal();
		}
		if(audit.getCompanyHandicapTotal()!=null){
			workerHandicapTotal=audit.getCompanyHandicapTotal();
		}
		request.setAttribute("companyEmpTotal",companyEmpTotal+ "");// 员工总人数
		request.setAttribute("workerHandicapTotal", workerHandicapTotal+"");// 残疾职工总人数
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
		
		Audit audit= auditService.getByPrimaryKey(CalendarUtil.getLastYear(), id);
		if(audit==null){
			logger.error("get_comapnmy_information:{}","null");
			return null;
		}
		Integer companyEmpTotal=0;
		Integer workerHandicapTotal=0;
		if(audit.getCompanyEmpTotal()!=null){
			companyEmpTotal=audit.getCompanyEmpTotal();
		}
		if(audit.getCompanyHandicapTotal()!=null){
			workerHandicapTotal=audit.getCompanyHandicapTotal();
		}
		request.setAttribute("companyEmpTotal",companyEmpTotal+ "");// 员工总人数
		request.setAttribute("workerHandicapTotal", workerHandicapTotal+"");// 残疾职工总人数
		
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
	public Boolean edit_company(Company company, HttpServletRequest request) {
		boolean b =false;
		boolean b1 =false;
		try {
			logger.debug("editCompanyParams:{}", company);
			//更新企业信息
			 b = companyService.update(company);
			//跟新审核信息
			

			
			Audit audit= auditService.getByPrimaryKey(CalendarUtil.getLastYear(), company.getId());
			if(audit==null){
				logger.error("editcomapnmy:{}","null");
				return null;
			}
			
			Integer companyEmpTotal=Integer.valueOf(request.getParameter("companyEmpTotal"));
			Integer companyHandicapTotal=Integer.valueOf(request.getParameter("companyHandicapTotal"));
			// 员工总数
			audit.setCompanyEmpTotal(Integer.valueOf(companyEmpTotal));
			// 残疾员工总数 已录入数
			audit.setCompanyHandicapTotal(companyHandicapTotal);
			b1=auditService.update(audit);
			logger.debug("editCompanyResult:{}", b);
		} catch (Exception e) {
		logger.error("editCompany:{}","error");
		}
		return b && b1;
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

	@RequestMapping(value = "/getinformation/{companyId}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getComapnmyInformation(@PathVariable(value = "companyId") Integer companyId,HttpServletRequest request) {

		logger.debug("get_comapnmy_information:{}", companyId);
		List<Map<String, String>> list = null;
		Map<String, String> map;
		Company c = companyService.getByPrimaryKey(companyId);
		if (c != null) {
			map = new HashMap<String, String>();
			list = new ArrayList<Map<String, String>>();
			map.put("companyName", c.getCompanyName());// 企业名称
			map.put("companyCode", c.getCompanyCode());// 档案编码
			map.put("companyTaxCode", c.getCompanyTaxCode());// 税务编码
			map.put("companyEconomyType", c.getCompanyEconomyType().getCompanyEconomyType());// 经济类型
			map.put("companyArea", c.getArea().getName());// 地区
			Audit audit= auditService.getByPrimaryKey(CalendarUtil.getLastYear(), companyId);
			if(audit==null){
				logger.error("get_comapnmy_information:{}","null");
				return null;
			}
			map.put("companyEmpTotal",audit.getCompanyEmpTotal() + "");// 员工总人数
			map.put("workerHandicapTotal", audit.getCompanyHandicapTotal() + "");// 残疾职工总人数
			list.add(map);
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
		if (companyService.checkCompanyCode(param)) {
			logger.debug("validate_companyCode:{},Result{}", param, "fasle");
			return false;
		} else {
			logger.debug("validate_companyCode:{},Result{}", param, "true");
			return true;
		}
	}

}
