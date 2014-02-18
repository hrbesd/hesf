package com.esd.cs.statistics;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.esd.cs.worker.WorkerController;

@Controller
@RequestMapping(value = "/security/report")
public class ReportController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public ModelAndView area(HttpServletRequest request) {
		logger.info("goToPage:{}", "Report_Area");
		return new ModelAndView("report/area");
	}
	
	
	/**
	 * 转到 报表 企业性质
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/nature", method = RequestMethod.GET)
	public ModelAndView nature(HttpServletRequest request) {
		logger.info("goToPage:{}", "Report_Nature");
		return new ModelAndView("report/nature");
	}
	
	/**
	 * 转到 报表 经济类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/economytype", method = RequestMethod.GET)
	public ModelAndView economytype(HttpServletRequest request) {
		logger.info("goToPage:{}", "Report_Economytype");
		return new ModelAndView("report/economytype");
	}
	
}
