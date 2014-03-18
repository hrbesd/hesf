/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esd.common.captcha.CaptchaService;

/**
 * 验证 码
 * 
 * @author zhangjianzong
 * 
 */
@Controller
@RequestMapping(value = "/captcha")
public class CaptchaController {
	private static Logger logger = Logger.getLogger(CaptchaController.class);

	@RequestMapping(value = "/create")
	public void createCheckCode(HttpServletRequest request, HttpServletResponse response) {
		CaptchaService captchaService = new CaptchaService();
		captchaService.createCaptchaImage(request, response);
		logger.debug("captcha create finish");
	}
}
