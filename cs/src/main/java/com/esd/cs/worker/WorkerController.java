/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.worker;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.cs.common.CommonUtil;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;
import com.esd.hesf.model.WorkerTemp;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.CompanyYearWorkerService;
import com.esd.hesf.service.WorkerService;
import com.esd.hesf.service.WorkerTempService;

/*
 * 残疾职工控制器
 * 
 * @author yuxingliang
 */
@Controller
@RequestMapping(value = "/security/worker")
public class WorkerController {

	private static final Logger logger = LoggerFactory
			.getLogger(WorkerController.class);

	// 残疾证号最大长度
	public static final int MAX_HANDICAP_CODE_LENGTH = 22;
	// 残疾证号最小长度
	public static final int MIN_HANDICAP_CODE_LENGTH = 20;

	@Autowired
	private WorkerService workerService;// 员工service接口

	@Autowired
	private CompanyService companyService;// 企业service接口

	@Autowired
	private WorkerTempService wtService; // 员工缓存service接口

	@Autowired
	private AuditParameterService auditParameterService;// 年审参数service接口

	@Autowired
	private CompanyYearWorkerService cywService; // 企业员工关系表service接口

	@Value("${LoadUpFileMaxSize}")
	String LoadUpFileMaxSize;

	/**
	 * 转到残疾职工列表页面 初审时利用tab标签页的post方式获取。 所以get和post都可以请求，
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/{companyId}/{year}")
	public ModelAndView worker_list(
			@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {
		logger.debug("goToWorkerList year:{},companyId:{}", year, companyId);

		request.setAttribute("year", year);
		request.setAttribute("companyId", companyId);
		// 获取年审参数
		AuditParameter param = auditParameterService.getByYear(year);
		if (param != null) {
			// 男职工退休年龄
			request.setAttribute("maleRetirementAge", param.getRetireAgeMale());
			// 女职工退休年龄
			request.setAttribute("femaleRetirementAge",
					param.getRetireAgeFemale());
		} else {
			logger.error("getAuditParameterError");
		}
		logger.debug("goToPage:{}", "转到残疾职工列表页面");
		return new ModelAndView("basicInfo/worker_list");
	}

	/**
	 * 转到残疾职工列表页面 提供给企业用户自己创建和导入残疾职工信息时使用
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list/auditCreate")
	public ModelAndView audit_create_worker_list(HttpServletRequest request,
			HttpSession session) {
		String year = session.getAttribute(Constants.YEAR).toString();
		logger.debug("goToWorkerList year:{}", year);
		request.setAttribute("year", year);
		// 获取年审参数
		AuditParameter param = auditParameterService.getByYear(year);
		if (param != null) {
			// 男职工退休年龄
			request.setAttribute("maleRetirementAge", param.getRetireAgeMale());
			// 女职工退休年龄
			request.setAttribute("femaleRetirementAge",
					param.getRetireAgeFemale());
		} else {
			logger.error("getAuditParameterError");
		}
		logger.debug("goToPage:{}", "转到残疾职工列表页面");
		return new ModelAndView("audit/audit_create_worker_list");
	}

	/**
	 * 转向残疾职工列表 没有功能按钮
	 * 
	 * @param companyId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view/{companyId}/{year}")
	public ModelAndView view(
			@PathVariable(value = "companyId") String companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		// 获取年审参数
		AuditParameter param = auditParameterService.getByYear(year);
		if (param != null) {
			// 男职工退休年龄
			request.setAttribute("maleRetirementAge", param.getRetireAgeMale());
			// 女职工退休年龄
			request.setAttribute("femaleRetirementAge",
					param.getRetireAgeFemale());
		} else {
			logger.error("getAuditParameterError");
		}
		logger.debug("goToPage:{}", "转到残疾职工列表页面Ex");
		return new ModelAndView("basicInfo/worker_simplelist");
	}

	/**
	 * 转到增加残疾职工页面
	 */
	@RequestMapping(value = "/add/{companyId}/{year}", method = RequestMethod.GET)
	public ModelAndView add_worker(
			@PathVariable(value = "companyId") String companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request) {
		logger.debug("gotoAddWorker,companyId:{},year:{}", companyId, year);

		// 续传企业id
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		// 获取年审参数
		AuditParameter auditParam = auditParameterService.getByYear(year);
		request.setAttribute("retireAgeFemale", auditParam.getRetireAgeFemale());// 女退休年龄
		request.setAttribute("retireAgeMale", auditParam.getRetireAgeMale());// 男退休年龄

		logger.info("goToPage:{}", "添加残疾职工页面");
		return new ModelAndView("basicInfo/add_worker");
	}

