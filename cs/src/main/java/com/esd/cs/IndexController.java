/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esd.common.captcha.CaptchaService;
import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.hesf.model.Menu;
import com.esd.hesf.model.User;
import com.esd.hesf.model.UserGroup;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.MenuService;
import com.esd.hesf.service.UserGroupService;
import com.esd.hesf.service.UserService;

/**
 * 
 * 主页处理
 * 
 * @author zhangjianzong
 * 
 */
@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService groupService;

	@Autowired
	private AuditParameterService auditParameterService; // 年审参数

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/quit", method = RequestMethod.GET)
	public ModelAndView quitGet(HttpSession session) {
		session.removeAttribute(Constants.USER_ID);
		return new ModelAndView("login");
	}

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet() {
		return new ModelAndView("login");
	}

	/**
	 * 登录提交处理
	 * 
	 * @param request
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginPost(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {

		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
//		String checkCode = request.getParameter("checkCode");

//		logger.debug("userName:{},passWord:{},checkCode:{}", userName, passWord, checkCode);
//
//		CaptchaService captchaService = new CaptchaService();
//		Boolean b = captchaService.checkCode(checkCode, request);
//		logger.debug("checkcode status:{}", b);
//		if (b == false) {
//			redirectAttributes.addFlashAttribute("username", userName);
//			redirectAttributes.addFlashAttribute("password", passWord);
//			redirectAttributes.addFlashAttribute("message", "验证码错误");
//			return new ModelAndView("redirect:/login");
//		}

		User user = userService.getUserByUserName(userName);
		if (user != null && user.getUserName().equals(userName)) {
			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
			String pwd = md5.getMd5(userName, passWord);
			logger.debug("pwd:", pwd);
			if (pwd.equals(user.getUserPassword())) {
				session.setAttribute(Constants.USER_ID, user.getId());
				session.setAttribute(Constants.USER_NAME, user.getUserName());
				session.setAttribute(Constants.USER_REAL_NAME, user.getUserRealName());
				session.setAttribute(Constants.USER_GROUP_ID, user.getUserGroup().getId());
				// 登录成功获得审核年度
				String year = auditParameterService.getLastestYear();
				session.setAttribute(Constants.YEAR, year);
				//如果为查询用户组用户, 则跳转到查询页面
				if(user.getUserGroup().getId().equals(5)){
					return new ModelAndView("redirect:/security/query/audit/listforcompany");
				}
				return new ModelAndView("redirect:/security/index");
			} else {
				redirectAttributes.addFlashAttribute("username", userName);
				redirectAttributes.addFlashAttribute("password", passWord);
				redirectAttributes.addFlashAttribute("message", "用户名密码错误");
			}
		} else {
			redirectAttributes.addFlashAttribute("username", userName);
			redirectAttributes.addFlashAttribute("password", passWord);
			redirectAttributes.addFlashAttribute("message", "用户名密码错误");
			return new ModelAndView("redirect:/login");
		}

		return new ModelAndView("redirect:/login");
	}

	/**
	 * 主页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/security/index", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	/**
	 * 转到主菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/security/main", method = RequestMethod.GET)
	public ModelAndView main() {
		return new ModelAndView("main");
	}

	/**
	 * 获取菜单
	 */
	@RequestMapping(value = "/security/menus", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> jsonMenu(HttpSession session) {
		Integer id = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(id);
		UserGroup userGroup = groupService.getByPrimaryKey(user.getUserGroup().getId());
		List<Menu> menus = menuService.getByPermissionType(userGroup.getPermissionType().getId());
		// List<Menu> menus = menuService.getTreeMap();
		if (menus == null) {
			logger.error("menus:{}", menus);
			return null;
		}
		logger.debug("menus size:{}", menus.size());
		List<Map<String, Object>> list = new ArrayList<>();
		for (Menu menu : menus) {
			Map<String, Object> map = new HashMap<>();
			logger.debug("menu:{}", menu.toString());
			map.put("id", menu.getId());
			map.put("text", menu.getText());
			map.put("state", menu.getState());
			map.put("url", menu.getUrl());
			map.put("checked", menu.getChecked());
			map.put("iconCls", menu.getIconcls());
			map.put("children", menu.getChildren());
			map.put("attributes", menu.getAttributes());
			list.add(map);
		}
		return list;
	}

}
