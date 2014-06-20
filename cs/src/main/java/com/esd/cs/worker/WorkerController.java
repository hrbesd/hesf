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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
	@Autowired
	private WorkerService workerService;// 工作者

	@Autowired
	private CompanyService companyService;// 企业

	@Autowired
	private WorkerTempService wtService; // 员工缓存service接口

	@Autowired
	private AuditParameterService auditParameterService;// 年审参数

	@Autowired
	private CompanyYearWorkerService cywService; // 企业员工关系表service接口

	@Value("${LoadUpFileMaxSize}")
	String LoadUpFileMaxSize;

	// 残疾证号最大长度
	static int MAX_HANDICAPCODE = 22;
	// 残疾证号最小长度
	static int MIN_HANDICAPCODE = 20;

	// 提示文本
	static String LENGTHERROR = "残疾证号长度不符";

	// 提示文本
	static String BEENHIRED = "职工已被录用";
	// 提示文本
	static String ILLEGALSTR = "残疾证号内含有中文字符";

	// 提示文本
	static String LEVELERROR = "残疾证号，残疾等级数值非法";
	// 提示文本
	static String TYPEERROR = "残疾证号，残疾类型数值非法";
	// 提示文本
	static String AGEERROR = "年龄超标";
	static String NAMENULL = "姓名为空";
	static String WORDERROR = "excel文件内容格式错误!";
	static String CREATEERRORFILE = "创建错误信息列表文件错误";

	// 总条数
	static Integer workerCount; // 表格中员工总条数
	static Integer currentCount; // 当前处理进行到的条数

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
		response.setContentType("text/html;chrset=utf-8");
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
			notice = "文件不应大于"
					+ getFileMB(((MaxUploadSizeExceededException) ex)
							.getMaxUploadSize());
		} else {
			notice = "不知错误" + ex.getMessage();
		}
		PrintWriter writer = response.getWriter();
		writer.write(notice);
	}