	/**
	 * 异步增加 不 带照片的残疾职工
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean add_worker(Worker worker, HttpServletRequest request) {
		// 年审年度
		String year = request.getParameter("year");
		// 公司id
		Integer companyId = Integer.valueOf(request.getParameter("companyId"));
		logger.debug("addWorker--:{},year:{},companyID:{}", worker, year,
				companyId);
		// 组装员工对象
		worker = WorkerUtil.assembly(worker);
		// 查看数据库中是否有该身份证号的员工, 没有的话-直接保存; 有的话-更新该条数据,并同时保存和该公司的关系
		Worker tempWorker = workerService.getByWorkerIdCard(worker
				.getWorkerIdCard());
		// ①数据库中没有该员工的话,直接保存
		if (tempWorker == null) {
			return workerService.save(worker, companyId, year);
		} else {
			// ②数据库中已经存在该员工(但不在任何一家公司)的话, 则更新他的信息, 并同时保存和该公司今年的关系
			tempWorker.setWorkerName(worker.getWorkerName());
			tempWorker.setCareerCard(worker.getCareerCard());
			tempWorker.setPhone(worker.getPhone());
			tempWorker.setCurrentJob(worker.getCurrentJob());
			tempWorker.setRemark(worker.getRemark());
			// 关系表对象
			CompanyYearWorker cyw = new CompanyYearWorker();
			cyw.setCompanyId(companyId);
			cyw.setYear(year);
			cyw.setWorkerId(tempWorker.getId());
			// 更新并保存关系
			return workerService.update(tempWorker) && cywService.save(cyw);
		}
	}

	/**
	 * 异步增加带照片的残疾职工
	 * 
	 * @param worker
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/addWithPic", method = RequestMethod.POST)
	public void aadd_worker(
			@RequestParam("picfile") CommonsMultipartFile picfile,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String workerHandicapCode = request.getParameter("workerHandicapCode");
		// 根据残疾证号 组装残疾职工对象
		Worker worker = WorkerUtil.assembly(workerHandicapCode);
		// 添加上其他信息
		worker.setWorkerName(request.getParameter("workerName"));
		worker.setCareerCard(request.getParameter("careerCard"));
		worker.setPhone(request.getParameter("phone"));
		worker.setCurrentJob(request.getParameter("currentJob"));
		worker.setRemark(request.getParameter("remark"));
		// 年审年度
		String year = request.getParameter("year");
		// 公司id
		Integer companyId = Integer.valueOf(request.getParameter("companyId"));
		// 从CommonsMultipartFile 得到图片和图片名信息
		worker.setPic(picfile.getBytes());
		worker.setPicTitle(picfile.getOriginalFilename());

		// 查看数据库中是否有该身份证号的员工, 没有的话-直接保存; 有的话-更新该条数据,并同时保存和该公司的关系
		Worker tempWorker = workerService.getByWorkerIdCard(worker
				.getWorkerIdCard());
		Boolean bl = false;
		// ①数据库中没有该员工的话,直接保存
		if (tempWorker == null) {
			bl = workerService.save(worker, companyId, year);
		} else {
			// ②数据库中已经存在该员工(但不在任何一家公司)的话, 则更新他的信息, 并同时保存和该公司今年的关系
			tempWorker.setWorkerName(worker.getWorkerName());
			tempWorker.setCareerCard(worker.getCareerCard());
			tempWorker.setPhone(worker.getPhone());
			tempWorker.setCurrentJob(worker.getCurrentJob());
			tempWorker.setRemark(worker.getRemark());
			tempWorker.setPic(picfile.getBytes());
			tempWorker.setPicTitle(picfile.getOriginalFilename());
			// 关系表对象
			CompanyYearWorker cyw = new CompanyYearWorker();
			cyw.setCompanyId(companyId);
			cyw.setYear(year);
			cyw.setWorkerId(tempWorker.getId());
			// 更新并保存关系
			bl = workerService.update(tempWorker) && cywService.save(cyw);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		if (bl) {
			writer.write("success");
		} else {
			writer.write("failure");
		}
	}

	/**
	 * 上传图片超出最大值时, 弹出的异常
	 * 
	 * @param ex
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	public void handlerException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf8");
		String notice = "error";
		if (ex instanceof MaxUploadSizeExceededException) {
			notice = "文件大小不超过"
					+ getFileMB(((MaxUploadSizeExceededException) ex)
							.getMaxUploadSize());
		} else {
			notice = "上传文件出现错误,错误信息:" + ex.getMessage();
		}
		PrintWriter writer = response.getWriter();
		writer.write(notice);
	}

	/**
	 * 字节转为MB 方法
	 * 
	 * @param byteFile
	 * @return
	 */
	private String getFileMB(long byteFile) {
		if (byteFile == 0) {
			return "0MB";
		}
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}

