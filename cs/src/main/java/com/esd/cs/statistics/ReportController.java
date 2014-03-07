package com.esd.cs.statistics;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.cs.Constants;
import com.esd.cs.common.CommonUtil;
import com.esd.cs.common.ParameterController;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.cs.worker.WorkerController;
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

	// 一级路径
	@Value("${uploadPath}")
	String uploadPath;

	// 二级路径
	@Value("${companyPath}")
	String companyPath;

	// 制表单位
	@Value("${createTabCompany}")
	String createTabCompany;

	/**
	 * 转到 企业性质报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/nature", method = RequestMethod.GET)
	public ModelAndView nature(HttpServletRequest request, HttpSession session) {
		logger.info("goToPage:{}", "Report_Nature");
		request.setAttribute("year", ParameterController.getYear());
		request.setAttribute("currentTime", CommonUtil.formatData());

		request.setAttribute("createTabCompany", createTabCompany);// 制表公司
		request.setAttribute("createPeople", session.getAttribute(Constants.USER_REAL_NAME).toString());// 制表人
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
	public ModelAndView area(HttpServletRequest request, HttpSession session) {
		logger.info("goToPage:{}", "Report_Area");
		request.setAttribute("year", ParameterController.getYear());
		request.setAttribute("currentTime", CommonUtil.formatData());
		request.setAttribute("createTabCompany", createTabCompany);// 制表公司
		request.setAttribute("createPeople", session.getAttribute(Constants.USER_REAL_NAME).toString());// 制表人

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
	public ModelAndView economytype(HttpServletRequest request, HttpSession session) {
		logger.info("goToPage:{}", "Report_Economytype");
		request.setAttribute("year", ParameterController.getYear());
		request.setAttribute("currentTime", CommonUtil.formatData());
		request.setAttribute("createTabCompany", createTabCompany);// 制表公司
		request.setAttribute("createPeople", session.getAttribute(Constants.USER_REAL_NAME).toString());// 制表人
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

	/**
	 * 导出报表
	 * 
	 * @param type
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/export/{type}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public String export(@PathVariable(value = "type") String type, @PathVariable(value = "year") String year, HttpServletRequest request, HttpSession session) {

		List<ReportViewModel> list = null;
		// 初始化路径信息
		String url = request.getServletContext().getRealPath("/");
		// 检测并创建文件夹
		CommonUtil.chineseFolder(url, uploadPath, companyPath);
		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		// 文件生成路径
		String exportPath = url + uploadPath + File.separator + companyPath + File.separator + uuid + ".xls";

		// 初始化打印参数
		ReportModel model = new ReportModel();
		model.setCreateCompany(createTabCompany);// 制表公司
		model.setCreateData("制表日期：" + CommonUtil.formatData());// 制表日期
		model.setCreatePeople("制表人：" + session.getAttribute(Constants.USER_REAL_NAME).toString());// 制表人

		// 类型判断
		// 单位属性
		if (StringUtils.equals("nature", type)) {
			list = reportViewService.getByCompanyType(year);
			model.setType("单位性质");// 类型
			model.setTitle("年审单位性质汇总表");// 标题
		}
		// 地区
		if (StringUtils.equals("area", type)) {
			list = reportViewService.getByArea(year);
			model.setType("地区");// 类型
			model.setTitle("年审地区汇总表");// 标题
		}
		// 经济类型
		if (StringUtils.equals("economytype", type)) {
			list = reportViewService.getByArea(year);
			model.setType("经济类型");// 类型
			model.setTitle("年审经济类型汇总表");// 标题
		}

		// 生成文件
		PoiCreateExcel.createRepeaExcel(exportPath, list, model);

		String destPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
		// 返回文件下载地址
		String loaddown = "http://" + destPath + "/" + uploadPath + "/" + companyPath + "/" + uuid + ".xls";
		return loaddown;
	}

}
