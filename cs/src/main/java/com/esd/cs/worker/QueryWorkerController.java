/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.worker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.WorkerService;
import com.esd.hesf.viewmodels.WorkerViewModel;

@Controller
@RequestMapping(value = "/security/query/worker")
public class QueryWorkerController {
	private static final Logger logger = LoggerFactory.getLogger(QueryWorkerController.class);
	@Autowired
	private WorkerService workerService;// 工作者
	@Autowired
	private AuditParameterService auditParameterService;// 年审参数

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView worker_list(HttpServletRequest request) {
		logger.debug("goToPage:{}", "转到残疾职工列表页面");
		return new ModelAndView("query/worker");

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerList(WorkerParamModel params, HttpServletRequest request) {
		logger.debug("queryWorkerParams{}", params);
		Map<String, Object> entity = new HashMap<>();
		Integer total = 0;
		try {

			Map<String, Object> paramsMap = new HashMap<String, Object>();

			paramsMap.put("workerHandicapCode", params.getWorkerHandicapCode()); // 残疾证号
			paramsMap.put("careerCard", params.getCareerCard()); // 就业证号
			paramsMap.put("workerName", params.getWorkerName()); // 姓名
			paramsMap.put("workerGender", params.getWorkerGender()); // 性别
			paramsMap.put("currentJob", params.getCurrentJob()); // 当前岗位
			paramsMap.put("minAge", params.getWorkerAge_1()); // 最小年龄
			paramsMap.put("maxAge", params.getWorkerAge_2()); // 最大年龄
			paramsMap.put("workerHandicapType", params.getWorkerHandicapType()); // 残疾类别
																					// 对应的id
			paramsMap.put("workerHandicapLevel", params.getWorkerHandicapLevel()); // 残疾等级
																					// 对应的id
			paramsMap.put("page", params.getPage()); // 分页--起始页
														// ******************************
			paramsMap.put("pageSize", params.getRows());// 分页--返回量
														// ******************************
			PaginationRecordsAndNumber<WorkerViewModel, Number> query = workerService.getByMultiCondition(paramsMap);
			total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<WorkerViewModel> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				WorkerViewModel it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("workerName", it.getWorkerName());// 姓名
				map.put("workerHandicapCode", it.getWorkerHandicapCode());// 残疾证号
				if (it.getWorkerGender() == null) {
					map.put("workerGender", "未知");// 性别
				} else {
					if (it.getWorkerGender().equals("0")) {
						map.put("workerGender", "女");// 性别
					} else {
						map.put("workerGender", "男");// 性别
					}
				}
				// 计算年龄 传入残疾证号，参数错误返回-1
				map.put("workerAge", WorkerUtil.conversionAge(it.getWorkerHandicapCode()));
				map.put("phone", it.getPhone());// phone
				map.put("workerHandicapType", it.getWorkerHandicapType().getHandicapType());// 残疾类别
				map.put("workerHandicapLevel", it.getWorkerHandicapLevel().getHandicapLevel());// 残疾等级

				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.debug("queryWorker:{}", total);
		return entity;
	}

	/**
	 * 企业获取残疾职工列表
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/company_worker_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> companyHireList(WorkerParamModel params, HttpServletRequest request) {
		logger.debug("queryCompanyWorkerParams{}", params);

		Map<String, Object> entity = new HashMap<>();
		Integer total = 0;
		List<Map<String, Object>> list = null;
		// 获取年审参数
		AuditParameter auditParam = auditParameterService.getByYear(params.getYear());
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("getOverproof", params.getIsExceed()); // 是否获取超过退休年龄
			paramsMap.put("year", params.getYear()); // 公司id
			paramsMap.put("companyId", params.getCompanyId()); // 公司id
			paramsMap.put("workerName", params.getWorkerName()); // 姓名
			paramsMap.put("workerHandicapCode", params.getWorkerHandicapCode()); // 残疾证号
			paramsMap.put("workerGender", params.getWorkerGender()); // 性别
			paramsMap.put("maxAge", params.getWorkerAge_2()); // 最大年龄
			paramsMap.put("minAge", params.getWorkerAge_1()); // 最小年龄
			paramsMap.put("phone", params.getPhone()); // 电话
			paramsMap.put("workerHandicapType", params.getWorkerHandicapType()); // 残疾类别
																					// 对应的id
			paramsMap.put("workerHandicapLevel", params.getWorkerHandicapLevel()); // 残疾等级
																					// 对应的id
			paramsMap.put("page", params.getPage()); // 分页--起始页
														// ******************************
			paramsMap.put("pageSize", params.getRows());// 分页--返回量
														// ******************************

			PaginationRecordsAndNumber<Worker, Number> query = workerService.getPaginationRecords(paramsMap);
			total = query.getNumber().intValue();// 数据总条数
			list = new ArrayList<>();
			for (Iterator<Worker> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				Worker it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("workerName", it.getWorkerName());// 姓名
				map.put("workerHandicapCode", it.getWorkerHandicapCode());// 残疾证号
				if (it.getWorkerGender() == null) {
					map.put("workerGender", "未知");// 性别

				} else {

					if (it.getWorkerGender().equals("0")) {
						map.put("workerGender", "女");// 性别
						map.put("retirementAge", auditParam.getRetireAgeFemale());// 女退休年龄
					} else {
						map.put("workerGender", "男");// 性别
						map.put("retirementAge", auditParam.getRetireAgeMale());// 男退休年龄
					}
				}

				// 计算年龄(去年的年龄) 传入残疾证号，参数错误返回-1
				map.put("workerAge", WorkerUtil.conversionAge(it.getWorkerHandicapCode())-1);
				map.put("phone", it.getPhone());// phone
				map.put("workerHandicapType", it.getWorkerHandicapType().getHandicapType());// 残疾类别
				map.put("workerHandicapLevel", it.getWorkerHandicapLevel().getHandicapLevel());// 残疾等级
				map.put("currentJob", it.getCurrentJob());// 现任职位
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
		} catch (Exception e) {

			logger.error("queryCompanyWorkerErrorin:{}", e.getMessage());
		}
		logger.debug("queryCompanyWorkerResult:{},list:{}", total, list);
		return entity;

	}

	/**
	 * 批量导出残疾员工信息
	 * 
	 * @param idArr
	 * @param request
	 * @return
	 */	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public String exportWorker(@RequestParam(value = "params[]") Integer idArr[], HttpServletRequest request) {
		logger.debug("idArr:{}", idArr+"");
		boolean b = true;
		List<Worker> list = null;
		if(idArr[0] == Integer.MAX_VALUE){
			list = new ArrayList<Worker>();
			for(Worker c:workerService.getPaginationRecords(null, Constants.START_PAGE, Integer.MAX_VALUE).getRecords()){
				list.add(c);
			}
		}else{
			list= workerService.getByIds(idArr);
		}
		String url = request.getServletContext().getRealPath("/");
		// 创建导出文件夹
		File uploadPath = new File(url + "upload");
		// 导出文件夹
		String exportFolder = uploadPath + File.separator + "worker";
		File workerPath = new File(exportFolder);
		if (!(uploadPath.exists())) {
			uploadPath.mkdir();
		}
		if (!(workerPath.exists())) {
			workerPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createWorkerExcel(exportPath, list);
		if (b) {
			String destPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/worker/" + uuid + ".xls";
		}
		logger.debug("ecportWorkerResults:{},paramsId:{}", b, idArr);
		return FileDownloadPath;
	}

}