	/**
	 * 转到查看残疾职工页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewWorker(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
		// 续传企业id
		logger.debug("editWorkerParamsID:{}", id);
		Worker w = workerService.getByPrimaryKey(id);
		request.setAttribute("worker", w);
		logger.debug("goToedit_worker{}", w);
		return new ModelAndView("basicInfo/view_worker");
	}

	/**
	 * 转到编辑残疾职工页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editWorker(@PathVariable(value = "id") Integer id,
			HttpServletRequest request) {
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
	 * 删除残疾职工
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Boolean delete_worker(
			@RequestParam(value = "params[]") Integer params[],
			@RequestParam(value = "companyId") Integer companyId,
			@RequestParam(value = "year") String year,
			HttpServletRequest request) {

		logger.debug("deleteWorkerParamsID:{},years:{},companyId:{}", params,
				year, companyId);
		try {
			for (int i = 0; i < params.length; i++) {
				boolean b = companyService.deleteWorkerFromCompany(year,
						companyId, params[i]);
				logger.debug("delete_worker:{},result:{}", params[i], b);
				if (b = false) {

					logger.error("deleteWorkerError:{},result:{}", params[i],
							"error");
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("delete_worker{}", e.getMessage());
		}
		return true;
	}

	/**
	 * 跳转到 导入残疾职工页面
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importworker/{companyId}/{year}", method = RequestMethod.GET)
	public ModelAndView importworker(
			@PathVariable(value = "companyId") Integer companyId,
			@PathVariable(value = "year") String year,
			HttpServletRequest request, HttpSession session) {
		request.setAttribute("companyId", companyId);
		request.setAttribute("year", year);
		logger.debug("goToWorker_import,param:{}", companyId);
		// 每次进入前都要删除以前可能遗留的workerTemp员工缓存表中的数据
		Integer userId = Integer.parseInt(session.getAttribute(
				Constants.USER_ID).toString());
		wtService.deleteByUserId(userId);
		return new ModelAndView("basicInfo/worker_import");
	}

	/**
	 * 导入残疾职工文件
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/importworker", method = RequestMethod.POST)
	public void importworker(@RequestParam("excel") CommonsMultipartFile excel,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
		excel.getBytes();
		Integer userId = Integer.parseInt(session.getAttribute(
				Constants.USER_ID).toString());

		// ------------------------------------------------------------------//
		// 一. 每次进入前都要删除以前可能遗留的workerTemp员工缓存表中的数据 //
		// ------------------------------------------------------------------//
		wtService.deleteByUserId(userId);
		logger.debug("importWorker:{}");

		// ------------------------------------------------------------------//
		// 二. 将上传的excel, 放到指定的文件夹中 //
		// ------------------------------------------------------------------//
		// ① 初始化上传文件目录
		String url = request.getServletContext().getRealPath("/");
		String upload = "upload";
		String workerFolder = "worker";
		// 上传文件总目录
		String upLoadPath = url + upload + File.separator;
		// 上传的excel文件存放的目录
		String excelPath = upLoadPath + workerFolder + File.separator;
		// 缓存文件目录(比如提供下载的临时生成的excel文件)
		String tempPath = upLoadPath + "temp" + File.separator;

		// 如果不存在以上 3个目录的话, 则进行创建
		File uploadFolder = new File(upLoadPath); // upload总目录
		File excelFolder = new File(excelPath); // upload下的 excel存放目录
		File tempFolder = new File(tempPath); // upload下的 生成的excel缓存目录
		if (!uploadFolder.exists()) {
			uploadFolder.mkdir();
		}
		if (!excelFolder.exists()) {
			excelFolder.mkdir();
		}
		if (!tempFolder.exists()) {
			tempFolder.mkdir();
		}
		// ② 数据导入的文件
		File destFile = new File(excelPath + File.separator
				+ excel.getOriginalFilename());
		if (!destFile.exists()) {
			destFile.mkdir();
		}
		// ③ response 输出相应内容
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();

		// 将上传的excel放到 存放excel的文件夹的 destFile文件中
		try {
			excel.transferTo(destFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			writer.write(Constants.NOTICE_FAILURE + ":"
					+ "上传并复制excel文件到服务器时发生错误,错误信息:" + e.getMessage());
			return;
		} catch (IOException e) {
			e.printStackTrace();
			writer.write(Constants.NOTICE_FAILURE + ":"
					+ "上传并复制excel文件到服务器时发生错误,错误信息:" + e.getMessage());
			return;
		}

		// ------------------------------------------------------------------//
		// 三. 文件上传成功，进入解析阶段 //
		// ------------------------------------------------------------------//

		// ① 提取request中用于校验的公司id和审核年度
		Integer companyId = Integer.parseInt(request.getParameter("companyId")); // 所在公司id
		String year = request.getParameter("year"); // 审核年度
		AuditParameter auditParameter = auditParameterService.getByYear(year);

		// ② 读取上传的excel中的worker信息到list列表中
		List<Worker> list = null; // 定义解析出来的员工list列表
		list = WorkerUtil.parse(destFile, 0);
		if (list == null || list.size() <= 0) {
			// excel文件内部文本信息格式错误
			writer.write(Constants.NOTICE_FAILURE + ":"
					+ "excel文件内部文本信息格式错误,导致解析失败, 请检查excel文件是否符合规范.");
			return;
		}

		// ③ 逐条校验, 不管正误, worker都存储到员工缓存表workerTemp中
		for (int i = 0; i < list.size(); i++) {
			WorkerTemp result = validateWorkerFromExcel(list.get(i), companyId,
					userId, auditParameter);
			if (result == null) {
				continue;
			}
			wtService.save(result);
		}

		// ④ 校验完毕后, 删除上传的excel文件
		if (destFile.exists()) {
			destFile.delete();
		}

		// ⑤ 校验完毕后, 从缓存表中读取到导入的数据总数, 正确条数, 错误条数
		Integer totalCount = wtService.getCountByCheck(null, userId);
		Integer rightCount = wtService.getCountByCheck(true, userId);
		Integer wrongCount = wtService.getCountByCheck(false, userId);

		// String notice = "excel文件数据校验成功, 共有残疾证号数据 " + totalCount + " 条,"
		// + "其中, 通过验证并可以导入的数据有 " + rightCount + " 条, "
		// + "错误数据有 " + wrongCount + " 条."
		// +"successEnd"

		String notice = totalCount + ","  + rightCount + "," + wrongCount +"successEnd";
		writer.write(Constants.NOTICE_SUCCESS + ":" + notice);

		// ⑥ 如果有错误残疾职工信息的话, 则将其保存到缓存目录的excel文件中
		if (wrongCount != null && wrongCount > 0) {
			String uuid = UUID.randomUUID().toString().replace("-", ""); // 随机id,
																			// 防止重复
			String wrongPath = tempPath + uuid + ".xls"; // 错误错误列表 文件路径
			List<WorkerTemp> wrongList = wtService.getByCheck(false, userId);
			if (PoiCreateExcel.createExcel(wrongPath, wrongList)) {
				// 项目在服务器上的远程绝对地址
				String serverAndProjectPath = request.getLocalAddr() + ":"
						+ request.getLocalPort() + request.getContextPath();
				// 文件所谓的远程绝对路径
				wrongPath = "http://" + serverAndProjectPath + "/upload/temp/"
						+ uuid + ".xls";
				writer.write("wrongPath:" + wrongPath);
			}
		}

	}

	/**
	 * 校验从excel中读取出来的worker信息
	 * 
	 * @param worker
	 * @return
	 */
	public WorkerTemp validateWorkerFromExcel(Worker worker, Integer companyId,
			Integer userId, AuditParameter auditParameter) {
		// ------------------------------------------------------------------//
		// 一 创建准备保存到 “员工缓存表”workerTemp 中的结果对象
		// 提示信息都保存到workerTemp对象的remark属性字段中
		// ------------------------------------------------------------------//
		WorkerTemp result = new WorkerTemp();
		result.setUserId(userId); // 操作人id
		result.setIsOk(false); // 默认为false--即“不合格”
		String year = auditParameter.getYear();// 得到审核年度
		// ------------------------------------------------------------------//
		// 二 以下对列表中的worker数据, 逐步进行校验 //
		// ------------------------------------------------------------------//

		// 取得残疾证号
		String workerHandicapCode = worker.getWorkerHandicapCode();
		// 取得员工姓名
		String workerName = worker.getWorkerName().replace(" ", "");// 去除所有空格
		// 残疾证号, 姓名保存到缓存对象中去
		result.setWorkerHandicapCode(workerHandicapCode);
		result.setWorkerName(workerName);
		// ************************* 校验开始 ***************************//
		// 1.姓名是否为空
		if (StringUtils.isEmpty(workerName)) {
			result.setRemark("员工姓名为空.");
			return result;
		}
		// 2.校验姓名长度
		if (workerName.length() > 20 || workerName.length() < 2) {
			result.setRemark("姓名长度过长或者过短.");
			return result;
		}
		// 3.校验残疾证号是否为空
		if (StringUtils.isBlank(workerHandicapCode)) {
			result.setRemark("残疾证号为空.");
			return result;
		}
		// ******** 以下校验残疾证号 ********//
		workerHandicapCode.replace(" ", "");// 去掉所有空格
		// 4.校验残疾证号长度
		if (workerHandicapCode.length() < MIN_HANDICAP_CODE_LENGTH
				|| workerHandicapCode.length() > MAX_HANDICAP_CODE_LENGTH) {
			result.setRemark("残疾证号长度不符, 应为20-22位之间.");
			return result;
		}
		// 5.校验残疾证号是否含有中文
		if (CommonUtil.chineseValid(workerHandicapCode)) {
			result.setRemark("残疾证号错误, 不能出现中文.");
			return result;
		}
		// 6.校验14之前是否有其他字符, 即身份证的前14位必须为数字
		String handicapStr = workerHandicapCode.substring(0, 14);
		if (!handicapStr.matches("\\d+")) {
			result.setRemark("残疾证号前14位出现非数字的字符.");
			return result;
		}
		// 7.校验残疾类型
		String handicapTypeStr = workerHandicapCode.substring(18, 19);
		boolean ishandicapType = handicapTypeStr.matches("\\d+");// 返回true为纯数字,否则就不是纯数字
		// 8.校验是否数数字
		if (!ishandicapType) {
			result.setRemark("残疾类型错误, 必须为数字.");
			return result;
		}
		int handicapType = Integer.valueOf(handicapTypeStr);
		if (handicapType > 7 || handicapType == 0) {
			result.setRemark("残疾类型错误, 需在1~7之间.");
			return result;
		}
		// 9.校验残疾证号等级
		String handicapLevelStr = workerHandicapCode.substring(19, 20);
		boolean ishandicapLevel = handicapLevelStr.matches("\\d+");// 返回true为纯数字,否则就不是纯数字
		if (!ishandicapLevel) {
			result.setRemark("残疾等级错误, 必须为数字.");
			return result;
		}
		int handicapLevel = Integer.valueOf(handicapLevelStr);
		if (handicapLevel > 4 || handicapLevel == 0) {
			result.setRemark("残疾等级错误, 需在1~4之间.");
			return result;
		}
		// 10.校验职工年龄
		List<String> ageResult = new WorkerUtil().ageVerify(workerHandicapCode,
				auditParameter);
		if (ageResult != null) {
			String bl = ageResult.get(0).toString();
			// 年龄校验没有通过
			if ("no".equals(bl)) {
				result.setRemark(ageResult.get(1).toString());
				return result;
			}
		} else {
			result.setRemark("校验残疾职工年龄出现错误.");
			return result;
		}
		String workerIdCard = workerHandicapCode.substring(0, 18); // 身份证号
		// 11.校验缓存表中是否存在该身份证号, 即校验excel表中数据是否有重复的
		Integer existNum = wtService.getCountByworkerIdCard(workerIdCard);
		if (existNum != null && existNum > 0) {
			result.setRemark("身份证号重复");
			return result;
		}

		// 12.校验身份证号重复性
		Map<String, Object> validateResult = validateWorkerIdentityCode(
				workerIdCard, year);
		String notice = validateResult.get(Constants.NOTICE).toString(); // 验证结果字符串
		// 11--- 第一种情况 存在，并且在其他公司内。
		if (StringUtils.equals(notice, "inCompany")) {
			// 存储错误信息
			String errinfo = "职工已被：" + validateResult.get("companyName")
					+ " 单位录用，单位档案编码为：" + validateResult.get("companyCode");
			result.setRemark(errinfo);
			return result;
		}
		// 12--- 第二种情况：存在，并且不再任何公司。 第三种情况： 不存在数据库中，进行存储
		if (StringUtils.equals(notice, "exists")
				|| StringUtils.equals(notice, "notExists")) {
			// 根据残疾证号, 组装 残疾职工基本信息
			Worker workerCorrect = WorkerUtil.assembly(workerHandicapCode);
			result.setWorkerName(workerName);
			result.setWorkerHandicapCode(workerCorrect.getWorkerHandicapCode());
			result.setIsOk(true); // 设为OK， 即 可以导入的意思
			result.setWorkerBirth(workerCorrect.getWorkerBirth());
			result.setWorkerBirthYear(workerCorrect.getWorkerBirthYear());
			result.setWorkerGender(workerCorrect.getWorkerGender());
			result.setWorkerHandicapLevel(workerCorrect
					.getWorkerHandicapLevel().getId());
			result.setWorkerHandicapType(workerCorrect.getWorkerHandicapType()
					.getId());
			result.setWorkerIdCard(workerCorrect.getWorkerIdCard());
			// 如果该残疾人存在, 但不在任何公司中, 则将其原来的id保存起来
			if (StringUtils.equals(notice, "exists")) {
				result.setPreId(Integer.parseInt(validateResult.get("workerId")
						.toString()));
			}
			return result;
		}
		return null;
	}

