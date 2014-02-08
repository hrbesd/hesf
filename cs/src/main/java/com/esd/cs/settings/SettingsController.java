package com.esd.cs.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 系统设置控制器
 */
@Controller
@RequestMapping(value = "/settings")
public class SettingsController {
	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);

}
