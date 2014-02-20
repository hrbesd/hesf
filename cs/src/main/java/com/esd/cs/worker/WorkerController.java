/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.worker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.common.CommonUtil;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditService;
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
	@Autowired
	private AuditParameterService auditParameterService;// 年审参数
	@Autowired
	Properties fileUploadPro = null;

	// 身份证号长度
	static int HANDICAPCODE = 20;

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
	static String WORDERROR = "excel文件内部文本信息格式错误";

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

		// 获取年审参数
		AuditParameter param = auditParameterService.getByYear(year);
		if (param != null) {
			// 男职工退休年龄
			request.setAttribute("maleRetirementAge", param.getRetireAgeMale());
			// 女职工退休年龄
			request.setAttribute("femaleRetirementAge", param.getRetireAgeFemale());
		} else {
			logger.error("getAuditParameterError");
		}
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
		try {
			String companyId = request.getParameter("companyId");
			Company c = companyService.getByPrimaryKey(companyId);
			boolean b = workerService.save(worker, c.getCompanyCode());
			logger.debug("addWorker:{},Result:{}", worker, b);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addWorkerError:{}", e.getMessage());
			return null;
		}
	}

	private boolean addWorker(Worker worker, String companyId) {
		logger.debug("addWorkerParams:{},companyId:{}", worker, companyId);
		Company c = companyService.getByPrimaryKey(companyId);
		if (c == null) {
			logger.error("addWorker_getCompanyError:{}", "null");
			return false;
		}
		// 设置年份
		worker.setWorkerBirthYear(CalendarUtil.getNowYear());
		boolean b = workerService.save(worker, c.getCompanyCode());
		logger.debug("addWorkerResult:{}", b);
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
	 * 转到导入残疾职工页面
	 * 
	 * @param worker
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/importworker", method = RequestMethod.GET)
	public ModelAndView importworker() {
		logger.debug("goToWorker_import");
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
	@ResponseBody
	public Boolean edit_worker_up(Worker worker, HttpServletRequest request) {
		logger.debug("editUpdata:{}", worker);
		String companyId = request.getParameter("companyId");

		return editWorkerUp(worker, companyId);
	}

	private boolean editWorkerUp(Worker worker, String companyId) {
		boolean workerUpDataStatus = false, companyUpdataStatus = false;
		try {
			System.out.println(worker.getWorkerHandicapCode() + "====================");
			// 根据残疾证号获取版本号
			Worker w = workerService.getByWorkerHandicapCode(worker.getWorkerHandicapCode());
			if (w == null) {
				logger.error("UpWorkerError:{},info:{}", "notWorkerHandicapCode", worker);
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
					logger.debug("upData_workerCompanyParamsWorkerId:{},companyCode:{},year:{},workerCurrenJob:{}", worker.getId(), c.getCompanyCode(), CalendarUtil.getNowYear(), worker.getCurrentJob());
					companyUpdataStatus = workerService.changeCompany(worker.getId(), c.getCompanyCode(), CalendarUtil.getNowYear(), worker.getCurrentJob());
					if (companyUpdataStatus) {
						logger.debug("workerUpDataGetCompanyResult:{}", companyUpdataStatus);
					} else {
						logger.error("workerUpDataGetCompanyResult:{}", companyUpdataStatus);
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
	 * 导入残疾职工文件
	 */
	public Map<String, String> importfile(HttpServletRequest request, HttpServletResponse response) {
		
		// 获取并解析文件类型和支持最大值
		String fileType = "xls";
		String maxSize = 2 + "";
		String url = request.getServletContext().getRealPath("/");
		// 临时目录名
		String tempPath = url + "upload" + File.separator + "temp" + File.separator;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 最大缓存
		factory.setSizeThreshold(5 * 1024);
		// 设置临时文件目录
		factory.setRepository(new File(tempPath));
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		Map<String,String> result=new HashMap<String,String>();
		
		if (maxSize != null && !"".equals(maxSize.trim())) {
			// 文件最大上限
			upload.setSizeMax(Integer.valueOf(maxSize) * 1024 * 1024);
		}
		try {
			// 获取所有文件列表
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				 // 如果是文件项，则保存文件到上传目录
				if (!item.isFormField()) {
					// 文件名
					String fileName = item.getName();
					// 检查文件后缀格式
					String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if (fileType != null && !"".equals(fileType.trim())) {
						boolean isRealType = false;
						String[] arrType = fileType.split(",");
						for (String str : arrType) {
							if (fileEnd.equals(str.toLowerCase())) {
								isRealType = true;
								break;
							}
						}
						if (!isRealType) {
							// 提示错误信息:文件格式不正确
							logger.error("loadUpWorkerFileTypeError");
							return null;
						}
					}
					// 创建文件唯一名称
					String uuid = UUID.randomUUID().toString();
					// 真实上传路径
					StringBuffer sbRealPath = new StringBuffer();
					sbRealPath.append(tempPath).append(uuid).append(".").append(fileEnd);
					// 写入文件
					File file = new File(sbRealPath.toString());
					item.write(file);
					logger.info("上传文件成功,filePath：" + file.getPath());
					//返回文件路径
					result.put("filePath", file.getPath());
				}else{
					//企业id
					result.put("companyId", item.getString());
				}
			}
		} catch (Exception e) {
			// 提示错误:比如文件大小
			// super.printJsMsgBack(response, );
			result.put("fileError", "上传失败,文件大小不能超过"+maxSize+"M!");
			logger.error("上传文件异常!");
			return result;
		}
		return result;
	}

	/**
	 * 导入残疾职工文件
	 */
	@RequestMapping(value = "/importworker", method = RequestMethod.POST)
	public ModelAndView importworker(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("importWorker:{}");
		//上传文件
		Map<String, String> paramMap = importfile(request, response);
		String filePath		=paramMap.get("filePath");//文件路径
		String companyId =paramMap.get("companyId");//文件路径
		String fileError			=paramMap.get("fileError");//错误信息
		
		// 错误信息列表
		List<Worker> workerErrorList = new ArrayList<Worker>();
		List<Worker> list = null;
		String url = request.getServletContext().getRealPath("/");
		if (fileError== null) {
			try {
				File f = new File(filePath);
				//读取excel
				list = WorkerUtil.parse(f, 0);
				if (list == null || list.size() <= 0) {
					//excel文件内部文本信息格式错误
					logger.error("importWorkerError:{}", WORDERROR);
					request.setAttribute("errorInfo", WORDERROR);
					// 返回成功页面
					return new ModelAndView("basicInfo/worker_importInfo");
				}
				for (int i = 0; i < list.size(); i++) {
					Worker worker = list.get(i);
					// 校验部分
					String workerHandicapCode = worker.getWorkerHandicapCode();
					// 员工姓名
					String workerName = worker.getWorkerName().replace(" ", "");// 取出所有空格
					Worker w = new Worker();
					w.setWorkerName(worker.getWorkerName());
					w.setWorkerHandicapCode(workerHandicapCode);

					// 1.校验姓名
					if (StringUtils.isEmpty(workerName) || StringUtils.equals(workerName, "null")) {
						// 存储错误信息
						w.setRemark(NAMENULL);
						workerErrorList.add(w);
						logger.error("impoerWorkerError:{},info:{}", w, NAMENULL);
						continue;
					}
					// 2.校验残疾证号长度
					if (workerHandicapCode == null) {
						// 存储错误信息
						w.setRemark(LENGTHERROR);
						workerErrorList.add(w);
						logger.error("impoerWorkerError:{},info:{}", w, LENGTHERROR);
						continue;
					} else {
						workerHandicapCode.replace(" ", "");// 去掉所有空格
						// 3.校验残疾证号是否含有中文
						if (CommonUtil.chineseValid(workerHandicapCode)) {
							// 存储错误信息
							w.setRemark(ILLEGALSTR);
							workerErrorList.add(w);
							logger.error("impoerWorkerError:{},info:{}", w, LENGTHERROR);
							continue;
						}
						// 4.校验残疾证号长度
						if (!(workerHandicapCode.length() == HANDICAPCODE)) {
							// 存储错误信息
							w.setRemark(LENGTHERROR);
							workerErrorList.add(w);
							logger.error("impoerWorkerError:{},info:{}", w, LENGTHERROR);
							continue;
						}
					}
					// 5.校验残疾类型
					int handicapType = Integer.valueOf(workerHandicapCode.substring(18, 19));
					if (handicapType > 7 || handicapType == 0) {
						w.setRemark(TYPEERROR);
						workerErrorList.add(w);
						logger.error("impoerWorkerError:{},info:{}", w, TYPEERROR);
						continue;
					}

					// 6.校验残疾证号等级
					int handicapLevel = Integer.valueOf(workerHandicapCode.substring(19, 20));
					if (handicapLevel > 4 || handicapLevel == 0) {
						w.setRemark(LEVELERROR);
						workerErrorList.add(w);
						logger.error("impoerWorkerError:{},info:{}", w, LEVELERROR);
						continue;
					}
					// 7.校验职工年龄
					List<String> ageResult = new WorkerUtil().ageVerifi(workerHandicapCode, auditParameterService.getByYear(CalendarUtil.getNowYear()));
					if (ageResult != null) {
						String ageErrorInfo = "该员工性别为：" + ageResult.get(0).toString() + ",年龄为：" + ageResult.get(1).toString() + "。已超过退休年龄。";
						w.setRemark(ageErrorInfo);
						workerErrorList.add(w);
						logger.error("impoerWorkerError:{},info:{}", w, ageErrorInfo);
						continue;
					}
					// 8.校验身份证号，重复性检测
					List<Map<String, String>> validateList = validateOrganizationCode(workerHandicapCode.substring(0, 18));
					Map<String, String> validateResult = validateList.get(0);
					logger.debug("LineNumber:{},validataType:{}", i, validateResult.get("type"));
					// 9.第一种情况 存在，并且在其他公司内。
					if (StringUtils.equals(validateResult.get("type"), "1")) {
						// 存储错误信息
						String errinfo = "职工已被：" + validateList.get(1).get("companyName") + " 单位录用，单位档案编码为：" + validateList.get(1).get("companyCode");
						w.setRemark(errinfo);
						workerErrorList.add(w);
						logger.error("impoerWorkerError:{},info:{}", w, errinfo);
						continue;
					}
					// 10.第二种情况：存在，并且不再任何公司。
					if (StringUtils.equals(validateResult.get("type"), "2")) {
						Worker workerUp = new Worker();
						workerUp.setWorkerName(worker.getWorkerName());
						workerUp.setWorkerHandicapCode(workerHandicapCode);
						logger.debug("impoerWorkerUp:{},companyId:{}", workerUp, companyId);
						if (editWorkerUp(workerUp, companyId)) {
							logger.debug("impoerWorkerUp:{}", "success");
						} else {
							w.setRemark("员工存在数据库内，更新时未成功");
							workerErrorList.add(w);
							logger.error("impoerWorkerUpError:{}", "false");

						}
						continue;
					}
					// 正常存储
					// 第三种情况： 不存在数据库中，进行存储
					logger.error("员工信息正常，可以进行存储：" + worker.getWorkerName());
					Worker workerUp = new Worker();
					workerUp.setWorkerName(w.getWorkerName());
					workerUp.setWorkerHandicapCode(workerHandicapCode);
					// 组装职工对象 并增加
					if (addWorker(WorkerUtil.assembly(workerUp), companyId)) {
						logger.debug("importWorkerAddResult:{}", "success");
					} else {
						// 增加失败
						w.setRemark("员工存储时未成功");
						workerErrorList.add(w);
						logger.error("impoerWorkerSaveError:{}", "false");

					}
				}
		
				// 检测是否有未导入数据
				if (workerErrorList.size() != 0) {
					String errorFilePath = url + "upload" + File.separator + "temp" + File.separator + companyId + ".xls";
					boolean result = PoiCreateExcel.createExcel(errorFilePath, workerErrorList);
					String destPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
					// 返回错误列表文件下载地址
					request.setAttribute("errorFilePath", "http://" + destPath + "/upload/temp/" + companyId + ".xls");//
				}
				//删除上传文件
				//f.delete();
				
				
			} catch (IllegalStateException e) {
				logger.error("importWorkerError:{}", e.getMessage());
			} catch (IOException e) {
				logger.error("importWorkerError:{}", e.getMessage());
			}
			int totalLength = 0;
			int errorLength = 0;
			int succesLength = 0;
			// 检测是否有导入失败数据
			if (list != null) {
				totalLength = list.size();
				errorLength = workerErrorList.size();
				succesLength = totalLength - errorLength;
			}
			request.setAttribute("totalLength", totalLength);// 总条数
			request.setAttribute("errorLength", errorLength);// 失败条数
			request.setAttribute("succesLength", succesLength);// 成功条数
			request.setAttribute("errorInfo", "null");//没有错误信息
			// 清理部分
			workerErrorList.clear();// 清楚错误列表数据
			workerErrorList = null;
			// 返回成功页面
			return new ModelAndView("basicInfo/worker_importInfo");
		} else {
			request.setAttribute("errorInfo", fileError);
			// 返回失败页面
			logger.error("importUpLoadError");
			return new ModelAndView("basicInfo/worker_importInfo");
		}
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
		// 参数 年份 残疾证号
		// 1.存在，并且在其他公司内 返回公司对象，前台提示在哪个公司内 2.存在，不在其他公司内。 返回公司id，前台调用更新方法 3.不存在
		logger.debug("validate_workerIdCardParams:{}", workerIdCard);
		return validateOrganizationCode(workerIdCard);
	}

	/**
	 * 校验残疾证号
	 * 
	 * @param workerIdCard
	 * @return
	 */
	private List<Map<String, String>> validateOrganizationCode(String workerIdCard) {
		logger.debug("validateOrganizationCode:{}", workerIdCard);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		Company company = workerService.retrieveCompanyByWorker(CalendarUtil.getNowYear(), workerIdCard);
		// 第一种情况 存在，并且在其他公司内。
		if (company != null) {
			paramsMap.put("type", "1");
			Map<String, String> companyMap = new HashMap<String, String>();
			companyMap.put("companyName", company.getCompanyName());
			companyMap.put("companyCode", company.getCompanyCode());
			companyMap.put("companyTaxCode", company.getCompanyTaxCode());
			list.add(paramsMap);
			list.add(companyMap);
			logger.debug("validate_workerHandicapCodeResult:{},company:{}", "trpe:1。职工存在，并且在其他公司内", company.getCompanyName() + "  " + company.getCompanyCode());
			return list;
		} else {
			Worker w = workerService.getByWorkerIdCard(workerIdCard);
			// 第二种情况：存在，并且不再任何公司。
			if (w != null) {
				logger.debug("validate_workerHandicapCodeResult:{}", "trpe:2。职工" + w.getWorkerName() + "存在数据库中，并且不再任何公司");
				paramsMap.put("type", "2");
				list.add(paramsMap);
				return list;
				// 第三种情况，不存在.
			} else {
				logger.debug("validate_workerHandicapCodeResult:{}", "trpe:3。职工不存在数据库中");
				paramsMap.put("type", "3");
				list.add(paramsMap);
				return list;
			}
		}
	}

}
