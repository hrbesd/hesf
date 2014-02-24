package com.esd.cs;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Menu;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.PaymentType;
import com.esd.hesf.model.User;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;
import com.esd.hesf.service.AreaService;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.MenuService;
import com.esd.hesf.service.PaymentService;
import com.esd.hesf.service.PaymentTypeService;
import com.esd.hesf.service.ReportViewService;
import com.esd.hesf.service.UserGroupService;
import com.esd.hesf.service.UserService;
import com.esd.hesf.service.WorkerHandicapLevelService;
import com.esd.hesf.service.WorkerHandicapTypeService;
import com.esd.hesf.service.WorkerService;
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
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private MenuService menuService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService groupService;

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
		String[] entity = cService.getYears();
		map.put("entity", entity);
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

	// 企业 按组织机构代码查
	@RequestMapping("/10")
	@ResponseBody
	public Map<String, Object> test10() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = cService.getCompanyByOrganizationCode("22222221");
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
		PaginationRecordsAndNumber<Company, Number> entity = cService.getPaginationRecords(company, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 职工 获得所有
	@RequestMapping("/12")
	@ResponseBody
	public Map<String, Object> test12() {
		Map<String, Object> map = new HashMap<String, Object>();
		Worker entity = wService.getByPrimaryKey(1);
		map.put("entity", entity);
		return map;
	}

	// 职工 分页
	@RequestMapping("/13")
	@ResponseBody
	public Map<String, Object> test13() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("year", "2014");
		// paramMap.put("companyCode", "1390293306601");
		// paramMap.put("workerHandicapCode", "66"); // 残疾证号
		// paramMap.put("careerCard", "67"); // 就业证号
		// paramMap.put("workerName", "67"); // 姓名
		// paramMap.put("workerGender", "1"); // 性别
		paramMap.put("minAge", 26); // 最小年龄
		paramMap.put("maxAge", 30); // 最大年龄
		// paramMap.put("workerHandicapType", 1); // 残疾类别 对应的id
		// paramMap.put("workerHandicapLevel", 1); // 残疾等级 对应的id
		// paramMap.put("page", 1); // 分页--起始页 ******************************
		// paramMap.put("pageSize", 50);// 分页--返回量
		PaginationRecordsAndNumber<Worker, Number> entity = wService.getPaginationRecords(paramMap);
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
		PaginationRecordsAndNumber<Audit, Number> entity = auditService.getPaginationRecords(au, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 职工 得到一个
	@RequestMapping("/17")
	@ResponseBody
	public Map<String, Object> test17() {
		Map<String, Object> map = new HashMap<String, Object>();
		Worker entity = wService.getByPrimaryKey(1);
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
		boolean entity = cService.deleteWorkerFromCompany("2014", "21", 20);
		map.put("entity", entity);
		return map;
	}

	// 审核参数--所有数据
	@RequestMapping("/20")
	@ResponseBody
	public Map<String, Object> test20() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<AuditParameter, Number> entity = apService.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 时间对象 格式化测试
	@RequestMapping("/21")
	public ModelAndView test21() {
		ModelAndView mav = new ModelAndView("/test");
		// Map<String, Object> map = new HashMap<String, Object>();
		AuditParameter entity = apService.getByPrimaryKey(7);
		mav.addObject("entity", entity);
		// map.put("entity", entity);
		return mav;
	}

	// 支付表 --所有数据
	@RequestMapping("/22")
	@ResponseBody
	public Map<String, Object> test22() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 审核表视图 根据公司查
	@RequestMapping("/23")
	@ResponseBody
	public Map<String, Object> test23() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("year", "2001");
		PaginationRecordsAndNumber<Audit, Number> entity = auditService.getByMultiCondition(map1);
		map.put("entity", entity);
		return map;
	}

	// 账号 -- 得到所有缴款人
	@RequestMapping("/24")
	@ResponseBody
	public Map<String, Object> test24() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> entity = userService.getPayer();
		map.put("entity", entity);
		return map;
	}

	// 审核表 初始化审核参数
	@RequestMapping("/25")
	@ResponseBody
	public Map<String, Object> test25() {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean entity = auditService.initAuditData("2014", "2013");
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
		PaginationRecordsAndNumber<Company, Number> entity = cService.getByMultiCondition(paramMap);
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
		paramMap.put("minAge", 10); // 最大年龄
		paramMap.put("maxAge", 25); // 最小年龄
		// paramMap.put("workerHandicapType", 1); // 残疾类别 对应的id
		// paramMap.put("workerHandicapLevel", 1); // 残疾等级 对应的id
		// paramMap.put("page", 1); // 分页--起始页 ******************************
		// paramMap.put("pageSize", 50);// 分页--返回量
		// ******************************
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<WorkerViewModel, Number> entity = wService.getByMultiCondition(paramMap);
		map.put("entity", entity);
		return map;
	}

	// 公司--按year和companyCode获得
	@RequestMapping("/28")
	@ResponseBody
	public Map<String, Object> test28() {
		System.out.println(123123);
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = cService.getByYearAndCode("2014", "1390373258624");
		map.put("entity", entity);
		return map;
	}

	// 审核表 所有
	@RequestMapping("/29")
	@ResponseBody
	public Map<String, Object> test29() {
		Map<String, Object> map = new HashMap<String, Object>();
		Audit entity = auditService.getByPrimaryKey(136);
		map.put("entity", entity.getCompany().getCompanyHandicapTotal() + "   " + entity.getYear());
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
		Audit entity = auditService.getByPrimaryKey(137);
		map.put("entity", entity);
		return map;
	}

	// 公司 --中间--员工表 根据年, 身份证号查公司
	@RequestMapping("/32")
	@ResponseBody
	public Map<String, Object> test32() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company entity = wService.retrieveCompanyByWorker("2014", "222301198805294637");
		map.put("entity", entity);
		return map;
	}

	// 员工表 身份证号查员工是否存在
	@RequestMapping("/33")
	@ResponseBody
	public Map<String, Object> test33() {
		Map<String, Object> map = new HashMap<String, Object>();
		Worker entity = wService.getByWorkerHandicapCode("23230119850529463720");
		map.put("entity", entity + " ********* ");
		return map;
	}

	// 审核参数表 查询 比例不为1的列
	@RequestMapping("/34")
	@ResponseBody
	public Map<String, Object> test34() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkerCalculator> entity = apService.getSpecialSetting("2014");
		map.put("entity", entity);
		return map;
	}

	// 审核参数表 查询 比例不为1的人数 在某公司中有几人
	@RequestMapping("/35")
	@ResponseBody
	public Map<String, Object> test35() {
		Map<String, Object> map = new HashMap<String, Object>();
		int entity = apService.getSpecialCount("1390293306601", "2014", 1, 1);
		map.put("entity", entity);
		return map;
	}

	// 缴款表 查询已付款金额
	@RequestMapping("/36")
	@ResponseBody
	public Map<String, Object> test36() {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal entity = pService.getAlreadyPay("2014", "1390293713906");
		map.put("entity", entity);
		return map;
	}

	// 职工 更改公司
	@RequestMapping("/37")
	@ResponseBody
	public Map<String, Object> test37() {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean entity = wService.changeCompany(16, "1390293306601", "2014", "城管");
		map.put("entity", entity);
		return map;
	}

	// 付款表 根据审核id, 得到缴款列表
	@RequestMapping("/38")
	@ResponseBody
	public Map<String, Object> test38() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService.getPaymentRecord("0e79797525f95737da607bfc2079a010", 1, 30);
		map.put("entity", entity);
		return map;
	}

	// 付款表 得到缴款列表
	@RequestMapping("/39")
	@ResponseBody
	public Map<String, Object> test39() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<Payment, Number> entity = pService.getPaginationRecords(null, 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 缴款类型表 获得所有
	@RequestMapping("/40")
	@ResponseBody
	public Map<String, Object> test40() {
		Map<String, Object> map = new HashMap<String, Object>();
		PaginationRecordsAndNumber<PaymentType, Number> entity = ptService.getPaginationRecords(null, 1, 20);
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
		PaginationRecordsAndNumber<Worker, Number> entity = cService.getOverproofAge("768f0b92051a002df8b347c9f4dd70cd", 1, 20);
		map.put("entity", entity);
		return map;
	}

	// 审核表 获得一个公司过去未审核的年份列表
	@RequestMapping("/42")
	@ResponseBody
	public Map<String, Object> test42() {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] entity = cService.getUnauditYearByCompanycode("1390293306601");
		map.put("entity", entity);
		return map;
	}

	// 审核表 根据年份, 和公司code获得一个审核数据
	@RequestMapping("/43")
	@ResponseBody
	public Map<String, Object> test43() {
		Map<String, Object> map = new HashMap<String, Object>();
		Audit entity = auditService.getByPrimaryKey("2014", "1390293306601");
		map.put("entity", entity);
		return map;
	}

	// 公司表 更新数据
	@RequestMapping("/44")
	@ResponseBody
	public Map<String, Object> test44() {
		Map<String, Object> map = new HashMap<String, Object>();
		Company c = cService.getByPrimaryKey("636e88c06f5acd0858fd782dasdd674b");
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
		List<ReportViewModel> entity = rvmService.getByCompanyType("2014");
		map.put("entity", entity);
		return map;
	}

	// 统计报表--按经济类型
	@RequestMapping("/46")
	@ResponseBody
	public Map<String, Object> test46() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportViewModel> entity = rvmService.getByCompanyEconomyType("2014");
		map.put("entity", entity);
		return map;
	}

	// 统计报表--按地区
	@RequestMapping("/47")
	@ResponseBody
	public Map<String, Object> test47() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReportViewModel> entity = rvmService.getByArea("2222");
		map.put("entity", entity);
		return map;
	}

	// 统计报表--按地区
	@RequestMapping("/48")
	@ResponseBody
	public Map<String, Object> test48() {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer entity = cService.getCompanyCode();
		map.put("entity", entity);
		return map;
	}
	
	// 公司表--根据档案号查
		@RequestMapping("/49")
		@ResponseBody
		public Map<String, Object> test49() {
			Map<String, Object> map = new HashMap<String, Object>();
			boolean entity = cService.checkCompanyCode("251001");
			map.put("entity", entity);
			return map;
		}

}