	/**
	 * 校验残疾证号
	 * 
	 * @param workerIdCard
	 * @return
	 */
	public Map<String, Object> validateWorkerIdentityCode(String workerIdCard,
			String year) {
		logger.debug("validateOrganizationCode:{},year:{}", workerIdCard, year);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Company company = workerService.retrieveCompanyByWorker(year,
					workerIdCard);
			// 第一种情况 存在，并且在其他公司内。
			if (company != null) {
				result.put(Constants.NOTICE, "inCompany");
				Map<String, String> companyMap = new HashMap<String, String>();
				companyMap.put("companyName", company.getCompanyName());
				companyMap.put("companyCode", company.getCompanyCode());
				companyMap.put("companyTaxCode", company.getCompanyTaxCode());
				logger.debug("validate_workerHandicapCodeResult:{},company:{}",
						"type:1。职工存在，并且在其他公司内", company.getCompanyName() + "  "
								+ company.getCompanyCode());
				result.put("companyId", company.getId());
				result.put("companyCode", company.getCompanyCode());
				result.put("companyName", company.getCompanyName());
				// 同时也将该员工的信息调出来
				Worker w = workerService.getByWorkerIdCard(workerIdCard);
				result.put("workerName", w.getWorkerName()); // 姓名
				result.put("careerCard", w.getCareerCard()); // 就业证号
				result.put("phone", w.getPhone()); // 联系电话
				result.put("remark", w.getRemark()); // 备注
				return result;
			} else {
				// 根据身份证号判断是否已经存在
				Worker w = workerService.getByWorkerIdCard(workerIdCard);
				logger.error("workerIdCard:{},obg:{}" + workerIdCard, w);
				// 第二种情况：存在，但不在任何公司。
				if (w != null) {
					logger.debug("validateWorkerHandicapCodeResult:{}",
							"type:2。职工" + w.getWorkerName() + "存在数据库中，并且不再任何公司");
					result.put(Constants.NOTICE, "exists");
					result.put("workerId", w.getId().toString());
					result.put("workerName", w.getWorkerName()); // 姓名
					result.put("careerCard", w.getCareerCard()); // 就业证号
					result.put("phone", w.getPhone()); // 联系电话
					result.put("remark", w.getRemark()); // 备注
					return result;
				} else {
					// 第三种情况，不存在.
					logger.debug("validateWorkerHandicapCodeResult:{}",
							"type:3。职工不存在数据库中");
					result.put(Constants.NOTICE, "notExists");
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("校验错误：{}", e.getMessage());
			result.put(Constants.NOTICE, "检验发生异常...");
			return result;
		}

	}

	/**
	 * 验证 残疾证号是否存在，是否在其他公司内
	 * 
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validateWorkerIdentityCode")
	@ResponseBody
	public Map<String, Object> validateWorkerIdentityCodeForConstroller(
			@RequestParam(value = "workerIdCard") String workerIdCard,
			@RequestParam(value = "year") String year,
			HttpServletRequest request) {

		logger.debug(
				"validateWorkerIdentityCode --workerIdCard:{},year:{},companyId:{}",
				workerIdCard, year);
		// 参数 年份 残疾证号
		// 1.存在, 并且在其他公司内, 返回所在公司对象基本信息
		// 2.存在, 不在其他公司内, 返回从数据库中读取到已有的信息, 以便更新用
		// 3.不存在
		return validateWorkerIdentityCode(workerIdCard, year);
	}

	/**
	 * 向员工表中导入 前面校验过的保存在缓存表中的信息
	 * 
	 * @param companyId
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importFromWorkerTemp", method = RequestMethod.POST)
	@ResponseBody
	public Boolean importFromWorkerTemp(
			@RequestParam(value = "companyId") Integer companyId,
			@RequestParam(value = "year") String year,
			HttpServletRequest request, HttpSession session) {
		// 当前登陆用户ID
		Integer userId = Integer.parseInt(session.getAttribute(
				Constants.USER_ID).toString());
		// 先得到总共需要导入的残疾职工数量
		int totalWorkers = wtService.getCountByCheck(true, userId);
		// 如果总数大于1K， 则分批导入, 防止内存溢出
		int circulationTimes = 1;
		if (totalWorkers > 1000) {
			circulationTimes =(totalWorkers / 1000) + 1;
		}
		// 外层循环次数, 即将总数据分为几批导入
		PaginationRecordsAndNumber<WorkerTemp, Number> workerTempList = null;
		WorkerTemp t = new WorkerTemp();
		t.setIsOk(true);
		t.setUserId(userId);
		boolean bl = true;
		for (int k = 0; k < circulationTimes; k++) {
			int page = k + 1; // 起始索引
			workerTempList = null;
			workerTempList = wtService.getPaginationRecords(t, page, 1000);
			if (workerTempList == null) {
				break;
			}
			if (workerTempList.getRecords() == null) {
				break;
			}
			if (workerTempList.getRecords().size() < 1) {
				break;
			}
			// 将每批的1000条数据叠加起来
			Iterator<WorkerTemp> iterator = workerTempList.getRecords()
					.iterator();
			while (iterator.hasNext()) {
				WorkerTemp wt = iterator.next();
				// ①如果先前的员工id存在, 则对其进行更新, 然后插入到企业员工关系表中
				if (wt.getPreId() != null && wt.getPreId() > 0) {
					Worker w = workerService.getByPrimaryKey(wt.getPreId());
					w.setWorkerName(wt.getWorkerName());
					if (!workerService.update(w)) {
						bl = false;
					}
					CompanyYearWorker cyw = new CompanyYearWorker();
					cyw.setCompanyId(companyId);
					cyw.setYear(year);
					cyw.setCurrentJob(Constants.NOTYET);
					cyw.setWorkerId(w.getId());
					cyw.setUserId(userId);
					if (!cywService.save(cyw)) {
						bl = false;
					}
					continue;
				}
				// ②如果员工不存在, 则将其保存到worker表中, 然后插入到企业员工关系表中
				Worker worker = new Worker();
				worker.setWorkerName(wt.getWorkerName());
				worker.setWorkerGender(wt.getWorkerGender());
				worker.setWorkerBirth(wt.getWorkerBirth());
				worker.setWorkerBirthYear(wt.getWorkerBirthYear());
				worker.setWorkerIdCard(wt.getWorkerIdCard());
				worker.setWorkerHandicapCode(wt.getWorkerHandicapCode());
				worker.setWorkerHandicapLevel(new WorkerHandicapLevel(wt
						.getWorkerHandicapLevel()));
				worker.setWorkerHandicapType(new WorkerHandicapType(wt
						.getWorkerHandicapType()));
				worker.setUserId(userId);
				if (!workerService.save(worker, companyId, year)) {
					bl = false;
				}
			}
		}
		return bl;
	}

}
