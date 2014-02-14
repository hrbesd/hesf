/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.documentation;

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
@RequestMapping(value = "/security/print")
public class PrintCompany {
	private static final Logger logger = LoggerFactory.getLogger(PrintCompany.class);

	@Autowired
	private CompanyService companyService;// 企业
	@Autowired
	private CompanyTypeService companyTypeService;// 企业类型
	@Autowired
	private CompanyPropertyService companyPropertyService;// 企业性质
	@Autowired
	private CompanyEconomyTypeService companyEconomyTypeService;// 企业经济类型

	/**
	 * 转到打印列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView addCompany(HttpServletRequest request) {
		// 获取当前年份
		request.setAttribute("nowYear", CalendarUtil.getNowYear());
		logger.debug("gotoPrintList:{}", "queryAudit");
		return new ModelAndView("documents/print_list");
	}

	/**
	 * 转到打印审核表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/audittab/{id}", method = RequestMethod.GET)
	public ModelAndView audittab(@PathVariable(value = "id") String id, HttpServletRequest request) {
		logger.debug("gotoPrint_audit");
		return new ModelAndView("documents/print_audit");
	}

	/**
	 * 转到打印职工列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/workerlist/{id}", method = RequestMethod.GET)
	public ModelAndView workerlist(@PathVariable(value = "id") String id, HttpServletRequest request) {
		logger.debug("gotoPrint_workerList");
		return new ModelAndView("documents/print_workerList");
	}

	/**
	 * 转到打印催缴通知书页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notice/{id}", method = RequestMethod.GET)
	public ModelAndView notice(@PathVariable(value = "id") String id, HttpServletRequest request) {
		logger.debug("gotonotice");
		return new ModelAndView("documents/print_notice");
	}

}
