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

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.common.util.UsernameAndPasswordMd5;
import com.esd.hesf.model.User;
import com.esd.hesf.model.UserGroup;
import com.esd.hesf.service.UserGroupService;
import com.esd.hesf.service.UserService;

/*
 * 系统设置控制器
 */
@Controller
@RequestMapping(value = "/security/settings/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	/**
	 * 转到 用户
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView user() {
		return new ModelAndView("settings/user_list");
	}

	/**
	 * 查询用户列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows, HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		try {
			User user = new User();
			PaginationRecordsAndNumber<User, Number> query = null;
			query = userService.getPaginationRecords(user, page, rows);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<User> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				User it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("userName", it.getUserName());// 用户登陆名
				map.put("userRealName", it.getUserRealName());// 用户真实姓名
				map.put("userMobile", it.getUserMobile());// 电话
				map.put("userGroup", it.getUserGroup().getUserGroupName());// 所在用户组
				map.put("userStatus", it.getUserStatus());// 用户使用状态
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("total:{},rows:{}", total, list.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
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
	 * 跳转到添加用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addGet() {
		List<UserGroup> list = userGroupService.getAll();
		logger.debug("UserGroups:{}", list);
		return new ModelAndView("settings/user_detail_save","group",list);
	}

	@RequestMapping(value = "/checkName", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserName(@RequestParam(value = "userName") String userName) {
		User checkUserName = userService.getUserByUserName(userName);
		if (checkUserName != null) {
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/checkMobile", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserMobile(@RequestParam(value = "userMobile") String userMobile) {
		User checkUserMobile = userService.getUserByUserMobile(userMobile);
		if (checkUserMobile != null) {
			return false;
		}
		return true;
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addPost(User user, HttpServletRequest request) {
		logger.debug("id:{}", user.getId());
		logger.debug(user.toString());
		User checkUserName = userService.getUserByUserName(user.getUserName());
		if (checkUserName != null) {
			return false;
		}
		User checkUserMobile = userService.getUserByUserMobile(user.getUserMobile());
		if (checkUserMobile != null) {
			return false;
		}
		String passWord = user.getUserPassword();
		String userName = user.getUserName();
		UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
		String pwd = md5.getMd5(userName, passWord);
		user.setUserPassword(pwd);
		userService.save(user);
		return true;
	}

	/**
	 * 更新用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updatePost(User user) {
		logger.debug(user.toString());
		return userService.update(user);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Boolean deletePost(@RequestParam(value = "params[]") int[] ids) {
		logger.debug(ids.toString());
		for (int i = 0; i < ids.length; i++) {
			if (userService.delete(ids[i]) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/change/{id}", method = RequestMethod.GET)
	public ModelAndView changeGet(@PathVariable("id") int id) {
		User user = userService.getByPrimaryKey(id);
		return new ModelAndView("settings/user_change_pwd", "entity", user);
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	@ResponseBody
	public Boolean changePost(@RequestParam("id") Integer id, @RequestParam("userPassword") String userPassword) {
		logger.debug("id:{},password:{}", id, userPassword);
		User user = userService.getByPrimaryKey(id);
		if (user == null) {
			return false;
		}
		if (StringUtils.isNotBlank(userPassword)) {
			UsernameAndPasswordMd5 md5 = new UsernameAndPasswordMd5();
			String pwd = md5.getMd5(user.getUserName(), userPassword);
			user.setUserPassword(pwd);
			userService.update(user);
			return true;
		}
		return false;
	}

	/**
	 * 跳转到添加用户
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable("id") int id,HttpServletRequest request) {
		logger.debug("id:{}", id);
		User user = userService.getByPrimaryKey(id);
		List<UserGroup> list = userGroupService.getAll();
		logger.debug("UserGroups:{}", list);
		request.setAttribute("group", list);
		return new ModelAndView("settings/user_detail_update", "entity", user);
	}
}
