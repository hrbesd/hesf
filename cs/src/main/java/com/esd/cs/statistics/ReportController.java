package com.esd.cs.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.cs.common.CommonUtil;
import com.esd.cs.worker.WorkerController;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.User;
import com.esd.hesf.service.ReportViewService;
import com.esd.hesf.viewmodels.ReportViewModel;

/**
 * 报表控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/security/report")
public class ReportController {
	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private ReportViewService reportViewService;

	@RequestMapping(value = "/createExcle", method = RequestMethod.GET)
	public ModelAndView createExcle(HttpServletRequest request) {
	
		return new ModelAndView("report/dowanloadArea");
	}
	/**
	 * 转到 企业性质报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/nature", method = RequestMethod.GET)
	public ModelAndView nature(HttpServletRequest request) {
		logger.info("goToPage:{}", "Report_Nature");
		request.setAttribute("year", CalendarUtil.getNowYear());
		request.setAttribute("currentTime", CommonUtil.formatData());
		return new ModelAndView("report/nature");
	}

	/**
	 * 获取 企业性质 数据
	 * 
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/notice/{year}", method = RequestMethod.POST)
	@ResponseBody
	public Object get_company(@PathVariable(value = "year") String year, HttpServletRequest request) {
		logger.debug("printNoticeParamsYear:{}", year);
		List<ReportViewModel> list = reportViewService.getByCompanyType(year);

		logger.debug("printNoticeResult:{}", list.size());
		return list;
	}

	/**
	 * 转到 地区报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public ModelAndView area(HttpServletRequest request) {
		logger.info("goToPage:{}", "Report_Area");
		request.setAttribute("year", CalendarUtil.getNowYear());
		request.setAttribute("currentTime", CommonUtil.formatData());
		return new ModelAndView("report/area");
	}

	/**
	 * 获取 地区 数据
	 * 
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/area/{year}", method = RequestMethod.POST)
	@ResponseBody
	public Object getarea(@PathVariable(value = "year") String year, HttpServletRequest request) {
		logger.debug("printareaParamsYear:{}", year);
		List<ReportViewModel> list = reportViewService.getByArea(year);
		logger.debug("printareaResult:{}", list.size());
		return list;
	}

	/**
	 * 转到 经济类型 报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/economytype", method = RequestMethod.GET)
	public ModelAndView economytype(HttpServletRequest request) {
		logger.info("goToPage:{}", "Report_Economytype");
		request.setAttribute("year", CalendarUtil.getNowYear());
		request.setAttribute("currentTime", CommonUtil.formatData());
		return new ModelAndView("report/economytype");
	}

	/**
	 * 获取 经济类型 数据
	 * 
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/economytype/{year}", method = RequestMethod.POST)
	@ResponseBody
	public Object geteconomytype(@PathVariable(value = "year") String year, HttpServletRequest request) {
		logger.debug("printarEconomytypeParamsYear:{}", year);
		List<ReportViewModel> list = reportViewService.getByCompanyEconomyType(year);
		logger.debug("printEconomytypeResult:{}", list.size());
		return list;
	}
}
