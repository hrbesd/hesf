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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;
import com.esd.hesf.model.WorkerTemp;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.WorkerHandicapLevelService;
import com.esd.hesf.service.WorkerHandicapTypeService;
import com.esd.hesf.service.WorkerService;
import com.esd.hesf.service.WorkerTempService;
import com.esd.hesf.viewmodels.WorkerViewModel;

@Controller
@RequestMapping(value = "/security/query/worker")
public class QueryWorkerController {
	private static final Logger logger = LoggerFactory
			.getLogger(QueryWorkerController.class);

	@Autowired
	private WorkerService workerService;// 残疾职工service

	@Autowired
	private WorkerTempService wtService; // 残疾职工缓存表

	@Autowired
	private WorkerHandicapTypeService whtService;

	@Autowired
	private WorkerHandicapLevelService whlService;

	@Autowired
	private AuditParameterService auditParameterService;// 年审参数

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView worker_list(HttpServletRequest request) {
		logger.debug("goToPage:{}", "转到残疾职工列表页面");
		return new ModelAndView("query/worker");

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workerList(WorkerParamModel params,
			HttpServletRequest request) {
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
			paramsMap.put("workerHandicapLevel",
					params.getWorkerHandicapLevel()); // 残疾等级
														// 对应的id
			paramsMap.put("page", params.getPage()); // 分页--起始页
														// ******************************
			paramsMap.put("pageSize", params.getRows());// 分页--返回量
														// ******************************
			PaginationRecordsAndNumber<WorkerViewModel, Number> query = workerService
					.getByMultiCondition(paramsMap);
			total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<WorkerViewModel> iterator = query.getRecords()
					.iterator(); iterator.hasNext();) {
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
				map.put("workerAge",
						WorkerUtil.conversionAge(it.getWorkerHandicapCode()));
				map.put("phone", it.getPhone());// phone
				map.put("workerHandicapType", it.getWorkerHandicapType()
						.getHandicapType());// 残疾类别
				map.put("workerHandicapLevel", it.getWorkerHandicapLevel()
						.getHandicapLevel());// 残疾等级

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
	 * 获取企业残疾职工列表
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/company_worker_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> companyHireList(WorkerParamModel params,
			HttpServletRequest request) {
		logger.debug("queryCompanyWorkerParams{}", params);

		Map<String, Object> entity = new HashMap<>();
		Integer total = 0;
		List<Map<String, Object>> list = null;
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
			paramsMap.put("workerHandicapLevel",
					params.getWorkerHandicapLevel()); // 残疾等级
														// 对应的id
			paramsMap.put("isCadre", params.getIsCadre()); // 是否是干部
			paramsMap.put("page", params.getPage()); // 分页--起始页
														// ******************************
			paramsMap.put("pageSize", params.getRows());// 分页--返回量
														// ******************************
			PaginationRecordsAndNumber<Worker, Number> query = workerService
					.getPaginationRecords(paramsMap);
			total = query.getNumber().intValue();// 数据总条数
			list = new ArrayList<>();
			for (Iterator<Worker> iterator = query.getRecords().iterator(); iterator
					.hasNext();) {
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
					} else {
						map.put("workerGender", "男");// 性别
					}
				}
				// 是否干部
				map.put("isCadre", it.getIsCadre());
				// 计算年龄(去年的年龄,周岁 ) 传入残疾证号
				map.put("workerAge",
						WorkerUtil.conversionAge(it.getWorkerHandicapCode()) - 1 - 1);
				map.put("phone", it.getPhone());// phone
				map.put("workerHandicapType", it.getWorkerHandicapType()
						.getHandicapType());// 残疾类别
				map.put("workerHandicapLevel", it.getWorkerHandicapLevel()
						.getHandicapLevel());// 残疾等级
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
	 * 获取保存在缓存表中的 即将替换掉预订人数的残疾员工列表
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/company_temp_worker_list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> companyWorkerInTemp(WorkerParamModel params,
			HttpServletRequest request) {
		logger.debug("companyWorkerInTemp{}", params);
		Map<String, Object> entity = new HashMap<>();
		Integer total = 0;
		List<Map<String, Object>> list = null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("getOverproof", params.getIsExceed()); // 是否获取超过退休年龄
			paramsMap.put("isCadre", params.getIsCadre()); // 是否是干部
			paramsMap.put("companyId", params.getCompanyId()); // 公司id
			paramsMap.put("workerName", params.getWorkerName()); // 姓名
			paramsMap.put("workerHandicapCode", params.getWorkerHandicapCode()); // 残疾证号
			paramsMap.put("workerGender", params.getWorkerGender()); // 性别
			paramsMap.put("maxAge", params.getWorkerAge_2()); // 最大年龄
			paramsMap.put("minAge", params.getWorkerAge_1()); // 最小年龄
			paramsMap.put("phone", params.getPhone()); // 电话
			paramsMap.put("workerHandicapType", params.getWorkerHandicapType()); // 残疾类别
			paramsMap.put("workerHandicapLevel",
					params.getWorkerHandicapLevel()); // 残疾等级
			paramsMap.put("page", params.getPage()); // 分页--起始页
			paramsMap.put("pageSize", params.getRows());// 分页--返回量
			PaginationRecordsAndNumber<WorkerTemp, Number> query = wtService
					.getByMultiConditions(paramsMap);
			total = query.getNumber().intValue();// 数据总条数
			list = new ArrayList<>();
			List<WorkerHandicapType> tlist = whtService.getAll();
			List<WorkerHandicapLevel> llist = whlService.getAll();
			for (Iterator<WorkerTemp> iterator = query.getRecords().iterator(); iterator
					.hasNext();) {
				WorkerTemp it = iterator.next();
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
				// 是否干部
				map.put("isCadre", it.getIsCadre());
				// 计算年龄(去年的年龄,周岁 ) 传入残疾证号
				map.put("workerAge",
						WorkerUtil.conversionAge(it.getWorkerHandicapCode()) - 1 - 1);
				map.put("phone", it.getPhone());// phone
				for (WorkerHandicapType tt : tlist) {
					if (it.getWorkerHandicapType() == tt.getId()) {
						map.put("workerHandicapType", tt.getHandicapType());// 残疾类别
					}
				}
				for (WorkerHandicapLevel tt : llist) {
					if (it.getWorkerHandicapLevel() == tt.getId()) {
						map.put("workerHandicapLevel", tt.getHandicapLevel());// 残疾类别
					}
				}
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
	 * 多条件查询, 残疾员工缓存表中的 残疾人按比例计算出的实际安排人数
	 * 
	 * @param companyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getfillNumber/{companyId}/{year}", method = RequestMethod.POST)
	@ResponseBody
	public Integer getfillNumber(
			@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {
		logger.debug("getfillNumber:companyId:{}", companyId);
		// 获得已录入残疾人数
		Integer yiLuRuCanJiRen = wtService.getCountByCompanyId(companyId);
		// 处理残疾人残疾类型和等级不同的比例
		List<WorkerCalculator> list = auditParameterService
				.getSpecialSetting(year);
		for (WorkerCalculator workerCalculator : list) {
			Integer per = workerCalculator.getPer().intValue();
			Integer type = workerCalculator.getType();
			Integer lvl = workerCalculator.getLvl();
			Integer num = auditParameterService.getSpecialCountFromWorkerTemp(companyId,
					year, type, lvl);
			logger.debug("type:{},lvl:{},per:{}", type, lvl, per);
			yiLuRuCanJiRen = ((yiLuRuCanJiRen - num) + (num * per));
		}
		System.out.println("**************"+yiLuRuCanJiRen);
		System.out.println("**************"+yiLuRuCanJiRen);
		System.out.println("**************"+yiLuRuCanJiRen);
		System.out.println("**************"+yiLuRuCanJiRen);
		System.out.println("**************"+yiLuRuCanJiRen);
		return yiLuRuCanJiRen;
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
	public String export(@RequestParam(value = "params[]") Integer idArr[],
			HttpServletRequest request) {
		logger.debug("idArr:{}", idArr + "");
		boolean b = true;
		List<Worker> list = null;
		if (idArr[0] == Integer.MAX_VALUE) {
			list = new ArrayList<Worker>();
			for (Worker c : workerService.getPaginationRecords(null,
					Constants.PAGE_START, Constants.PAGE_SIZE_MAX).getRecords()) {
				list.add(c);
			}
		} else {
			list = workerService.getByIds(idArr);
		}
		String url = request.getServletContext().getRealPath("/");
		// 创建导出文件夹
		File downloadPath = new File(url + "temp");
		if (!(downloadPath.exists())) {
			downloadPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = downloadPath + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createWorkerExcel(exportPath, list);
		if (b) {
			String destPath = request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/temp/" + uuid + ".xls";
		}
		logger.debug("ecportWorkerResults:{},paramsId:{}", b, idArr);
		return FileDownloadPath;
	}

}
