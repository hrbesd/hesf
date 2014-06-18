package com.esd.cs;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Accounts;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyType;
import com.esd.hesf.model.Menu;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.PaymentExceptional;
import com.esd.hesf.model.PaymentType;
import com.esd.hesf.model.Reply;
import com.esd.hesf.model.User;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;
import com.esd.hesf.model.WorkerTemp;
import com.esd.hesf.service.AccountsService;
import com.esd.hesf.service.AreaService;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.CompanyTypeService;
import com.esd.hesf.service.MenuService;
import com.esd.hesf.service.PaymentExceptionalService;
import com.esd.hesf.service.PaymentService;
import com.esd.hesf.service.PaymentTypeService;
import com.esd.hesf.service.ReplyService;
import com.esd.hesf.service.ReportViewService;
import com.esd.hesf.service.UserService;
import com.esd.hesf.service.WorkerHandicapLevelService;
import com.esd.hesf.service.WorkerHandicapTypeService;
import com.esd.hesf.service.WorkerService;
import com.esd.hesf.service.WorkerTempService;
import com.esd.hesf.viewmodels.ReportViewModel;
import com.esd.hesf.viewmodels.WorkerViewModel;

/**
 * 
 * 主页处理
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/test")
public class TestController {
	// private static final Logger logger =
	// LoggerFactory.getLogger(TestController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	// @Autowired
	// private UserGroupService groupService;

	@Autowired
	private CompanyService cService;

	@Autowired
	private AreaService aService;

	@Autowired
	private WorkerService wService;

	@Autowired
	private AuditProcessStatusService apsService;

	@Autowired
	private WorkerHandicapLevelService hlService;

	@Autowired
	private WorkerHandicapTypeService htService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private AuditParameterService apService;

	@Autowired
	private PaymentService pService;

	@Autowired
	private PaymentTypeService ptService;

	@Autowired
	private ReportViewService rvmService;

	@Autowired
	private PaymentExceptionalService peService;

	@Autowired
	private ReplyService rService;

	@Autowired
	private CompanyTypeService ctService;

	@Autowired
	private CompanyEconomyTypeService cetService;

	@Autowired
	private AccountsService acService;

	@Autowired
	private WorkerTempService wtService;

	// 菜单
	@RequestMapping("/1")
	@ResponseBody
	public Map<String, Object> test1() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Menu> entity = menuService.getTreeMap();
		map.put("entity", entity);
		return map;
	}

	// 公司--得到一个
	@RequestMapping("/2")
	@ResponseBody
	public Map<String, Object> test2() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = cService.getByPrimaryKey(1);
		map.put("entity", entity);
		return map;
	}

	// 账号 -- 得到一个
	@RequestMapping("/3")
	@ResponseBody
	public Map<String, Object> test3() {
		Map<String, Object> map = new HashMap<String, Object>();
		User entity = userService.getByPrimaryKey(1);
		map.put("entity", entity);
		return map;
	}

	// 企业, 得到年份
	@RequestMapping("/4")
	@ResponseBody
	public Map<String, Object> test4() {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] entity = apService.getYears();
		String year = apService.getLastestYear();
		map.put("entity", entity);
		map.put("year", year);
		return map;
	}

	// 地区
	@RequestMapping("/5")
	@ResponseBody
	public Map<String, Object> test5() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Area> entity = aService.getSubArea("10230000");
		map.put("entity", entity);
		return map;
	}

	// 账号 根据真实姓名查
	@RequestMapping("/7")
	@ResponseBody
	public Map<String, Object> test7() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> entity = userService.getUserByRealName("张建宗");
		map.put("entity", entity);
		return map;
	}

	// 审核状态进程
	@RequestMapping("/9")
	@ResponseBody
	public Map<String, Object> test9() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AuditProcessStatus> entity = apsService.getAll();
		map.put("entity", entity);
		return map;
	}
	
	// 审核状态进程
		@RequestMapping(value="/post9",method=RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> test9post(HttpServletRequest req) {
			String name = req.getParameter("name");
			String password = req.getParameter("password");
			Map<String, Object> map = new HashMap<String, Object>();
		//	List<AuditProcessStatus> entity = apsService.getAll();
			String entity = "name: "+name+", password: "+password;
			map.put("entity", entity);
			return map;
		}

	// 企业 按组织机构代码查
	@RequestMapping("/10")
	@ResponseBody
	public Map<String, Object> test10() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = cService.getByCompanyOrganizationCode("123");
		map.put("entity", entity);
		return map;
	}

	// 公司--获得所有
	@RequestMapping("/11")
	@ResponseBody
	public Map<String, Object> test11() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> entity = cService.getAll(10);
		map.put("entity", entity);
		return map;
	}

	// 企业--分页
	@RequestMapping("/6")
	@ResponseBody
	public Map<String, Object> test6() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company company = new Company();
		// / company.setYear("2001");
		// company.setCompanyCode("00002");
		// company.setCompanyName("f");
		// company.setCompanyEconomyType(new CompanyEconomyType(1));
		PaginationRecordsAndNumber<Company, Number> entity = cService
				.getPaginationRecords(company, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 职工 获得所有
	@RequestMapping("/12")
	@ResponseBody
	public Map<String, Object> test12() {
		Map<String, Object> map = new HashMap<String, Object>();
		Worker entity = wService.getByPrimaryKey(18496);
		map.put("entity", entity);
		return map;
	}

	// 职工 分页
	@RequestMapping("/13")
	@ResponseBody
	public Map<String, Object> test13() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("year", "2011");
		paramMap.put("companyId", 1);
		paramMap.put("getOverproof", true);
		// paramMap.put("workerHandicapCode", "66"); // 残疾证号
		// paramMap.put("careerCard", "67"); // 就业证号
		// paramMap.put("workerName", "67"); // 姓名
		// paramMap.put("workerGender", "1"); // 性别
		// paramMap.put("minAge", 26); // 最小年龄
		// paramMap.put("maxAge", 30); // 最大年龄
		// paramMap.put("workerHandicapType", 1); // 残疾类别 对应的id
		// paramMap.put("workerHandicapLevel", 1); // 残疾等级 对应的id
		// paramMap.put("page", 1); // 分页--起始页 ******************************
		// paramMap.put("pageSize", 50);// 分页--返回量
		PaginationRecordsAndNumber<Worker, Number> entity = wService
				.getPaginationRecords(paramMap);
		map.put("entity", entity);
		return map;
	}

	// 残疾等级
	@RequestMapping("/14")
	@ResponseBody
	public Map<String, Object> test14() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkerHandicapLevel> entity = hlService.getAll();
		map.put("entity", entity);
		return map;
	}

	// 残疾类型
	@RequestMapping("/15")
	@ResponseBody
	public Map<String, Object> test15() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkerHandicapType> entity = htService.getAll();
		map.put("entity", entity);
		return map;
	}

	// 审核表 所有
	@RequestMapping("/16")
	@ResponseBody
	public Map<String, Object> test16() {
		Map<String, Object> map = new HashMap<String, Object>();
		Audit au = new Audit();
		// au.setYear("2014");
		// au.setCompany(new Company("1"));
		au.setIsExempt(true);
		PaginationRecordsAndNumber<Audit, Number> entity = auditService
				.getPaginationRecords(au, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 职工 得到一个
	@RequestMapping("/17")
	@ResponseBody
	public Map<String, Object> test17() {
		Map<String, Object> map = new HashMap<String, Object>();
		Worker entity = wService.getByPrimaryKey(26884);
		entity.setWorkerAddress("红旗大街");
		boolean bl = wService.update(entity);
		map.put("entity", bl);
		return map;
	}

	// 公司--复制上一年的信息
	// @RequestMapping("/18")
	// @ResponseBody
	// public Map<String, Object> test18() {
	// Map<String, Object> map = new HashMap<String, Object>();
	// boolean entity = cService("2011", "2010");
	// map.put("entity", entity);
	// return map;
	// }

	// 公司--删除一个员工信息
	@RequestMapping("/19")
	@ResponseBody
	public Map<String, Object> test19() {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean entity = cService.deleteWorkerFromCompany("2013", 1366, 26884);
		map.put("entity", entity);
		return map;
	}

	// 审核参数--所有数据
	@RequestMapping("/20")
	@ResponseBody
	public Map<String, Object> test20() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<AuditParameter, Number> entity = apService
				.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 时间对象 格式化测试
	@RequestMapping("/21")
	public ModelAndView test21() {
		ModelAndView mav = new ModelAndView("/test");
		// Map<String, Object> map = new HashMap<String, Object>();
		AuditParameter entity = apService.getByPrimaryKey(24);
		mav.addObject("entity", entity);
		// map.put("entity", entity);
		return mav;
	}

	// 支付表 --所有数据
	@RequestMapping("/22")
	@ResponseBody
	public Map<String, Object> test22() {
		Map<String, Object> map = new HashMap<String, Object>();
		Payment p = new Payment();
		// Audit audit = new Audit();
		// audit.setId(109066);
		// audit.setYear("2005");
		// p.setAudit(audit);
		// User user = new User();
		// user.setId(1);
		// p.setPaymentPerson(user);
		Company company = new Company();
		// company.setId(10);
		// company.setCompanyCode("10");
		// company.setCompanyName("杂志");
		// company.setCompanyOrganizationCode("1");
		// company.setCompanyZipCode("150001");
		// company.setCompanyType(new CompanyType(1));
		// company.setCompanyEconomyType(new CompanyEconomyType(1));
		// company.setCompanyProperty(new CompanyProperty(3));
		// company.setArea(new Area("10230090"));
		p.setPaymentCompany(company);
		// p.setPaymentBill("u");
		// PaymentType pt = new PaymentType();
		// pt.setId(2);
		// p.setPaymentType(pt);
		// p.setBillReturn(null);
		// p.setBillFinance(false);
		// p.setBillObsolete(false);
		// PaymentExceptional pe = new PaymentExceptional();
		// pe.setId(1);
		// p.setPaymentExceptional(pe);
		PaginationRecordsAndNumber<Payment, Number> entity = pService
				.getPaginationRecords(p, 1, 1);
		map.put("entity", entity);
		return map;
	}

	// 审核表视图 根据公司查
	@RequestMapping("/23")
	@ResponseBody
	public Map<String, Object> test23() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("year", "2012");
		PaginationRecordsAndNumber<Audit, Number> entity = auditService
				.getByMultiCondition(map1);
		map.put("entity", entity);
		return map;
	}

	// 账号 -- 得到所有缴款人
	@RequestMapping("/24")
	@ResponseBody
	public Map<String, Object> test24() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> entity = userService.getUserByGroup(Constants.USER_GROUP_JIAOKUAN);
		map.put("entity", entity);
		return map;
	}

	// 审核表 初始化审核参数
	@RequestMapping("/25")
	@ResponseBody
	public Map<String, Object> test25() {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean entity = auditService.initAuditData("2013");
		map.put("entity", entity);
		return map;
	}

	// 企业--多条件查询
	@RequestMapping("/26")
	@ResponseBody
	public Map<String, Object> test26() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("minTotal", 60); // 查询范围中 公司最少人数
		// paramMap.put("maxTotal", 123); // 查询范围中 公司最多人数
		paramMap.put("minHandicapTotal", 1); // 查询范围中 残疾职工最少人数
		paramMap.put("maxHandicapTotal", 10); // 查询范围中 残疾职工最多人数
		// paramMap.put("year", "2013");
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Company, Number> entity = cService
				.getByMultiCondition(paramMap);
		map.put("entity", entity);
		return map;
	}

	// 员工--多条件查询
	@RequestMapping("/27")
	@ResponseBody
	public Map<String, Object> test27() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("year", "2014");
		// paramMap.put("workerHandicapCode", "66"); // 残疾证号
		// paramMap.put("careerCard", "67"); // 就业证号
		// paramMap.put("workerName", "67"); // 姓名
		// paramMap.put("workerGender", "1"); // 性别
		// paramMap.put("currentJob", "67"); // 当前岗位
		// paramMap.put("minAge", 10); // 最大年龄
		// paramMap.put("maxAge", 25); // 最小年龄
		// paramMap.put("workerHandicapType", 1); // 残疾类别 对应的id
		// paramMap.put("workerHandicapLevel", 1); // 残疾等级 对应的id
		// paramMap.put("page", 1); // 分页--起始页 ******************************
		// paramMap.put("pageSize", 50);// 分页--返回量
		// ******************************
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<WorkerViewModel, Number> entity = wService
				.getByMultiCondition(paramMap);
		map.put("entity", entity);
		return map;
	}

	// 公司--按year和companyCode获得
	@RequestMapping("/28")
	@ResponseBody
	public Map<String, Object> test28() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = cService.getByCompanyCode("101007");
		map.put("entity", entity);
		return map;
	}

	// 审核表 所有
	@RequestMapping("/29")
	@ResponseBody
	public Map<String, Object> test29() {
		Map<String, Object> map = new HashMap<String, Object>();
		Audit entity = auditService.getByPrimaryKey(47634);
		map.put("entity",
				entity.getCompanyHandicapTotal() + "   " + entity.getYear());
		return map;
	}

	// 地区 获得黑龙江
	@RequestMapping("/30")
	@ResponseBody
	public Map<String, Object> test30() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Area> entity = aService.getHeiLongjiangCities();
		map.put("entity", entity);
		return map;
	}

	// 审核表 取一条
	@RequestMapping("/31")
	@ResponseBody
	public Map<String, Object> test31() {
		Map<String, Object> map = new HashMap<String, Object>();
		Audit entity = auditService.getByPrimaryKey(47634);
		map.put("entity", entity);
		return map;
	}

	// 公司 --中间--员工表 根据年, 身份证号查公司
	@RequestMapping("/32")
	@ResponseBody
	public Map<String, Object> test32() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = wService.retrieveCompanyByWorker("2013",
				"232623196309160029");
		map.put("entity", entity);
		return map;
	}

	// 员工表 身份证号查员工是否存在
	@RequestMapping("/33")
	@ResponseBody
	public Map<String, Object> test33() {
		Map<String, Object> map = new HashMap<String, Object>();
		Worker entity = wService
				.getByWorkerHandicapCode("23262319630916002943");
		map.put("entity", entity + " ********* ");
		return map;
	}

	// 审核参数表 查询 比例不为1的列
	@RequestMapping("/34")
	@ResponseBody
	public Map<String, Object> test34() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkerCalculator> entity = apService.getSpecialSetting("2013");
		map.put("entity", entity);
		return map;
	}

	// 审核参数表 查询 比例不为1的人数 在某公司中有几人
	@RequestMapping("/35")
	@ResponseBody
	public Map<String, Object> test35() {
		Map<String, Object> map = new HashMap<String, Object>();
		int entity = apService.getSpecialCount(1366, "2013", 1, 1);
		map.put("entity", entity);
		return map;
	}

	// 缴款表 查询已付款金额
	@RequestMapping("/36")
	@ResponseBody
	public Map<String, Object> test36() {
		Map<String, Object> map = new HashMap<String, Object>();
		// BigDecimal entity = pService.getAlreadyPayByAudit(47634);
		// // BigDecimal entity = pService.getAlreadyPayByCompany(11);
		// map.put("entity", entity);
		return map;
	}

	// 职工 更改公司
	@RequestMapping("/37")
	@ResponseBody
	public Map<String, Object> test37() {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean entity = wService.changeCompany(18496, 1370, "2013", "城管");
		map.put("entity", entity);
		return map;
	}

	// 付款表 根据审核id, 得到缴款列表
	@RequestMapping("/38")
	@ResponseBody
	public Map<String, Object> test38() {
		Map<String, Object> map = new HashMap<String, Object>();
		// PaginationRecordsAndNumber<Payment, Number> entity =
		// pService.getPaymentRecordByAudit(47634, 1, 999);
		// map.put("entity", entity);
		return map;
	}

	// 付款表 得到缴款列表
	@RequestMapping("/39")
	@ResponseBody
	public Map<String, Object> test39() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService
				.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 缴款类型表 获得所有
	@RequestMapping("/40")
	@ResponseBody
	public Map<String, Object> test40() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<PaymentType, Number> entity = ptService
				.getPaginationRecords(null, 1, 20);
		List<PaymentType> list = ptService.getAll();
		map.put("lit", list);
		map.put("entity", entity);
		return map;
	}

	// 员工--公司--关系表 查询一个公司某年的达到退休年龄的职工
	@RequestMapping("/41")
	@ResponseBody
	public Map<String, Object> test41() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Worker, Number> entity = cService
				.getOverproofAge("2013", 1366, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 审核表 获得一个公司过去未审核的年份列表
	@RequestMapping("/42")
	@ResponseBody
	public Map<String, Object> test42() {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] entity = cService.getUnauditYearByCompany(1366, "2013");
		map.put("entity", entity);
		return map;
	}

	// 审核表 根据年份, 和公司code获得一个审核数据
	@RequestMapping("/43")
	@ResponseBody
	public Map<String, Object> test43() {
		Map<String, Object> map = new HashMap<String, Object>();
		Audit entity = auditService.getByPrimaryKey("2013", 1);
		map.put("entity", entity);
		return map;
	}

	// 公司表 更新数据
	@RequestMapping("/44")
	@ResponseBody
	public Map<String, Object> test44() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company c = cService.getByPrimaryKey(1366);
		c.setCompanyContactPerson("虎头人");
		boolean entity = cService.update(c);
		map.put("entity", entity);
		return map;
	}

	// 统计报表--按单位性质
	@RequestMapping("/45")
	@ResponseBody
	public Map<String, Object> test45() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportViewModel> entity = rvmService.getByCompanyType("2011");
		map.put("entity", entity);
		return map;
	}

	// 统计报表--按经济类型
	@RequestMapping("/46")
	@ResponseBody
	public Map<String, Object> test46() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportViewModel> entity = rvmService
				.getByCompanyEconomyType("2011");
		map.put("entity", entity);
		return map;
	}

	// 统计报表--按地区
	@RequestMapping("/47")
	@ResponseBody
	public Map<String, Object> test47() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportViewModel> entity = rvmService.getByArea("2011");
		map.put("entity", entity);
		return map;
	}

	// 公司表--根据档案号查
	@RequestMapping("/49")
	@ResponseBody
	public Map<String, Object> test49() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = cService.getByCompanyCode("101001");
		map.put("entity", entity);
		return map;
	}

	// 公司档案号自增表--获得新档案号
	@RequestMapping("/51")
	@ResponseBody
	public Map<String, Object> test51() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("paymentPerson", 1);
		condition.put("year", "2013");
		PaginationRecordsAndNumber<Audit, Number> entity = auditService
				.getByMultiCondition(condition);
		map.put("entity", entity);
		return map;
	}

	// 特殊缴款方式类别--测试
	@RequestMapping("/52")
	@ResponseBody
	public Map<String, Object> test52() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<PaymentExceptional, Number> entity = peService
				.getPaginationRecords(null, 1, 10);
		map.put("entity", entity);
		return map;
	}

	// 特殊缴款方式类别--测试
	@RequestMapping("/53")
	@ResponseBody
	public Map<String, Object> test53() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PaymentExceptional> entity = peService.getAll();
		map.put("entity", entity);
		return map;
	}

	// 缴款表--更新新
	@RequestMapping("/54")
	@ResponseBody
	public Map<String, Object> test54() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService
				.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 员工-年度-企业 表 根据公司id, 年 查询人数
	@RequestMapping("/55")
	@ResponseBody
	public Map<String, Object> test55() {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer entity = cService.getWorkerHandicapTotal(1794, "2013");
		map.put("entity", entity);
		return map;
	}

	// 审核表 根据传入的年份和公司code 穿件一条审核记录
	@RequestMapping("/56")
	@ResponseBody
	public Map<String, Object> test56() {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean entity = auditService.save("2000", "101001");
		map.put("entity", entity);
		return map;
	}

	// 公司类型
	@RequestMapping("/57")
	@ResponseBody
	public Map<String, Object> test57() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompanyType> entity = ctService.getAll();
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/58")
	@ResponseBody
	public Map<String, Object> test58() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Reply> entity = rService.getAll();
		map.put("entity", entity);
		return map;
	}

	@RequestMapping(value = "/59")
	public String test59(HttpServletRequest request) {
		boolean b = true;
		// 每次导入量
		Integer pageSize = 99999;
		PaginationRecordsAndNumber<Payment, Number> prn = pService
				.getPaginationRecords(null, 1, pageSize);
		// 总条数
		int totalCount = (Integer) prn.getNumber();
		// 总页数
		int totalPages = totalCount % pageSize == 0 ? (totalCount / pageSize)
				: (totalCount / pageSize + 1);
		String url = request.getServletContext().getRealPath("/");

		// 创建导出文件夹
		File downPath = new File(url + "download");
		// 导出文件夹
		String exportFolder = downPath + File.separator + "payment";
		File paymentPath = new File(exportFolder);
		if (!(downPath.exists())) {
			downPath.mkdir();
		}
		if (!(paymentPath.exists())) {
			paymentPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		System.out.println("exportPath " + exportPath);
		File file = new File(exportPath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 循环向excel中导入文件
		int i = 0;
		do {
			i++;
			PaginationRecordsAndNumber<Payment, Number> eachPrn = pService
					.getPaginationRecords(null, i, pageSize);
			// 每一页数据循环导入其中
			List<Payment> plist = (List<Payment>) eachPrn.getRecords();
			b = pService.createPaymentExcel(exportPath, plist, i, pageSize);
			// 清空plist数据
			// plist = null;

		} while (i < totalPages);
		String FileDownloadPath = "";
		if (b) {
			String destPath = request.getLocalAddr() + ":"
					+ request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/download/payment/"
					+ uuid + ".xls";
		}
		return "redirect:" + FileDownloadPath;
	}

	@RequestMapping("/60")
	@ResponseBody
	public Map<String, Object> test60() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService
				.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/61")
	@ResponseBody
	public Map<String, Object> test61() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Accounts, Number> entity = acService
				.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// @RequestMapping("/62")
	// @ResponseBody
	// public Map<String, Object> test62() {
	// Map<String, Object> map = new HashMap<String, Object>();
	// String[] entity = acService.getAccountsYears();
	// map.put("entity", entity);
	// return map;
	// }
	//
	// @RequestMapping("/63")
	// @ResponseBody
	// public Map<String, Object> test63() {
	// Map<String, Object> map = new HashMap<String, Object>();
	// String[] entity = acService.getAccountsYears();
	// map.put("entity", entity);
	// return map;
	// }

	@RequestMapping("/64")
	@ResponseBody
	public Map<String, Object> test64() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Accounts> entity = acService.getCompanyAccount(null, "");
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/65")
	@ResponseBody
	public Map<String, Object> test65() {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal entity = pService.getEffPaid(null, null, "");
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/66")
	@ResponseBody
	public Map<String, Object> test66() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService
				.getPaymentRecords(null, 4, 1, 50);
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/67")
	@ResponseBody
	public Map<String, Object> test67() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Audit> entity = cService.getUnauditByCompany(33, "2011", 1);
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/68")
	@ResponseBody
	public Map<String, Object> test68() {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] entity = auditService.getAuditYears();
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/69")
	@ResponseBody
	public Map<String, Object> test69() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Accounts> entity = acService.getByYearAndCompany("2012", 22, 3);
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/70")
	@ResponseBody
	public Map<String, Object> test70() {
		Map<String, Object> map = new HashMap<String, Object>();
		Accounts accounts = new Accounts();
		accounts.setAuditProcessStatus(new AuditProcessStatus(3));
		PaginationRecordsAndNumber<Accounts, Number> entity = acService
				.getPaginationRecordsGroupByCompany(accounts,null, 1, 100);
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/71")
	@ResponseBody
	public Map<String, Object> test71() {
		Map<String, Object> map = new HashMap<String, Object>();
		Accounts entity = acService.getByYearAndCompany("2014", 2);
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/72")
	@ResponseBody
	public Map<String, Object> test72() {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] entity = acService.getAccountsYears();
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/73")
	@ResponseBody
	public Map<String, Object> test73() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportViewModel> entity = rvmService.getByCompanyType("2013");
		map.put("entity", entity);
		return map;
	}

	@RequestMapping("/75")
	@ResponseBody
	public Map<String, Object> test75() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompanyEconomyType> list = cetService.getAll();
		map.put("entity", list);
		return map;
	}

	// @RequestMapping("/74")
	// @ResponseBody
	// public Map<String, Object> test74() {
	// Map<String, Object> map = new HashMap<String, Object>();
	// Boolean entity = aService.updatedata();
	// map.put("entity", entity);
	// return map;
	// }

	@RequestMapping("/76")
	@ResponseBody
	public Map<String, Object> test76() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkerTemp> list = wtService.getByCheck(false, null);
		map.put("entity", list);
		return map;
	}

	@RequestMapping("/77")
	@ResponseBody
	public Map<String, Object> test77() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CompanyEconomyType> list = cetService.getAll();
		map.put("entity", list);
		return map;
	}

	@RequestMapping("/78")
	@ResponseBody
	public Map<String, Object> test78() {
		Map<String, Object> map = new HashMap<String, Object>();
		// List<CompanyEconomyType> list = cetService.getAll();
		for (int i = 0; i < 100000; i++) {
			String workerHandicapCode = "skdjflsd";
			Worker w = wService.getByWorkerHandicapCode(workerHandicapCode);
			System.out.println("*------ " + (i + 1) + " ------*");
			System.out.println(w);
		}
		map.put("entity", 123);
		return map;
	}

	@RequestMapping("/79")
	@ResponseBody
	public Map<String, Object> test79() {
		Map<String, Object> map = new HashMap<String, Object>();
		// List<CompanyEconomyType> list = cetService.getAll();
		for (int i = 0; i < 100000; i++) {
			System.out.println("*------ " + (i + 1) + " ------*");
			Area a = new Area();
			a.setCode("q" + i);
			a.setName("test地方");
			boolean w = aService.save(a);
			System.out.println(w);
		}
		map.put("entity", 123);
		return map;
	}
	
	//常量
	private static Integer c = 0;
	@RequestMapping("/80")
	@ResponseBody
	public Map<String, Object> test80() {
		Map<String,Object> map = new HashMap<String,Object>();
		c++;
		map.put("entity", c);
		return map;
	}
	
	//常量
		private static Integer d = 0;
	@RequestMapping("/81")
	@ResponseBody
	public Map<String, Object> test81() {
		Map<String, Object> map = new HashMap<String, Object>();
		Reply c = new Reply();
		c.setContent("测试");
		c.setTitle("test");
		boolean bl = rService.save(c);
		if(bl){
			d++;
			map.put("entity", bl);
			map.put("dbCount", d);
		}else{
			map.put("entity", bl);
			map.put("dbCount", d);
		}
		return map;
	}

	public static void main(String[] args) throws ClassNotFoundException {

		Connection conn = null;
		PreparedStatement ps = null;
		// 首先要获取连接，即连接到数据库
		for (int i = 0; i < 100000; i++) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(
						"jdbc:mysql://192.168.170.85:3306/hesf", "root",
						"esd123");
				String sql = "INSERT INTO area(code,name)"
						+ " VALUES ('q"+i+"', 'text')"; // 插入数据的sql语句
				ps = conn.prepareStatement(sql);
				int count = ps.executeUpdate(sql); // 执行插入操作的sql语句，并返回插入数据的个数
				System.out.println("向staff表中插入 " + count + " 条数据: "+(i+1)); // 输出插入操作的处理结果
			} catch (SQLException e) {
				System.out.println("插入数据失败" + e.getMessage());
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