//	private String getFileKB(long byteFile) {
//		if (byteFile == 0) {
//			return "0KB";
//		}
//		long kb = 1024;
//		return "" + byteFile / kb + "KB";
//	}

	private String getFileMB(long byteFile) {
		if (byteFile == 0) {
			return "0MB";
		}
		long mb = 1024 * 1024;
		return "" + byteFile / mb + "MB";
	}

	private boolean addWorker(Worker worker, Integer companyId, String year) {
		logger.debug("addWorkerParams:{},companyId:{},year:{}", worker,
				companyId, year);
		boolean b = workerService.save(worker, companyId, year);
		logger.debug("addWorkerResult:{}", b);
		return b;
	}

	/**
	 * 转到产看残疾职工页面
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
	 * 转到导入残疾职工页面
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
	public ModelAndView edit_worker_up(Worker worker, HttpServletRequest request) {
		logger.debug("editUpdata:{}", worker);
		Integer companyId = Integer.valueOf(request.getParameter("companyId"));
		boolean bl = editWorkerUp(worker, companyId,
				worker.getWorkerBirthYear());
		if (bl) {
			request.setAttribute(Constants.NOTICE, Constants.NOTICE_SUCCESS);
		} else {
			request.setAttribute(Constants.NOTICE, Constants.NOTICE_FAILURE);
		}
		return new ModelAndView("documents/add_worker_notice");
	}

	/**
	 * 更新残疾职工信息和职工与企业之前关联
	 * 
	 * @param worker
	 * @param companyId
	 * @param year
	 * @return
	 */
	private boolean editWorkerUp(Worker worker, Integer companyId, String year) {
		boolean workerUpDataStatus = false, companyUpdataStatus = false;
		logger.debug("upWorker:{},companyId:{}", worker, companyId);
		try {
			// 根据残疾证号获取版本号
			Worker w = workerService.getByWorkerHandicapCode(worker
					.getWorkerHandicapCode());
			if (w == null) {
				logger.error("UpWorkerError:{},info:{}",
						"notWorkerHandicapCode", worker);
				return false;
			}
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
					companyUpdataStatus = workerService.changeCompany(
							worker.getId(), c.getId(), year,
							worker.getCurrentJob());
					if (companyUpdataStatus) {
						logger.debug("workerUpDataGetCompanyResult:{}",
								companyUpdataStatus);
					} else {
						logger.error("workerUpDataGetCompanyResult:{}",
								companyUpdataStatus);
					}
				} else {
					logger.error("upWorkerError:{}", "noGetCompany");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	 * 导入残疾职工文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, String> importfile(String upLoadPath,
			HttpServletRequest request, HttpServletResponse response) {
		// 获取并解析文件类型和支持最大值
		// String fileType = "xls";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 最大缓存
		factory.setSizeThreshold(5 * 1024);
		// 设置临时文件目录
		factory.setRepository(new File(upLoadPath));
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		Map<String, String> result = new HashMap<String, String>();

		if (LoadUpFileMaxSize != null
				&& !StringUtils.equals(LoadUpFileMaxSize.trim(), "")) {
			// 文件最大上限
			fileUpload
					.setSizeMax(Integer.valueOf(LoadUpFileMaxSize) * 1024 * 1024);
		}
		try {
			// 获取所有文件列表
			List<FileItem> items = fileUpload.parseRequest(request);
			for (FileItem item : items) {
				// 如果是文件项，则保存文件到上传目录
				if (!item.isFormField()) {
					// 文件名
					String fileName = item.getName();
					// 检查文件后缀格式
					String fileEnd = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					// 创建文件唯一名称
					String uuid = UUID.randomUUID().toString();
					// 真实上传路径
					StringBuffer sbRealPath = new StringBuffer();
					sbRealPath.append(upLoadPath).append(uuid).append(".")
							.append(fileEnd);
					// 写入文件
					File file = new File(sbRealPath.toString());
					item.write(file);

					logger.info("上传文件成功,filePath：" + file.getPath());
					// 返回文件路径
					result.put("filePath", file.getPath());

					// form中参数信息
					result.put("companyId", request.getParameter("companyId")
							.toString());
					result.put("year", request.getParameter("year"));
				} else {
					// item.getFieldName():获取参数key。item.getString()：获取参数value
					result.put(item.getFieldName(), item.getString());
				}
			}
		} catch (Exception e) {

			// 提示错误信息
			result.put("fileError", "上传失败,文件大小不能超过" + LoadUpFileMaxSize + "M!");
			logger.error("uplaodWorkerFileError:{}", e.getMessage());
			return result;
		}
		return result;
	}

	// /**
	// * 获取已经处理的残疾职工总数
	// */
	// @RequestMapping(value = "/getDealedProgress", method = RequestMethod.GET)
	// @ResponseBody
	// public Integer getDealProgress(HttpSession session) {
	// Integer userId
	// =Integer.parseInt(session.getAttribute(Constants.USER_ID).toString());
	// int currentCount = wtService.getCountByCheck(null, userId,null);
	// System.out.println(currentCount);
	// System.out.println(workerCount);
	// //被除数不能为零
	// if (workerCount == null) {
	// workerCount = Integer.MAX_VALUE;
	// }
	// int rate = currentCount * 100 / workerCount;
	// return rate;
	// }

	/**
	 * 获取已经处理的残疾职工比例
	 */
	@RequestMapping(value = "/getDealedProgress", method = RequestMethod.GET)
	@ResponseBody
	public Integer getDealProgress(HttpSession session) {
		if (currentCount == null) {
			currentCount = 0;
		}
		System.out.println(currentCount + "-----------" + workerCount);
		// 被除数不能为零
		if (workerCount == null) {
			workerCount = Integer.MAX_VALUE;
		}
		int rate = currentCount * 100 / workerCount;
		return rate;
	}

	/**
	 * 导入残疾职工文件
	 */
	@RequestMapping(value = "/importworker", method = RequestMethod.POST)
	public ModelAndView importworker(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Integer userId = Integer.parseInt(session.getAttribute(
				Constants.USER_ID).toString());

		// 每次进入前都要删除以前可能遗留的workerTemp员工缓存表中的数据
		wtService.deleteByUserId(userId);
		logger.debug("importWorker:{}");
		// 初始化上传文件目录
		String upload = "upload";
		String workerFolder = "worker";
		String url = request.getServletContext().getRealPath("/");
		String upLoadPath = url + upload + File.separator + workerFolder
				+ File.separator;
		File uploadPath = new File(url + "upload");
		File tempPath = new File(uploadPath + File.separator + workerFolder);
		// 创建 上传目录
		if (!uploadPath.exists()) {
			logger.debug(upload + " Does not exist,Create ‘" + upload
					+ "’ Folder");
			uploadPath.mkdir();
		}
		if (!tempPath.exists()) {
			logger.debug(workerFolder + " Does not exist,Create ‘"
					+ workerFolder + "’ Folder");
			tempPath.mkdir();
		}

		// Map<String, String> paramMap = importfile(upLoadPath, request,
		// response);

		Map<String, String> paramMap = new HashMap<String, String>();

		// 上传文件
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(request
				.getSession().getServletContext());
		// 如果有文件的话
		if (cmr.isMultipart(request)) {
			MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
			Iterator<String> it = mhsr.getFileNames();
			while (it.hasNext()) {
				// 原来的文件
				String orgName = it.next();
				MultipartFile mf = mhsr.getFile(orgName);
				String fileName = mf.getOriginalFilename();
				// 检查文件后缀格式
				String fileEnd = fileName.substring(
						fileName.lastIndexOf(".") + 1).toLowerCase();
				// 创建文件唯一名称
				String uuid = UUID.randomUUID().toString();
				// 真实上传路径
				StringBuffer sbRealPath = new StringBuffer();
				sbRealPath.append(upLoadPath).append(uuid).append(".")
						.append(fileEnd);
				// 写入文件
				File file = new File(sbRealPath.toString());
				try {
					mf.transferTo(file);
				} catch (IllegalStateException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				logger.info("上传文件成功,filePath：" + file.getPath());
				// 返回文件路径
				paramMap.put("filePath", file.getPath());

			}
		}

		paramMap.put("companyId", request.getParameter("companyId").toString());
		paramMap.put("year", request.getParameter("year").toString());
		String fileError = paramMap.get("fileError");// 错误信息
		if (fileError != null) {
			// 上传失败，返回错误信息
			request.setAttribute("errorInfo", fileError);
			return new ModelAndView("basicInfo/worker_importInfo");
		}
		// 上传文件返回的参数信息
		String filePath = paramMap.get("filePath").toString();// 文件路径
		Integer companyId = Integer.valueOf(paramMap.get("companyId"));// companyID
		String year = paramMap.get("year");// 年份
		AuditParameter auditParameter = auditParameterService.getByYear(year);
		// 文件上传成功，进入解析阶段

		// List<Worker> workerErrorList = new ArrayList<Worker>();// 错误信息列表
		// List<Worker> workerCorrectList = new ArrayList<Worker>();// 正常信息列表
		List<Worker> list = null;

		if (fileError == null) {
			try {
				File f = new File(filePath);
				// 读取excel
				list = WorkerUtil.parse(f, 0);
				// 将员工总条数放入到常量字段中
				workerCount = list.size();
				if (list == null || list.size() <= 0) {
					// excel文件内部文本信息格式错误
					logger.error("importWorkerError:{}", WORDERROR);
					request.setAttribute("errorInfo", WORDERROR);
					// 返回成功页面
					return new ModelAndView("basicInfo/worker_importInfo");
				}
				for (int i = 0; i < list.size(); i++) {
					// 存入到缓存表中的对象
					WorkerTemp t = new WorkerTemp();
					t.setUserId(userId);
					// 默认为false--即“不合格”
					t.setIsOk(false);
					Worker w = null;
					// 当前进行到的条数
					currentCount = i + 1;
					try {
						Worker worker = list.get(i);
						// 校验部分
						String workerHandicapCode = worker
								.getWorkerHandicapCode();
						// 员工姓名
						String workerName = worker.getWorkerName().replace(" ",
								"");// 去除所有空格
						w = new Worker();
						w.setWorkerName(worker.getWorkerName());
						w.setWorkerHandicapCode(workerHandicapCode);
						// 员工缓存对象
						t.setWorkerName(workerName);
						t.setWorkerHandicapCode(workerHandicapCode);
						// 1.校验姓名是否为空
						if (StringUtils.isEmpty(workerName)
								|| StringUtils.equals(workerName, "null")) {
							// 存储错误信息
							w.setRemark(NAMENULL);
							// workerErrorList.add(w);
							t.setRemark(NAMENULL);
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									NAMENULL);
							continue;
						}
						// 2.校验姓名长度
						if (workerName.length() > 20) {
							// 存储错误信息
							w.setRemark("姓名长度不符");
							// workerErrorList.add(w);
							t.setRemark("姓名长度不符");
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									"姓名长度不符");
							continue;
						}
						// 3.校验残疾证号是否为空
						if (StringUtils.isBlank(workerHandicapCode)
								|| StringUtils.equals(workerHandicapCode,
										"null")) {
							// 存储错误信息
							w.setRemark(LENGTHERROR);
							// workerErrorList.add(w);
							t.setRemark(LENGTHERROR);
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									LENGTHERROR);
							continue;
						}
						// 以下校验残疾证号
						workerHandicapCode.replace(" ", "");// 去掉所有空格
						// 4.校验残疾证号长度
						if (workerHandicapCode.length() < MIN_HANDICAPCODE
								|| workerHandicapCode.length() > MAX_HANDICAPCODE) {
							// 存储错误信息
							w.setRemark(LENGTHERROR);
							// workerErrorList.add(w);
							t.setRemark(LENGTHERROR);
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									LENGTHERROR);
							continue;
						}
						// 5.校验残疾证号是否含有中文
						if (CommonUtil.chineseValid(workerHandicapCode)) {
							// 存储错误信息
							w.setRemark(ILLEGALSTR);
							// workerErrorList.add(w);
							t.setRemark(ILLEGALSTR);
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									LENGTHERROR);
							continue;
						}
						// 6.校验20之前是否有其他字符
						String handicapStr = workerHandicapCode
								.substring(0, 17);
						if (!handicapStr.matches("\\d+")) {
							w.setRemark("残疾证号前17位有非法字符");
							// workerErrorList.add(w);
							t.setRemark("残疾证号前17位有非法字符");
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									TYPEERROR);
							continue;
						}
						// 7.校验残疾类型
						String handicapTypeStr = workerHandicapCode.substring(
								18, 19);
						boolean ishandicapType = handicapTypeStr
								.matches("\\d+");// 返回true为纯数字,否则就不是纯数字
						// 8.校验是否数数字
						if (ishandicapType) {
							int handicapType = Integer.valueOf(handicapTypeStr);
							if (handicapType > 7 || handicapType == 0) {
								w.setRemark(TYPEERROR);
								// workerErrorList.add(w);
								t.setRemark(TYPEERROR);
								wtService.save(t);
								logger.error("impoerWorkerError:{},info:{}", w,
										TYPEERROR);
								continue;
							}
						}

						// 9.校验残疾证号等级
						String handicapLevelStr = workerHandicapCode.substring(
								19, 20);
						boolean ishandicapLevel = handicapLevelStr
								.matches("\\d+");// 返回true为纯数字,否则就不是纯数字
						if (ishandicapLevel) {
							int handicapLevel = Integer
									.valueOf(handicapLevelStr);
							if (handicapLevel > 4 || handicapLevel == 0) {
								w.setRemark(LEVELERROR);
								// workerErrorList.add(w);
								t.setRemark(LEVELERROR);
								wtService.save(t);
								logger.error("impoerWorkerError:{},info:{}", w,
										LEVELERROR);
								continue;
							}
						}

						// 10.校验职工年龄
						List<String> ageResult = new WorkerUtil().ageVerifi(
								workerHandicapCode, auditParameter);
						if (ageResult != null) {
							String ageErrorInfo = "该员工性别为："
									+ ageResult.get(0).toString() + ",年龄为："
									+ ageResult.get(1).toString() + "。"
									+ ageResult.get(2).toString();
							w.setRemark(ageErrorInfo);
							// workerErrorList.add(w);
							t.setRemark(ageErrorInfo);
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									ageErrorInfo);
							continue;
						}

						// 11.校验身份证号重复性
						Map<String, Object> validateResult = validateWorkerIdentityCode(
								workerHandicapCode.substring(0, 18), year);
						logger.debug("LineNumber:{},validataType:{}", i,
								validateResult.get("type"));
						String notice = validateResult.get(Constants.NOTICE)
								.toString();
						// 12.第一种情况 存在，并且在其他公司内。
						if (StringUtils.equals(notice, "inCompany")) {
							// 存储错误信息
							String errinfo = "职工已被："
									+ validateResult.get("companyName")
									+ " 单位录用，单位档案编码为："
									+ validateResult.get("companyCode");
							w.setRemark(errinfo);
							// workerErrorList.add(w);
							t.setRemark(errinfo);
							wtService.save(t);
							logger.error("impoerWorkerError:{},info:{}", w,
									errinfo);
							continue;
						}
						// 正常存储：.第二种情况：存在，并且不再任何公司。 第三种情况： 不存在数据库中，进行存储
						if (StringUtils.equals(notice, "exists")
								|| StringUtils.equals(notice, "notExists")) {
							Worker workerUp = new Worker();
							workerUp.setWorkerName(worker.getWorkerName());
							workerUp.setWorkerHandicapCode(workerHandicapCode);

							Worker workerCorrect = WorkerUtil
									.assembly(workerHandicapCode);
							// workerCorrectList.add(workerCorrect);
							// 将正确的残疾职工信息导入到数据 员工缓存表中
							t.setWorkerName(workerCorrect.getWorkerName());
							t.setWorkerHandicapCode(workerCorrect
									.getWorkerHandicapCode());
							t.setIsOk(true);
							t.setWorkerBirth(workerCorrect.getWorkerBirth());
							t.setWorkerBirthYear(workerCorrect
									.getWorkerBirthYear());
							t.setWorkerGender(workerCorrect.getWorkerGender());
							t.setWorkerHandicapLevel(workerCorrect
									.getWorkerHandicapLevel().getId());
							t.setWorkerHandicapType(workerCorrect
									.getWorkerHandicapType().getId());
							t.setWorkerIdCard(workerCorrect.getWorkerIdCard());
							// 如果该残疾人存在, 但不在任何公司中, 则将其原来的id保存起来
							if (StringUtils.equals(notice, "exists")) {
								t.setPreId(Integer.parseInt(validateResult.get(
										"workerId").toString()));
							}
							wtService.save(t);
							continue;
						}
					} catch (Exception e) {
						w.setRemark("未知错误");
						// workerErrorList.add(w);
						t.setRemark("未知错误");
						wtService.save(t);
						logger.error("impoerWorkerUpError:{}", "false");
					}
				}
				// 循环结束

				// 检测是否有错误数据
				List<WorkerTemp> workerErrorList = wtService.getByCheck(false,
						userId);// 错误信息列表
				if (workerErrorList.size() != 0) {
					String errorFilePath = upLoadPath + companyId + ".xls";
					// 错误列表是否创建成功
					if (PoiCreateExcel.createExcel(errorFilePath,
							workerErrorList)) {
						logger.debug("upLoadErrorListCreateSuccess!");
						String destPath = request.getLocalAddr() + ":"
								+ request.getLocalPort()
								+ request.getContextPath();
						// 返回错误列表文件下载地址
						request.setAttribute("errorFilePath", "http://"
								+ destPath + "/" + upload + "/" + workerFolder
								+ "/" + companyId + ".xls");//
					} else {
						logger.error("upLoadErrorListCreateError");
						request.setAttribute("errorInfo", CREATEERRORFILE);
					}
				}
				// 删除上传文件
				f.delete();

				// int totalLength = wtService.getCountByCheck(null, userId,
				// null);
				// int succesLength = wtService.getCountByCheck(true, userId,
				// null);
				// int errorLength = totalLength - succesLength;
				int totalLength = 0;
				int succesLength = 0;
				int errorLength = 0;
				// 检测是否有导入失败数据
				if (workerErrorList != null) {
					errorLength = workerErrorList.size();
				}
				// // 检测正确数据总量
				if (list != null) {
					totalLength = list.size();
					succesLength = totalLength - errorLength;
				}
				request.setAttribute("totalLength", totalLength);// 总条数
				request.setAttribute("errorLength", errorLength);// 失败条数
				request.setAttribute("succesLength", succesLength);// 成功条数
				// request.setAttribute("workerCorrectList", workerCorrectList);
				// //正确条数, 要传递到前台显示的
				// // 返回成功数据
				request.setAttribute("errorInfo", "null");// 没有发出错误信息
				request.setAttribute("companyId", companyId);// 续传单位iD
				request.setAttribute("year", year);// 续传年份

				logger.debug("totalLength:{}", totalLength);// 总条数
				logger.debug("errorLength:{}", errorLength);// 失败条数
				logger.debug("succesLength:{}", succesLength);// 成功条数

				// 清理部分
				list.clear();
				list = null;
				workerErrorList.clear();// 清除错误列表数据
				workerErrorList = null;

			} catch (IllegalStateException e) {
				e.printStackTrace();
				logger.error("importWorkerError:{}", e.getMessage());
			} catch (IOException e) {
				logger.error("importWorkerError:{}", e.getMessage());
			}
		}

		// 返回成功页面
		return new ModelAndView("basicInfo/worker_importInfo");
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
	 * 校验残疾证号
	 * 
	 * @param workerIdCard
	 * @return
	 */
	private Map<String, Object> validateWorkerIdentityCode(String workerIdCard,
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
	 * 确定导入企业信息
	 * 
	 * @param companyId
	 * @param year
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/determineimport", method = RequestMethod.POST)
	@ResponseBody
	public Boolean importworker1(
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
		if (totalWorkers != 1000) {
			circulationTimes = (totalWorkers / 1000) > 1 ? ((totalWorkers / 1000) + 1)
					: 1;
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
				worker.setRemark(wt.getRemark());
				worker.setUserId(userId);
				if (!workerService.save(worker, companyId, year)) {
					bl = false;
				}
			}
		}
		return bl;
		// int start = 0;
		// boolean b = true;
		// logger.debug("importWorkerparamsCode:{},companyId:{},year:{}",
		// paramsCode, companyId, year);
		// try {
		// for (int i = 0; i < paramsCode.length; i++) {
		// // 残疾证号
		// String workerHandicapCode = paramsCode[i].toString();
		// // 名字
		// String workerHandicapName = paramsName[i].toString();
		//
		// // 身份证号，重复性检测
		// List<Map<String, String>> validateList = validateOrganizationCode(
		// workerHandicapCode.substring(0, 18), year);
		// Map<String, String> validateResult = validateList.get(0);
		//
		// // 第一种情况 存在，并且在其他公司内。
		// if (StringUtils.equals(validateResult.get("type"), "1")) {
		// logger.error("importWorkerError:{}", "存在其他公司");
		// b = false;
		// break;
		// }
		// // 第二种情况：存在，并且不再任何公司。
		// if (StringUtils.equals(validateResult.get("type"), "2")) {
		// Worker workerUp = new Worker();
		// workerUp.setWorkerName(workerHandicapName);
		// workerUp.setWorkerHandicapCode(workerHandicapCode);
		// // 更新职工信息
		// if (editWorkerUp(WorkerUtil.assembly(workerUp), companyId,
		// year)) {
		// logger.debug("impoerWorkerUp:{}", "success");
		// } else {
		// logger.error("importWorkerError:{}", "upData失败");
		// b = false;
		// break;
		// }
		// continue;
		// }
		// // 第三种情况： 不存在数据库中，进行存储
		// if (StringUtils.equals(validateResult.get("type"), "3")) {
		// Worker workerUp = new Worker();
		// workerUp.setWorkerName(workerHandicapName);
		// workerUp.setWorkerHandicapCode(workerHandicapCode);
		// // 组装职工对象 并增加
		// if (addWorker(WorkerUtil.assembly(workerUp), companyId,
		// year)) {
		// logger.debug("importWorkerAddResult:{}", "success");
		// } else {
		// logger.error("importWorkerError:{}", "save失败");
		// b = false;
		// break;
		// }
		// }
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// logger.error("delete_worker{}", e.getMessage());
		// }
		// return b;
	}

}
