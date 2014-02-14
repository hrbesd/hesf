/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.audit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyProperty;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.CompanyPropertyService;
import com.esd.hesf.service.CompanyService;

/**
 * 初审管理控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/security/audits")
public class AuditsController {
	private static final Logger logger = LoggerFactory.getLogger(AuditsController.class);

	@Autowired
	private AuditService auditService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AuditParameterService auditParameterService;
	@Autowired
	private AuditProcessStatusService auditProcessStatusService;

	@Autowired
	private CompanyPropertyService companyPropertyService;
	@Autowired
	private CompanyEconomyTypeService companyEconomyTypeService;

	private static List<CompanyProperty> companyPropertys;
	private static List<CompanyEconomyType> companyEconomyTypes;

	/**
	 * 转到初审单位列表页面
	 */
	@RequestMapping(value = "/list/{process}", method = RequestMethod.GET)
	public ModelAndView initAudit_list(@PathVariable(value = "process") Integer process, HttpServletRequest request) {
		String nowYear = CalendarUtil.getNowYear();
		request.setAttribute("nowYear", nowYear);
		request.setAttribute("process", process);
		return new ModelAndView("audit/audit_list");
	}

	/**
	 * 拒绝
	 * 
	 * @param audit
	 * @return
	 */
	@RequestMapping(value = "/refusal", method = RequestMethod.POST)
	@ResponseBody
	public Boolean refusal(Audit audit, HttpSession session) {
		Integer id = audit.getId();
		Audit getAudit = auditService.getByPrimaryKey(id);
		getAudit.setVerifyAuditComment(audit.getVerifyAuditComment());
		getAudit.setVerifyAuditDate(new Date());// 添加复审时间
		getAudit.setVerifyAuditUserId((Integer) session.getAttribute(Constants.USER_ID));// 添加复审ID
		AuditProcessStatus auditProcessStatus = auditProcessStatusService.getByPrimaryKey(Constants.PROCESS_STATIC_WCS);
		getAudit.setAuditProcessStatus(auditProcessStatus);
		logger.debug(getAudit.toString());
		auditService.update(getAudit);
		return true;
	}

	/**
	 * 复审
	 * 
	 * @param audit
	 * @return
	 */
	@RequestMapping(value = "/verifyAudit", method = RequestMethod.POST)
	@ResponseBody
	public Boolean verifyAudit(Audit audit, HttpSession session) {
		Integer id = audit.getId();
		Audit getAudit = auditService.getByPrimaryKey(id);
		getAudit.setVerifyAuditComment(audit.getVerifyAuditComment());
		getAudit.setVerifyAuditDate(new Date());// 添加复审时间
		getAudit.setVerifyAuditUserId((Integer) session.getAttribute(Constants.USER_ID));// 添加复审ID
		AuditProcessStatus auditProcessStatus = null;
		if (getAudit.getActualAmount().signum() == 0) {
			auditProcessStatus = auditProcessStatusService.getByPrimaryKey(Constants.PROCESS_STATIC_OK);
		} else {
			auditProcessStatus = auditProcessStatusService.getByPrimaryKey(Constants.PROCESS_STATIC_WJK);
		}
		getAudit.setAuditProcessStatus(auditProcessStatus);
		logger.debug(getAudit.toString());
		auditService.update(getAudit);
		return true;
	}

	/**
	 * 初审
	 * 
	 * @param audit
	 * @return
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public Boolean audit(Audit audit, HttpSession session) {
		logger.debug(audit.toString());
		Company company = audit.getCompany();
		if (company != null) {
			logger.debug(company.toString());
			boolean b = companyService.update(company);
			logger.debug("save Company:{}", b);
			if (b == true) {
				audit.setInitAuditDate(new Date()); // 添加审计时间
				// 添加审计人
				audit.setInitAuditUserId((Integer) session.getAttribute(Constants.USER_ID));
				// 更改审计状态
				AuditProcessStatus auditProcessStatus = auditProcessStatusService.getByPrimaryKey(Constants.PROCESS_STATIC_WFS);
				audit.setAuditProcessStatus(auditProcessStatus);// 设置为未复审
				auditService.update(audit);
				return true;
			}
		}
		return false;
	}

	/**
	 * 转到初审单位列表页面
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean save(Audit audit) {
		logger.debug(audit.toString());
		Company company = audit.getCompany();
		if (company != null) {
			logger.debug(company.toString());
			logger.debug("getCompanyAlreadyTotal:{}", company.getCompanyAlreadyTotal());
			boolean b = companyService.update(company);
			logger.debug("save Company:{}", b);
			if (b == true) {
				auditService.update(audit);
				return true;
			}
		}
		return false;
	}

	/**
	 * 计算保障金公式
	 * 
	 * @param calculateModel
	 * @return
	 */
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	@ResponseBody
	public CalculateModel calculateModel(CalculateModel calculateModel) {
		logger.debug(calculateModel.toString());
		String companyCode = calculateModel.getCompanyCode();
		String year = calculateModel.getYear();
		AuditParameter auditParameter = auditParameterService.getByYear(year);

		// 获得在职员工总数
		Integer zaiZhiYuanGongZongShu = calculateModel.getZaiZhiYuanGongZongShu();
		// 获得已录入排数
		Integer yiLuRuCanJiRen = calculateModel.getYiLuRuCanJiRen();
		List<WorkerCalculator> list = auditParameterService.getSpecialSetting(year);
		for (WorkerCalculator workerCalculator : list) {
			Integer per = workerCalculator.getPer().intValue();
			Integer type = workerCalculator.getType();
			Integer lvl = workerCalculator.getLvl();
			Integer num = auditParameterService.getSpecialCount(companyCode, year, type, lvl);
			logger.debug("type:{},lvl:{},per:{}", type, lvl, per);
			yiLuRuCanJiRen = ((yiLuRuCanJiRen - num) + (num * per));
		}
		// 获取残疾人安排比例
		BigDecimal putScale = auditParameter.getPutScale();
		// 计算出应安排人数
		BigDecimal yingAnPaiCanJiRen = putScale.multiply(new BigDecimal(zaiZhiYuanGongZongShu));
		calculateModel.setYingAnPaiCanJiRen(yingAnPaiCanJiRen);// 添加
		// 获得已录入排数
		// Integer yiLuRuCanJiRen = calculateModel.getYiLuRuCanJiRen();
		// 获得预定残疾人数
		Integer yuDingCanJiRen = calculateModel.getYuDingCanJiRen();
		// 计算出在岗所有残疾人
		Integer yiAnPaiCanJiRen = yiLuRuCanJiRen + yuDingCanJiRen;
		calculateModel.setYiAnPaiCanJiRen(yiAnPaiCanJiRen);// 添加
		// 获得人均工资数
		BigDecimal averageSalary = auditParameter.getAverageSalary();
		// 计算出应缴金额
		BigDecimal yingJiaoJinE = averageSalary.multiply(yingAnPaiCanJiRen.subtract(new BigDecimal(yiAnPaiCanJiRen)));
		if (yingJiaoJinE.signum() == 1) {// 如果为正数添加
			calculateModel.setYingJiaoJinE(yingJiaoJinE);
		} else {
			yingJiaoJinE = Constants.ZERO;
			calculateModel.setYingJiaoJinE(yingJiaoJinE);
		}
		// 获得减缴金额
		BigDecimal jianJiaoJinE = calculateModel.getJianJiaoJinE();
		// 应缴金额-减缴金额+补缴+上年度未缴金额 =实缴金额
		BigDecimal shiJiaoJinE = yingJiaoJinE.subtract(jianJiaoJinE);
		// 获得补缴金额
		BigDecimal buJiaoJinE = calculateModel.getBuJiaoJinE();
		// 获得上年度未缴金额
		BigDecimal shangNianDuWeiJiaoBaoZhangJin = calculateModel.getShangNianDuWeiJiaoBaoZhangJin();
		BigDecimal real_yingJiaoJinE = shiJiaoJinE.add(buJiaoJinE).add(shangNianDuWeiJiaoBaoZhangJin);
		calculateModel.setShiJiaoJinE(real_yingJiaoJinE);// 添加
		// 计算滞纳金
		Date date = auditParameter.getAuditDelayDate();
		BigDecimal zhiNaJinBiLi = auditParameter.getAuditDelayRate();
		int zhiNanJinTianshu = CalendarUtil.getDaySub(date, new Date());
		if (zhiNanJinTianshu < 0) {
			zhiNanJinTianshu = 0;
		}
		calculateModel.setZhiNaJinTianShu(zhiNanJinTianshu);
		BigDecimal zhiNaJin = real_yingJiaoJinE.multiply(zhiNaJinBiLi).multiply(new BigDecimal(zhiNanJinTianshu));
		calculateModel.setZhiNaJin(zhiNaJin);// 添加滞纳金
		// 实缴总金额
		BigDecimal shiJiaoZongJinE = real_yingJiaoJinE.add(zhiNaJin);
		calculateModel.setShiJiaoZongJinE(shiJiaoZongJinE);

		return calculateModel;
	}

	/**
	 * 获取残疾职工列表数据
	 * 
	 * @param page
	 * @param rows
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> provincePost(HttpServletRequest request) {
		String year = request.getParameter("year");
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("rows"));
		Integer process = Integer.valueOf(request.getParameter("process"));
		String companyCode = request.getParameter("companyCode");
		String companyTaxCode = request.getParameter("companyTaxCode");
		String companyName = request.getParameter("companyName");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("year", year); // 年度
		params.put("auditProcessStatus", process); // 处理状态
		params.put("companyCode", companyCode); // 公司档案号
		params.put("companyTaxCode", companyTaxCode); // 公司税务编码
		params.put("companyName", companyName); // 公司税务编码

		logger.debug("years:{},page:{},rows:{},process{}", year, page, pageSize, process);
		Map<String, Object> entity = new HashMap<>();
		try {
			Audit audit = new Audit();
			audit.setYear(year);
			AuditProcessStatus auditProcessStatus = auditProcessStatusService.getByPrimaryKey(process);
			audit.setAuditProcessStatus(auditProcessStatus);

			PaginationRecordsAndNumber<Audit, Number> query = auditService.getByMultiCondition(params);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<Audit> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				Audit it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("companyCode", it.getCompany().getCompanyCode());// 企业档案编号
				map.put("companyTaxCode", it.getCompany().getCompanyTaxCode());// 税务编号
				map.put("companyId", it.getCompany().getId());// 企业名称
				map.put("companyName", it.getCompany().getCompanyName());// 企业名称
				map.put("auditProcessStatus", it.getAuditProcessStatus().getAuditProcessStatus());// 流程状态
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("total:{},rows:{}", total, list.toString());

		} catch (Exception e) {
			logger.error("error{}", e);
		}
		return entity;
	}

	/**
	 * 转到年审单位初审页面
	 */
	@RequestMapping(value = "/edit/{id}/{process}", method = RequestMethod.GET)
	public ModelAndView initAudit(@PathVariable(value = "id") int id, @PathVariable(value = "process") int process, HttpServletRequest request) {
		logger.debug("id:{}", id);
		Audit audit = auditService.getByPrimaryKey(id);
		String year = audit.getYear();
		AuditParameter auditParameter = auditParameterService.getByYear(year);
		request.setAttribute("params", auditParameter);
		if (companyPropertys == null) {
			companyPropertys = companyPropertyService.getAll();
		}
		request.setAttribute("companyPropertys", companyPropertys);
		if (companyEconomyTypes == null) {
			companyEconomyTypes = companyEconomyTypeService.getAll();
		}
		request.setAttribute("companyEconomyTypes", companyEconomyTypes);
		request.setAttribute("process", process);

		String companyCode = audit.getCompany().getCompanyCode();
		PaginationRecordsAndNumber<Worker, Number> query = companyService.getOverproofAge(year, companyCode, 1, Integer.MAX_VALUE);
		request.setAttribute("ageEx", query.getNumber());
		String[] unAudits = companyService.getUnauditYearByCompanycode(companyCode);
		StringBuilder sb = new StringBuilder();
		for (String s : unAudits) {
			sb.append(s).append(",");
		}
		request.setAttribute("unAudityear", sb.toString());
		request.setAttribute("unAudityearNum", unAudits.length);

		return new ModelAndView("audit/audit_detail", "entity", audit);
	}

}
