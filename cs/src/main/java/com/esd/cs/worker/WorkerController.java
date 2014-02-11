/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.WorkerService;

/*
 * 残疾职工控制器
 * 
 * @author yuxingliang
 */
@Controller
@RequestMapping(value = "/security/worker")
public class WorkerController {

	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);
	@Autowired
	private WorkerService workerService;// 工作者
	@Autowired
	private CompanyService companyService;// 企业

	/**
	 * 转到残疾职工列表页面 初审时利用tab标签页的post方式获取。 所以get和post都可以请求，
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/{companyId}/{year}")
	public ModelAndView worker_list(@PathVariable(value = "companyId") String companyId, @PathVariable(value = "year") String year, HttpServletRequest request) {
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		//男职工退休年龄
		request.setAttribute("maleRetirementAge", "60");
		//女职工退休年龄
		request.setAttribute("femaleRetirementAge", "50");
		
		logger.debug("goToPage:{}", "转到残疾职工列表页面");
		return new ModelAndView("basicInfo/worker_list");

	}

	@RequestMapping(value = "/get_comapnmy_information/{companyId}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getComapnmyInformation(@PathVariable(value = "companyId") String companyId, @PathVariable(value = "year") String year, HttpServletRequest request) {

		logger.debug("get_comapnmy_information:{},year:{}", companyId, year);
		List<Map<String, String>> list = null;
		Map<String, String> map;
		Company c = companyService.getByPrimaryKey(companyId);
		if (c != null) {
			map = new HashMap<String, String>();
			list = new ArrayList<Map<String, String>>();
			map.put("companyName", c.getCompanyName());// 企业名称
			map.put("companyCode", c.getCompanyCode());// 档案编码
			map.put("companyTaxCode", c.getCompanyTaxCode());// 税务编码
			map.put("companyEmpTotal", c.getCompanyEmpTotal() + "");// 员工总人数
			map.put("companyEconomyType", c.getCompanyEconomyType().getCompanyEconomyType());// 经济类型
			map.put("companyArea", c.getArea().getName());// 地区
			map.put("workerHandicapTotal", companyService.getWorkerHandicapTotal(c.getCompanyCode(), year) + "");// 残疾职工总人数
			list.add(map);
			return list;
		}

		return null;
	}

	/**
	 * 转到增加残疾职工页面
	 */
	@RequestMapping(value = "/add/{companyId}", method = RequestMethod.GET)
	public ModelAndView add_worker(@PathVariable(value = "companyId") String companyId, HttpServletRequest request) {
		// 续传企业id
		request.setAttribute("companyId", companyId);
		logger.info("goToPage:{}", "添加残疾职工页面");
		return new ModelAndView("basicInfo/add_worker");
	}

	/**
	 * 增加残疾职工
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean add_worker(Worker worker, HttpServletRequest request) {

		String companyId = request.getParameter("companyId");
		logger.debug("addWorkerParams:{},companyId:{}", worker, companyId);
		Company c = companyService.getByPrimaryKey(companyId);
		if(c==null){
			
			logger.error("addWorker_getCompanyError:{}","null");
			return false;
		}
		boolean b = workerService.save(worker, c.getCompanyCode());
		logger.debug("addWorkerResult{}", b);
		return b;
	}

	/**
	 * 转到编辑残疾职工页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editWorker(@PathVariable(value = "id") Integer id, HttpServletRequest request) {
		// 续传企业id
		logger.debug("editWorkerParamsID:{}", id);
		Worker w = workerService.getByPrimaryKey(id);
		request.setAttribute("worker", w);
		logger.debug("goToedit_worker{}", w);
		return new ModelAndView("basicInfo/edit_worker");
	}

	/**
	 * 编辑残疾职工
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public Boolean edit_worker(Worker worker, HttpServletRequest request) {
		logger.debug("editWorker:{}", worker);
		boolean b = workerService.update(worker);
		logger.debug("editWorkerResult:{}", b);
		return b;
	}

	/**
	 * 编辑残疾职工 用于企业录用已经存在残疾职工 获取版本号，在进行更新
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updata", method = RequestMethod.POST)
	@ResponseBody
	public Boolean edit_worker_up(Worker worker, HttpServletRequest request) {
		logger.debug("editUpdata:{}", worker);
		String companyId = request.getParameter("companyId");
		boolean workerUpDataStatus = false, companyUpdataStatus = false;

		try {
			// 根据残疾证号获取版本号
			Worker w = workerService.getByWorkerHandicapCode(worker.getWorkerHandicapCode());
			// set版本号
			worker.setVersion(w.getVersion());
			// 设置id
			worker.setId(w.getId());
			// 更新员工信息
			workerUpDataStatus = workerService.update(worker);
			logger.debug("workerUpDataResult:{}", workerUpDataStatus);
			if (workerUpDataStatus) {
				// 员工信息更新成功，进行员工和录用企业之间关联更新
				Company c = companyService.getByPrimaryKey(companyId);
				if (c != null) {

					logger.debug("upData_workerCompanyParamsWorkerId:{},companyCode:{},year:{},workerCurrenJob:{}", worker.getId(), c.getCompanyCode(), CalendarUtil.getNowYear(), worker.getCurrentJob());
					companyUpdataStatus = workerService.changeCompany(worker.getId(), c.getCompanyCode(), CalendarUtil.getNowYear(), worker.getCurrentJob());
					logger.debug("workerUpDataGetCompanyResult:{}", companyUpdataStatus);
				}

			}

		} catch (Exception e) {
			logger.error("workerUpdataError:{}", e.getMessage());
		}
		return workerUpDataStatus && companyUpdataStatus;
	}

	/**
	 * 删除残疾职工
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delete_worker(@RequestParam(value = "params[]") Integer params[], @RequestParam(value = "companyId") String companyId, @RequestParam(value = "year") String year,

	HttpServletRequest request) {

		logger.debug("deleteWorkerParamsID:{},years:{},companyId:{}", params, year, companyId);
		try {
			for (int i = 0; i < params.length; i++) {
				boolean b = companyService.deleteWorkerFromCompany(year, companyService.getByPrimaryKey(companyId).getCompanyCode(), params[i]);
				logger.debug("delete_worker:{},result:{}", params[i], b);
			}
		} catch (Exception e) {
			logger.error("delete_worker{}", e.getMessage());
		}
		return true;
	}

	/**
	 * 验证 残疾证号是否存在，是否在其他公司内
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validate_workerHandicapCode")
	@ResponseBody
	public List<Map<String, String>> validate_companyOrganizationCode(@RequestParam(value = "workerIdCard") String workerIdCard, HttpServletRequest request) {

		logger.debug("validate_workerIdCardParams:{}", workerIdCard);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		Company company = workerService.retrieveCompanyByWorker(CalendarUtil.getNowYear(), workerIdCard);
		// 第一种情况 存在，并且在其他公司内。
		if (company != null) {
			logger.debug("validate_workerHandicapCodeResult:{}", "trpe:1。存在其他公司内");
			paramsMap.put("type", "1");
			Map<String, String> companyMap = new HashMap<String, String>();
			companyMap.put("companyName", company.getCompanyName());
			companyMap.put("companyCode", company.getCompanyCode());
			list.add(paramsMap);
			list.add(companyMap);
			return list;
		}
		// 没有在任何公司
		// 第二种情况：存在，并且没有在任何公司。
		// 第三种情况，不存在.
		else {

			Worker w = workerService.getByWorkerIdCard(workerIdCard);
			// 存在，并且不再任何公司
			if (w != null) {
				logger.debug("validate_workerHandicapCodeResult:{}", "trpe:2。存在，并且不再任何公司");
				paramsMap.put("type", "2");
				list.add(paramsMap);
				return list;
				// 不存在
			} else {
				logger.debug("validate_workerHandicapCodeResult:{}", "trpe:3。不存在");
				paramsMap.put("type", "3");
				list.add(paramsMap);
				return list;

			}

		}

		/**
		 * 参数 年份 残疾证号
		 * 
		 * 1.存在，并且在其他公司内 返回公司对象，前台提示在哪个公司内 2.存在，不在其他公司内。 返回公司id，前台调用更新方法 3.不存在
		 * 
		 */

	}
}
