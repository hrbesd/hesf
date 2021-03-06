/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.audit;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.esd.hesf.model.Accounts;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyProperty;
import com.esd.hesf.model.Reply;
import com.esd.hesf.model.User;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.service.AccountsService;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.CompanyPropertyService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.PaymentService;
import com.esd.hesf.service.ReplyService;
import com.esd.hesf.service.UserService;

/**
 * 初审管理控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/security/audits")
public class AuditsController {
	private static final Logger logger = LoggerFactory
			.getLogger(AuditsController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AuditParameterService auditParameterService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private AuditProcessStatusService auditProcessStatusService;

	@Autowired
	private CompanyPropertyService companyPropertyService;

	@Autowired
	private CompanyEconomyTypeService companyEconomyTypeService;

	@Autowired
	private ReplyService replyService;

	private static List<CompanyProperty> companyPropertys;
	private static List<CompanyEconomyType> companyEconomyTypes;
	private DecimalFormat df = new DecimalFormat("0.00");
	private DecimalFormat df4 = new DecimalFormat("0.0000");

	/**
	 * 转到初审单位列表页面
	 */
	@RequestMapping(value = "/list/{process}", method = RequestMethod.GET)
	public ModelAndView initAudit_list(
			@PathVariable(value = "process") Integer process,
			HttpSession session, HttpServletRequest request) {
		String nowYear = (String) session.getAttribute(Constants.YEAR);
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
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		getAudit.setVerifyAuditUser(user);// 添加复审ID
		AuditProcessStatus auditProcessStatus = auditProcessStatusService
				.getByPrimaryKey(Constants.PROCESS_STATIC_WTG);
		getAudit.setAuditProcessStatus(auditProcessStatus);
		logger.debug(getAudit.toString());
		getAudit.setRefuseTimes(getAudit.getRefuseTimes() + 1);
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
		Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
		User user = userService.getByPrimaryKey(userId);
		getAudit.setVerifyAuditUser(user);// 添加复审ID

		getAudit.setAmountPayable(audit.getAmountPayable());// 应缴金额
		getAudit.setReductionAmount(audit.getReductionAmount());// 减缴金额
		getAudit.setActualAmount(audit.getActualAmount());// 实际应缴金额
		getAudit.setPayAmount(audit.getPayAmount());// 实际应缴金额
		getAudit.setRemainAmount(audit.getRemainAmount());// 上年度未缴金额
		getAudit.setComplementAmount(audit.getComplementAmount());// 补缴金额
		getAudit.setDelayPayAmount(audit.getDelayPayAmount());// 滞纳金
		getAudit.setIsDelayPay(audit.getIsDelayPay());// 是否减免滞纳金
		getAudit.setIsExempt(audit.getIsExempt());// 是否免0

		AuditProcessStatus auditProcessStatus = null;
		if (getAudit.getPayAmount().signum() == 0) {
			auditProcessStatus = auditProcessStatusService
					.getByPrimaryKey(Constants.PROCESS_STATIC_OK);
		} else {
			auditProcessStatus = auditProcessStatusService
					.getByPrimaryKey(Constants.PROCESS_STATIC_WJK);
		}
		// 处理未审年度
		Integer companyId = getAudit.getCompany().getId();
		AuditProcessStatus auditProcessStatusOK = auditProcessStatusService
				.getByPrimaryKey(Constants.PROCESS_STATIC_OK);// 达标
		String[] unAudits = companyService.getUnauditYearByCompany(companyId,
				audit.getYear());
		if (unAudits != null) {
			for (String year : unAudits) {
				Audit a = auditService.getByPrimaryKey(year, companyId);
				a.setPayAmount(new BigDecimal(0));// 设置实缴总金额为0
				a.setAuditProcessStatus(auditProcessStatusOK);// 设置为达标
				a.setSupplementYear(getAudit.getYear());// 设置补缴年度
				auditService.update(a);
			}
		}
		// 处理未审年度结束
		getAudit.setAuditProcessStatus(auditProcessStatus);
		logger.debug(getAudit.toString());
		auditService.update(getAudit);

		// 生成账单
		Accounts accounts = new Accounts();
		accounts.setYear(CalendarUtil.getNowYear()); // 账目的出账年份
		accounts.setAudit(getAudit); // 账目对应的审核审核对象
		accounts.setCompany(getAudit.getCompany()); // 账单公司
		accounts.setTotalMoney(getAudit.getPayAmount()); // 实际应缴金额
		accounts.setAuditProcessStatus(getAudit.getAuditProcessStatus());
		accountsService.save(accounts);
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
				audit.setInitAuditDate(new Date()); // 添加初审计时间
				// 添加审计人
				Integer userId = (Integer) session
						.getAttribute(Constants.USER_ID);
				User user = userService.getByPrimaryKey(userId);
				audit.setInitAuditUser(user);
				// 更改审计状态
				AuditProcessStatus auditProcessStatus = auditProcessStatusService
						.getByPrimaryKey(Constants.PROCESS_STATIC_WFS);
				audit.setAuditProcessStatus(auditProcessStatus);// 设置为未复审
				auditService.update(audit);
				return true;
			}
		}
		return false;
	}

	/**
	 * 转到创建页面
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createAudit() {
		return new ModelAndView("audit/audit_create");
	}

	/**
	 * 新添加审计记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Boolean createAudit(HttpServletRequest request) {
		String year = (String) request.getParameter("year");
		String companyCode = (String) request.getParameter("companyCode");
		Boolean b = auditService.save(year, companyCode);
		return b;
	}

	/**
	 * 模糊查找公司档案号
	 * 
	 * @return
	 */
	@RequestMapping(value = "/create/{companyCode}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> findCompanyCode(
			@PathVariable(value = "companyCode") String companyCode) {
		List<Map<String, String>> list = new ArrayList<>();

		Company company = new Company();
		company.setCompanyCode(companyCode);
		PaginationRecordsAndNumber<Company, Number> query = companyService
				.getPaginationRecords(company, 1, 20);

		for (Company c : query.getRecords()) {
			Map<String, String> entity = new HashMap<>();
			entity.put("id", c.getCompanyCode());
			entity.put("text", c.getCompanyCode() + ":" + c.getCompanyName());
			list.add(entity);
		}

		return list;
	}

	/**
	 * 保存初审信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Boolean save(Audit audit) {
		logger.debug(audit.toString());
		Company company = audit.getCompany();
		if (company != null) {
			logger.debug(company.toString());
			boolean b = companyService.update(company);
			logger.debug("save Company:{}", b);
			if (b == true) {
//				AuditProcessStatus auditProcessStatus = auditProcessStatusService
//						.getByPrimaryKey(Constants.PROCESS_STATIC_WCS);
//				audit.setAuditProcessStatus(auditProcessStatus);
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
		Integer companyId = calculateModel.getCompanyId();
		String year = calculateModel.getYear();
		AuditParameter auditParameter = auditParameterService.getByYear(year);

		// 获得在职员工总数
		Integer zaiZhiYuanGongZongShu = calculateModel
				.getZaiZhiYuanGongZongShu();
		if (zaiZhiYuanGongZongShu == null) {
			zaiZhiYuanGongZongShu = 0;
		}
		// 残疾人安排比例
		BigDecimal putScale = auditParameter.getPutScale();
		// 计算出应安排人数
		// 应安排人数=单位在职职工总数*残疾人安排比例
		BigDecimal yingAnPaiCanJiRen = putScale.multiply(new BigDecimal(
				zaiZhiYuanGongZongShu)).setScale(2, BigDecimal.ROUND_HALF_UP);
		calculateModel.setYingAnPaiCanJiRen(yingAnPaiCanJiRen);// 添加应安排残疾人数
		// ========================================================================================
		// 获得已录入残疾人数
		Integer yiLuRuCanJiRen = calculateModel.getYiLuRuCanJiRen();
		// 处理残疾人残疾类型和等级不同的比例
		List<WorkerCalculator> list = auditParameterService
				.getSpecialSetting(year);
		for (WorkerCalculator workerCalculator : list) {
			Integer per = workerCalculator.getPer().intValue();
			Integer type = workerCalculator.getType();
			Integer lvl = workerCalculator.getLvl();
			Integer num = auditParameterService.getSpecialCount(companyId,
					year, type, lvl);
			logger.debug("type:{},lvl:{},per:{}", type, lvl, per);
			yiLuRuCanJiRen = ((yiLuRuCanJiRen - num) + (num * per));
		}
		// 获得预定残疾人数
		Integer yuDingCanJiRen = calculateModel.getYuDingCanJiRen();
		// 计算出在岗所有残疾人
		Integer yiAnPaiCanJiRen = yiLuRuCanJiRen + yuDingCanJiRen;
		calculateModel.setYiAnPaiCanJiRen(yiAnPaiCanJiRen);// 添加已安排残疾人数;残疾人总数
		// =========================================================================================
		// 本地区上年度职工年人均工资数
		BigDecimal averageSalary = auditParameter.getAverageSalary();
		// 计算出应缴金额
		// 本地区上年度职工年人均工资数*(应安排人数﹣已安排人数)
		BigDecimal yingJiaoJinE = averageSalary.multiply(yingAnPaiCanJiRen
				.subtract(new BigDecimal(yiAnPaiCanJiRen)));
		if (yingJiaoJinE.signum() == 1) {// 如果为正数添加 负数为达标置为0
			calculateModel.setYingJiaoJinE(yingJiaoJinE);
		} else {
			yingJiaoJinE = Constants.ZERO;
			calculateModel.setYingJiaoJinE(yingJiaoJinE);
		}
		// 获得减缴金额
		BigDecimal jianJiaoJinE = calculateModel.getJianJiaoJinE();
		// 应缴金额=应缴金额-减缴金额
		BigDecimal shiJiaoJinE = yingJiaoJinE.subtract(jianJiaoJinE);
		// 获得未缴金额 --------需要计算

		// ============================================================欠缴金额 部分缴款
		List<AccountModel> qianJiaoMingXi = new ArrayList<AccountModel>();
		BigDecimal qianJiao = getSectionPaid(year, companyId, qianJiaoMingXi);
		calculateModel.setQianJiaoMingXi(qianJiaoMingXi);
		// ============================================================未审年度
		List<AccountModel> weiShenMingXi = new ArrayList<AccountModel>();
		BigDecimal weiShen = getUnAudits(year, companyId, new BigDecimal(
				zaiZhiYuanGongZongShu), weiShenMingXi);
		calculateModel.setWeiShenMingXi(weiShenMingXi);
		// =============================================================未缴款
		List<AccountModel> weiJiaoMingXi = new ArrayList<AccountModel>();
		BigDecimal weiJiao = getUnpaid(year, companyId, weiJiaoMingXi);
		calculateModel.setWeiJiaoMingXi(weiJiaoMingXi);
		// ==================================================================上年未缴金额
		logger.debug("qianJiao:{} weiShen:{} weiJiao{}", qianJiao, weiShen,
				weiJiao);
		// 上年未缴金额 =未缴+欠缴+未审
		BigDecimal shangNianDuWeiJiaoBaoZhangJin = qianJiao.add(weiShen).add(
				weiJiao);
		calculateModel
				.setShangNianDuWeiJiaoBaoZhangJin(shangNianDuWeiJiaoBaoZhangJin);
		// =====================================================================================================
		// 实缴金额=应缴金额-减缴金额+补缴金额+上年度未缴金额
		BigDecimal real_yingJiaoJinE = shiJiaoJinE
				.add(shangNianDuWeiJiaoBaoZhangJin);
		// BigDecimal real_yingJiaoJinE = shiJiaoJinE;
		calculateModel.setShiJiaoJinE(real_yingJiaoJinE);// 添加实缴金额
		// 计算滞纳金============================================================================================
		// 获得滞纳金开始时间
		Date date = auditParameter.getAuditDelayDate();
		// 获得滞纳金比例
		BigDecimal zhiNaJinBiLi = auditParameter.getAuditDelayRate();
		// 计算滞纳金天数
		int zhiNanJinTianshu = CalendarUtil.getDaySub(date, new Date());
		if (zhiNanJinTianshu < 0) {
			zhiNanJinTianshu = 0;
		}
		calculateModel.setZhiNaJinTianShu(zhiNanJinTianshu);// 添加滞纳金天数
		// 计算滞纳金
		BigDecimal zhiNaJin = real_yingJiaoJinE.multiply(zhiNaJinBiLi)
				.multiply(new BigDecimal(zhiNanJinTianshu));
		// 判断是否免除滞纳金
		Boolean mian = calculateModel.getMianZhiNaJin();
		if (mian) {
			zhiNaJin = new BigDecimal(0.00);
		}
		calculateModel.setZhiNaJin(zhiNaJin);// 添加滞纳金
		// 计算滞纳金===============================================================================================
		// 实缴总金额=实缴金额+滞纳金
		BigDecimal shiJiaoZongJinE = real_yingJiaoJinE.add(zhiNaJin);
		Boolean mianJiao = calculateModel.getMianJiao();// 获取免交状态
		if (mianJiao) {
			shiJiaoZongJinE = new BigDecimal(0.00);
		}
		calculateModel.setShiJiaoZongJinE(shiJiaoZongJinE);
		return calculateModel;
	}

	/**
	 * 获得未审年度的金额
	 * 
	 * @param year
	 * @param companyCode
	 * @param total
	 * @return
	 */
	private BigDecimal getUnAudits(String year, Integer companyId,
			BigDecimal total, List<AccountModel> sb) {
		BigDecimal amount = new BigDecimal(0.00);
		// 本地区上年度职工年人均工资数
		AuditParameter auditParameter = auditParameterService.getByYear(year);
		BigDecimal averageSalary = auditParameter.getAverageSalary();
		// 计算出应缴金额
		// 残疾人安排比例
		BigDecimal putScale = auditParameter.getPutScale();
		// 本地区上年度职工年人均工资数*(职工总人数*残疾人安排比例)未审按一个也没有安排计算所以不减 应安排
		BigDecimal payableAmount = averageSalary.multiply(total
				.multiply(putScale));
		String[] unAudits = companyService.getUnauditYearByCompany(companyId,
				year);
		if (unAudits != null) {
			for (String unYear : unAudits) {
				AuditParameter oldAuditParameter = auditParameterService
						.getByYear(unYear);
				Date auditDelayDate = oldAuditParameter.getAuditDelayDate();
				int days = CalendarUtil.getDaySub(auditDelayDate, new Date());
				// 滞纳金=应缴金额*滞纳金比例*滞纳金天数
				BigDecimal penalty = payableAmount.multiply(
						oldAuditParameter.getAuditDelayRate()).multiply(
						new BigDecimal(days));
				BigDecimal unYearTotal = payableAmount.add(penalty);
				logger.debug(
						"payableAmount:{},year:{} date:{} penalty:{} unYearTotal:{}",
						payableAmount, unYear, days, penalty, unYearTotal);
				AccountModel am = new AccountModel();
				am.setYear(unYear);
				am.setDays(String.valueOf(days));
				am.setMoney(df.format(payableAmount));
				am.setPenalty(df.format(penalty));
				am.setProp(df4.format(oldAuditParameter.getAuditDelayRate()));
				am.setTotal(df.format(unYearTotal));
				sb.add(am);
				amount = amount.add(unYearTotal);
			}
		}
		if (amount.compareTo(new BigDecimal(0.00)) != 0) {
			AccountModel am = new AccountModel();
			am.setTotal(df.format(amount));
			sb.add(am);
		}
		return amount;
	}

	/**
	 * 获得部分缴款状态金额
	 * 
	 * @param companyCode
	 * @return
	 */
	private BigDecimal getSectionPaid(String year, Integer companyId,
			List<AccountModel> sb) {
		BigDecimal amount = new BigDecimal(0.00);
		// List<Accounts> audits =
		// accountsService.getUnauditByCompany(companyId, year,
		// Constants.PROCESS_STATIC_BFJK);
		List<Accounts> accounts = accountsService.getByYearAndCompany(year,
				companyId, Constants.PROCESS_STATIC_BFJK);
		Map<String, Accounts> map = new HashMap<>();
		for (Accounts group : accounts) {
			Object obj = map.get(group.getYear());
			if (obj == null) {
				map.put(group.getYear(), group);
			} else {
				Accounts a = (Accounts) obj;
				a.setTotalMoney(a.getTotalMoney().add(group.getTotalMoney()));
			}
		}
		for (Accounts a : map.values()) {
			BigDecimal paymentTotal = paymentService.getEffPaid(null,
					a.getYear(), companyId);// 获得真实的已缴款记录
			BigDecimal qj = a.getTotalMoney().subtract(paymentTotal);
			AuditParameter auditParameter = auditParameterService.getByYear(a
					.getYear());
			Date auditDelayDate = auditParameter.getAuditDelayDate();
			int days = CalendarUtil.getDaySub(auditDelayDate, new Date());
			BigDecimal penalty = qj
					.multiply(auditParameter.getAuditDelayRate()).multiply(
							new BigDecimal(days));
			BigDecimal total = qj.add(penalty);
			AccountModel am = new AccountModel();
			am.setYear(a.getYear());
			am.setDays(String.valueOf(days));
			am.setMoney(df.format(qj));
			am.setPenalty(df.format(penalty));
			am.setProp(df4.format(auditParameter.getAuditDelayRate()));
			am.setTotal(df.format(total));
			sb.add(am);
			amount = amount.add(total);
		}
		if (amount.compareTo(new BigDecimal(0.00)) != 0) {
			AccountModel am = new AccountModel();
			am.setTotal(df.format(amount));
			sb.add(am);
		}
		return amount;
	}

	/**
	 * 未缴款
	 * 
	 * @param companyId
	 * @param sb
	 * @return
	 */
	private BigDecimal getUnpaid(String year, Integer companyId,
			List<AccountModel> sb) {
		BigDecimal amount = new BigDecimal(0.00);
		List<Accounts> accounts = accountsService.getByYearAndCompany(year,
				companyId, Constants.PROCESS_STATIC_WJK);
		Map<String, Accounts> map = new HashMap<>();
		for (Accounts group : accounts) {
			Object obj = map.get(group.getYear());
			if (obj == null) {
				map.put(group.getYear(), group);
			} else {
				Accounts a = (Accounts) obj;
				a.setTotalMoney(a.getTotalMoney().add(group.getTotalMoney()));
			}
		}
		for (Accounts a : map.values()) {
			BigDecimal total = a.getTotalMoney();
			AuditParameter auditParameter = auditParameterService.getByYear(a
					.getYear());
			Date auditDelayDate = auditParameter.getAuditDelayDate();
			int days = CalendarUtil.getDaySub(auditDelayDate, new Date());
			BigDecimal penalty = total.multiply(
					auditParameter.getAuditDelayRate()).multiply(
					new BigDecimal(days));
			AccountModel am = new AccountModel();
			am.setYear(a.getYear());
			am.setDays(String.valueOf(days));
			am.setMoney(df.format(total));
			am.setPenalty(df.format(penalty));
			am.setProp(df4.format(auditParameter.getAuditDelayRate()));
			am.setTotal(df.format(total));
			sb.add(am);
			amount = amount.add(total);
		}
		if (amount.compareTo(new BigDecimal(0.00)) != 0) {
			AccountModel am = new AccountModel();
			am.setTotal(df.format(amount));
			sb.add(am);
		}
		return amount;
	}

	/**
	 * 获取审计列表数据
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
		String money = request.getParameter("money");
		String companyCode = request.getParameter("companyCode");
		String companyTaxCode = request.getParameter("companyTaxCode");
		String companyName = request.getParameter("companyName");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", page);
		params.put("pageSize", pageSize);
		params.put("year", year); // 年度
		params.put("auditProcessStatus", process); // 审核状态
		params.put("companyCode", companyCode); // 公司档案号
		params.put("companyTaxCode", companyTaxCode); // 公司税务编码
		params.put("companyName", companyName); // 公司税务编码
		if (StringUtils.isNotBlank(money)) {
			params.put("actualAmount", new BigDecimal(money)); // 实缴金额
		}

		logger.debug("years:{},page:{},rows:{},process{}", year, page,
				pageSize, process);
		Map<String, Object> entity = new HashMap<>();
		try {
			PaginationRecordsAndNumber<Audit, Number> query = auditService
					.getByMultiCondition(params);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<Audit> iterator = query.getRecords().iterator(); iterator
					.hasNext();) {
				Audit it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("year", it.getYear()); // 审核年度
				map.put("companyCode", it.getCompany().getCompanyCode());// 企业档案编号
				map.put("companyTaxCode", it.getCompany().getCompanyTaxCode());// 税务编号
				map.put("companyId", it.getCompany().getId());// 企业名称
				map.put("companyName", it.getCompany().getCompanyName());// 企业名称
				Integer pId = it.getAuditProcessStatus().getId();
				map.put("auditProcessStatusId", pId);// 流程状态
				String statusName = it.getAuditProcessStatus()
						.getAuditProcessStatus();
				if (it.getRefuseTimes() > 0) {
					statusName = statusName + "(" + it.getRefuseTimes() + ")";
				}
				map.put("auditProcessStatus", statusName);// 流程状态

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
	public ModelAndView initAudit(@PathVariable(value = "id") int id,
			@PathVariable(value = "process") int process,
			HttpServletRequest request, HttpSession session) {
		Audit audit = auditService.getByPrimaryKey(id);
		if (audit.getInitAuditUser() == null) {
			Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
			User user = userService.getByPrimaryKey(userId);
			audit.setInitAuditUser(user);
		}
		if (audit.getVerifyAuditUser() == null && process == 2) {
			Integer userId = (Integer) session.getAttribute(Constants.USER_ID);
			User user = userService.getByPrimaryKey(userId);
			audit.setVerifyAuditUser(user);
		}
		// 如果为重审回来的数据, 则总缴款额需要-该审核年度以前已经缴过的款
		if (process == 2) {
			BigDecimal alreadyPayment = paymentService.getEffPaid(
					audit.getYear(), null, audit.getCompany().getId());
			audit.setPayAmount(audit.getPayAmount().subtract(alreadyPayment));
		}
		String year = audit.getYear();
		AuditParameter auditParameter = auditParameterService.getByYear(year);
		String areaName = auditParameter.getArea().getName();
		request.setAttribute("areaName", areaName);
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

		Integer companyId = audit.getCompany().getId();
		// 年龄超标
		PaginationRecordsAndNumber<Worker, Number> workers = companyService
				.getOverproofAge(year, companyId, 1, Integer.MAX_VALUE);
		request.setAttribute("ageEx", workers.getNumber());
		// 未审年度
		String[] unAudits = companyService.getUnauditYearByCompany(companyId,
				year);
		StringBuilder sb = new StringBuilder();
		for (String s : unAudits) {
			sb.append(s).append(",");
		}
		request.setAttribute("unAudityear", sb.toString());
		request.setAttribute("unAudityearNum", unAudits.length);
		// 拒绝条件选择
		List<Reply> replys = replyService.getAll();
		request.setAttribute("reasons", replys);

		return new ModelAndView("audit/audit_detail", "entity", audit);
	}

}
