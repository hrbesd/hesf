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

import com.esd.common.util.CalendarUtil;
import com.esd.cs.Constants;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;

@Controller
@RequestMapping(value = "/security/company")
public class CompayController {
	private static final Logger logger = LoggerFactory
			.getLogger(CompayController.class);

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
	public ModelAndView companyGet(
			@PathVariable(value = "property") String property,
			HttpServletRequest request) {
		logger.debug("companyProperty{}", property);
		if (Constants.LURU.equals(property)) {
			return new ModelAndView("audit/audit_create_list");
		}
		request.setAttribute("companyProperty", property);
		return new ModelAndView("basicInfo/company_list");
	}

	/**
	 * 转到 增加企业信息 页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add/{companyProperty}", method = RequestMethod.GET)
	public ModelAndView addCompany(
			@PathVariable("companyProperty") Integer companyProperty,
			HttpServletRequest request) {
		logger.debug("gotoaddCompany");
		request.setAttribute("companyProperty", companyProperty);
		return new ModelAndView("basicInfo/add_company");
	}

	/**
	 * 转到 查看企业信息 页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view_company(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
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
	public ModelAndView editCompany(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
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
	public Object get_company(@RequestParam(value = "id") Integer id,
			HttpServletRequest request) {

		try {
			logger.debug("getByIdCompanyId:{}", id);
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
	 * 根据公司档案code获取单位对象
	 * 
	 * @param companyCode
	 * @return
	 */
	@RequestMapping(value = "/getCompanyByCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCompanyByCode(HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		try {
			String companyCode = request.getParameter("companyCode");
			logger.debug("companyCode:{}", companyCode);
			Company company = companyService.getByCompanyCode(companyCode);
			logger.debug(" getcompany{}", company);
			entity.put("entity", company);
			return entity;
		} catch (Exception e) {
			logger.error("获取企业对象发生错误");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据公司组织机构代码获取单位对象
	 * 
	 * @param companyCode
	 * @return
	 */
	@RequestMapping(value = "/getCompanyByOrganizationCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getCompanyByOrganizationCode(
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<String, Object>();
		try {
			String companyOrganizationCode = request
					.getParameter("companyOrganizationCode");
			logger.debug("companyOrganizationCode:{}", companyOrganizationCode);
			Company company = companyService
					.getByCompanyOrganizationCode(companyOrganizationCode);
			logger.debug(" getcompany{}", company);
			entity.put("entity", company);
			return entity;
		} catch (Exception e) {
			logger.error("获取企业对象发生错误");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加企业信息方法1
	 * 
	 * @param company
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean add_company(Company company, HttpServletRequest request,
			HttpSession session) {
		logger.debug("addCompany:{}", company);
		try {
			if (company == null) {
				logger.error("addCompany:{}", "paramserror");
				return false;
			}
			String nowYear = (String) session.getAttribute(Constants.YEAR);
			// 检查该公司是否存在, 不存在则保存, 存在则更新
			boolean b;
			Company tempCompany = companyService.getByCompanyCode(company
					.getCompanyCode());
			if (tempCompany == null) {
				b = companyService.save(company);
			} else {
				company.setId(tempCompany.getId());
				b = companyService.update(company);
				// 同时检查下是否有员工, 有的话则将关系复制一份
				Integer workerCount = companyService.getWorkerHandicapTotal(
						company.getId(), CalendarUtil.getBeforeYear());
				if (workerCount > 0) {
					companyService.copyLastYearWorker(
							CalendarUtil.getNowYear(),
							CalendarUtil.getBeforeYear(), company.getId());
				}
			}
			// 如果选中创建当年审核数据
			String createAudit = request.getParameter("createAudit");
			if (createAudit != null && "1".equals(createAudit)) {
				Audit audit = new Audit();
				audit.setCompany(company);
				audit.setYear(nowYear);
				auditService.save(audit);
			}
			// 检查一下,
			logger.debug("addCompanyResult:{}", b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addCompany:{}", "adderror");
		}
		return false;
	}

	/**
	 * 增加企业信息方法2
	 * 
	 * @param company
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addCompany", method = RequestMethod.POST)
	@ResponseBody
	public Integer add_company2(Company company, HttpServletRequest request,
			HttpSession session) {
		System.out.println("///////////////**************************");
		logger.debug("addCompany:{}", company);
		try {
			if (company == null) {
				logger.error("addCompany:{}", "paramserror");
				return -1;
			}
			String nowYear = (String) session.getAttribute(Constants.YEAR);
			// 检查该公司是否存在, 不存在则保存, 存在则更新
			boolean b;
			Company tempCompany = companyService.getByCompanyCode(company
					.getCompanyCode());
			if (tempCompany == null) {
				b = companyService.save(company);
			} else {
				company.setId(tempCompany.getId());
				company.setVersion(tempCompany.getVersion());
				b = companyService.update(company);
				// 同时检查下是否有员工, 有的话则将关系复制一份
				Integer workerCount = companyService.getWorkerHandicapTotal(
						company.getId(), CalendarUtil.getBeforeYear());
				if (workerCount > 0) {
					companyService.copyLastYearWorker(
							CalendarUtil.getNowYear(),
							CalendarUtil.getBeforeYear(), company.getId());
				}
			}

			// 检查是否存在当年的审核数据, 如不存在则创建
			Audit audit = auditService
					.getByPrimaryKey(nowYear, company.getId());
			if (audit == null) {
				audit = new Audit();
				audit.setCompany(company);
				audit.setYear(nowYear);
				auditService.save(audit);
			}
			// // 如果选中创建当年审核数据
			// String createAudit = request.getParameter("createAudit");
			// if (createAudit != null && "1".equals(createAudit)) {
			// Audit audit = new Audit();
			// audit.setCompany(company);
			// audit.setYear(nowYear);
			// auditService.save(audit);
			// }
			// 检查一下,
			logger.debug("addCompanyResult:{}", b);
			// 返回创建/更新的公司id
			return company.getId();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addCompany:{}", "adderror");
		}
		return -1;
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
	public boolean deleteCompany(
			@RequestParam(value = "params[]") Integer idArr[],
			HttpServletRequest request) {
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
	public Boolean validate_companyOrganizationCode(
			@RequestParam(value = "param") String param,
			HttpServletRequest request) {
		Company c = companyService.getByCompanyOrganizationCode(param);
		if (c == null) {
			logger.debug("validateCompanyOrganizationCode{} Result{}", param,
					"true");
			return true;
		} else {
			logger.debug("validateCompanyOrganizationCode{} Result{}", param,
					"false");
			return false;
		}
	}

	/**
	 * 根据企业id 获取企业信息
	 * 
	 * @param companyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getinformation/{companyId}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getComapnmyInformation(
			@PathVariable(value = "companyId") Integer companyId,
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
			map.put("companyEconomyType", c.getCompanyEconomyType()
					.getCompanyEconomyType());// 经济类型
			map.put("companyArea", c.getArea().getName());// 地区
			Audit audit = auditService.getByPrimaryKey(year, companyId);
			if (audit == null) {
				logger.error("get_comapnmy_information:{}", "null");
				return null;
			}
			map.put("companyEmpTotal", audit.getCompanyEmpTotal() + "");// 员工总人数
			map.put("workerHandicapTotal",
					companyService.getWorkerHandicapTotal(companyId, year) + "");// 残疾职工总人数
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
	public Boolean validate_companyCode(
			@RequestParam(value = "param") String param,
			HttpServletRequest request) {

		if (companyService.getByCompanyCode(param) == null) {
			logger.debug("validate_companyCode:{},Result{}", param, "true");
			return true;
		} else {
			logger.debug("validate_companyCode:{},Result{}", param, "fasle");
			return false;
		}
	}

	/**
	 * 验证 公司名称
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validate_companyName")
	@ResponseBody
	public Boolean validate_companyName(
			@RequestParam(value = "param") String param,
			HttpServletRequest request) {
		if (param == null || "".equals(param)) {
			return false;
		}
		if (companyService.getByCompanyName(param) == null) {
			logger.debug("validate_companyName:{},Result{}", param, "true");
			return true;
		} else {
			logger.debug("validate_companyName:{},Result{}", param, "fasle");
			return false;
		}
	}

	/**
	 * 返回一个自动递增的companyCode
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRandomCode")
	@ResponseBody
	public Map<String, Object> getNextCompanyCode() {
		Map<String, Object> map = new HashMap<String, Object>();
		String randomCode = companyService.getNextCompanyCode();
		map.put("randomCode", randomCode);
		return map;
	}

}
