/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.settings;

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
import com.esd.cs.Constants;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.UserGroup;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.UserGroupService;

/**
 * 年审参数
 */
@Controller
@RequestMapping(value = "/security/settings/yearAuditParameter")
public class YearAuditParameterController {
	private static final Logger logger = LoggerFactory
			.getLogger(YearAuditParameterController.class);

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private AuditParameterService auditParameterService;
	@Autowired
	private AuditService auditService;

	/**
	 * 转到年审参数
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView user() {
		logger.debug("yearAuditParameter");
		return new ModelAndView("settings/parameter_list");
	}

	/**
	 * 查询用户列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "page") Integer page,
			@RequestParam(value = "rows") Integer rows,
			HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		String year = request.getParameter("year");
		try {
			AuditParameter auditParameter = new AuditParameter();
			if (StringUtils.isNotBlank(year)) {
				auditParameter.setYear(year);
			}
			PaginationRecordsAndNumber<AuditParameter, Number> query = null;
			query = auditParameterService.getPaginationRecords(auditParameter,
					page, rows);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<AuditParameter> iterator = query.getRecords()
					.iterator(); iterator.hasNext();) {
				AuditParameter it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("year", it.getYear());// 年度
				map.put("areaCode", it.getArea().getName());// 地区
				map.put("averageSalary", it.getAverageSalary());// 计算基数
				map.put("putScale", it.getPutScale());// 安置比例
				String payCloseDate = CalendarUtil.dateFormat(it
						.getPayCloseDate());
				map.put("payCloseDate", payCloseDate);// 支付截至日期
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("total:{},rows:{}", total, list.toString());

		} catch (Exception e) {
			logger.error("error{}", e);
		}
		return entity;
	}

	/**
	 * 获得用户组
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/group")
	@ResponseBody
	public List<UserGroup> getUserGroup() {
		List<UserGroup> list = userGroupService.getAll();
		logger.debug("UserGroups:{}", list);
		return list;
	}

	/**
	 * 跳转到添加年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addGet(HttpServletRequest request) {
		String year = CalendarUtil.getBeforeYear();
		request.setAttribute("year", year);
		return new ModelAndView("settings/parameter_save");
	}

	/**
	 * 添加年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPost(AuditParameter auditParameter,
			HttpServletRequest request, HttpSession session) {
		logger.debug("auditParameter:{}", auditParameter);
		Boolean copy = Boolean.valueOf(request.getParameter("copy"));
		Map<String, Object> map = new HashMap<String, Object>();
		if (copy) {
			// 检查审核表中该年份的审核数据是否存在,如不存在-则进行复制以产生审核数据, 如存在-则不进行复制
			Boolean isExist = false;
			String[] years = auditService.getAuditYears();
			for (String year : years) {
				if (auditParameter.getYear().equals(year)) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				// 产生审核数据
				try {
					String currentYear = auditParameter.getYear(); // 获取指定要审核的年
					if (currentYear != null) {
						auditService.initAuditData(currentYear);
					}
				} catch (Exception e) {
					logger.error("copy audit date error", e);
					map.put(Constants.NOTICE, "产生审核数据失败");
					return map;
				}
			}
		}
		try {
			// 验证该年度的审核参数是否存在
			AuditParameter tempAup = auditParameterService
					.getByYear(auditParameter.getYear());
			if (tempAup != null) {
				map.put(Constants.NOTICE, "该年度的审核数据已经存在");
				return map;
			}
			auditParameterService.save(auditParameter);
			String year = auditParameterService.getLastestYear();
			session.setAttribute(Constants.YEAR, year);
			map.put(Constants.NOTICE, true);
		} catch (Exception e) {
			logger.error("add audit year parms error", e);
			map.put(Constants.NOTICE, "保存审核数据失败");
		}
		return map;

	}

	/**
	 * 更新年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updatePost(AuditParameter auditParameter) {
		logger.debug(auditParameter.toString());
		return auditParameterService.update(auditParameter);
	}

	/**
	 * 批量删除年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Boolean deletePost(@RequestParam(value = "params[]") int[] ids) {
		logger.debug(ids.toString());
		for (int i = 0; i < ids.length; i++) {
			if (auditParameterService.delete(ids[i]) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 编辑年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view/{year}")
	public ModelAndView editGetByYear(@PathVariable("year") String year) {
		logger.debug("year:{}", year);
		AuditParameter auditParameter = auditParameterService.getByYear(year);
		return new ModelAndView("settings/parameter_edit", "entity",
				auditParameter);
	}

	/**
	 * 编辑年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable("id") int id) {
		logger.debug("id:{}", id);
		AuditParameter auditParameter = auditParameterService
				.getByPrimaryKey(id);
		return new ModelAndView("settings/parameter_edit", "entity",
				auditParameter);
	}
}
