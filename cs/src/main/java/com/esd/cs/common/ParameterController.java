package com.esd.cs.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.cs.Constants;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyProperty;
import com.esd.hesf.model.CompanyType;
import com.esd.hesf.model.PaymentExceptional;
import com.esd.hesf.model.PaymentType;
import com.esd.hesf.model.Reply;
import com.esd.hesf.model.User;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;
import com.esd.hesf.service.AreaService;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.CompanyPropertyService;
import com.esd.hesf.service.CompanyTypeService;
import com.esd.hesf.service.PaymentExceptionalService;
import com.esd.hesf.service.PaymentTypeService;
import com.esd.hesf.service.ReplyService;
import com.esd.hesf.service.UserService;
import com.esd.hesf.service.WorkerHandicapLevelService;
import com.esd.hesf.service.WorkerHandicapTypeService;

/**
 * 常用参数 控制器
 * 
 * @author yuxingliang
 * 
 */

@Controller
@RequestMapping(value = "/security/parameter")
public class ParameterController {

	private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);
	@Autowired
	private CompanyTypeService companyTypeService;// 企业类型
	@Autowired
	private CompanyPropertyService companyPropertyService;// 企业性质
	@Autowired
	private CompanyEconomyTypeService companyEconomyTypeService;// 企业经济类型
	@Autowired
	private WorkerHandicapTypeService workerHandicapTypeService;// 残疾类型
	@Autowired
	private WorkerHandicapLevelService workerHandicapLevelService;// 残疾等级

	@Autowired
	private AuditParameterService auditParameterService; // 年审参数
	@Autowired
	private AreaService areaService;// 地区
	@Autowired
	private AuditProcessStatusService auditProcessStatusService;// 审核流程状态
	@Autowired
	private UserService userService;// 用户
	@Autowired
	private PaymentExceptionalService paymentExceptionalService;
	@Autowired
	private PaymentTypeService paymentTypeService;
	@Autowired
	private ReplyService replyService;

	private static List<CompanyProperty> companyPropertys;
	private static List<CompanyEconomyType> companyEconomyType;
	private static List<CompanyType> companyTypes;

	private static String nowYear = null;

	/**
	 * 返回最新年份
	 * 
	 * @param request
	 * @return
	 */
	public static String getYear() {
		return nowYear;
	}

	public static void setYear(String year) {
		nowYear = year;
	}

	/**
	 * 返回年份
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getyears")
	@ResponseBody
	public List<Map<String, String>> jsonNature(HttpServletRequest request) {
		logger.debug("getYears");
		List<Map<String, String>> list = new ArrayList<>();
		try {
			String[] arr = auditParameterService.getYears();
			for (String str : arr) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("text", str);
				map.put("id", str);
				list.add(map);
			}
			if (list != null) {
				logger.debug("getYearsSize{}:" + list.size());
				return list;
			}
		} catch (Exception e) {
			logger.error("error{}", e);
		}
		return null;
	}

	/**
	 * 返回 企业性质
	 */
	@RequestMapping(value = "/property")
	@ResponseBody
	public List<CompanyProperty> companyPropertyService(HttpServletRequest request) {

		companyPropertys = companyPropertyService.getAll();
		if (companyPropertys == null) {
			companyPropertys = new ArrayList<CompanyProperty>();
		}
		CompanyProperty c = new CompanyProperty();
		c.setId(Constants.DEFAULT_TVALUE);
		c.setCompanyProperty(Constants.PLEASE_SELECT);
		companyPropertys.add(0, c);
		logger.debug("getProperty{}", companyPropertys.toString());
		return companyPropertys;
	}

	/**
	 * 返回 企业性质Ex
	 */
	@RequestMapping(value = "/propertyEx")
	@ResponseBody
	public List<CompanyProperty> companyPropertyServiceEx(HttpServletRequest request) {

		companyPropertys = companyPropertyService.getAll();
		if (companyPropertys == null) {
			companyPropertys = new ArrayList<CompanyProperty>();
		}

		logger.debug("property{}", companyPropertys.toString());
		return companyPropertys;
	}

	/**
	 * 返回 经济类型Ex
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/economytypeEx")
	@ResponseBody
	public List<CompanyEconomyType> economytypeEx(HttpServletRequest request) {

		companyEconomyType = companyEconomyTypeService.getAll();
		if (companyEconomyType == null) {
			companyEconomyType = new ArrayList<CompanyEconomyType>();
		}

		logger.debug("economytype{}", companyEconomyType.toString());
		return companyEconomyType;
	}

	/**
	 * 返回 经济类型
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/economytype")
	@ResponseBody
	public List<CompanyEconomyType> companyEconomyTypeService(HttpServletRequest request) {

		companyEconomyType = companyEconomyTypeService.getAll();
		if (companyEconomyType == null) {
			companyEconomyType = new ArrayList<CompanyEconomyType>();
		}
		CompanyEconomyType c = new CompanyEconomyType();
		c.setId(Constants.DEFAULT_TVALUE);
		c.setCompanyEconomyType(Constants.PLEASE_SELECT);
		companyEconomyType.add(0, c);
		logger.debug("economytype{}", companyEconomyType.toString());
		return companyEconomyType;
	}

	/**
	 * 返回 企业类型
	 */
	@RequestMapping(value = "/companytype", method = RequestMethod.POST)
	@ResponseBody
	public List<CompanyType> companyTypeService(HttpServletRequest request) {

		companyTypes = companyTypeService.getAll();
		if (companyTypes == null) {
			companyTypes = new ArrayList<CompanyType>();
		}
		CompanyType c = new CompanyType();
		c.setId(Constants.DEFAULT_TVALUE);
		c.setCompanyType(Constants.PLEASE_SELECT);
		companyTypes.add(0, c);
		logger.debug("companytype{}", companyTypes.toString());
		return companyTypes;
	}

	/**
	 * 返回 企业类型Ex
	 */
	@RequestMapping(value = "/companytypeEx", method = RequestMethod.POST)
	@ResponseBody
	public List<CompanyType> companyTypeServiceEx(HttpServletRequest request) {
		companyTypes = companyTypeService.getAll();
		if (companyTypes == null) {
			companyTypes = new ArrayList<CompanyType>();
		}
		logger.debug("companytype{}", companyTypes.toString());
		return companyTypes;
	}

	/**
	 * 返回 残疾类型
	 */
	@RequestMapping(value = "/workerHandicapTypeService", method = RequestMethod.POST)
	@ResponseBody
	public List<WorkerHandicapType> workerHandicapTypeService(HttpServletRequest request) {

		return getHandicapType();
	}

	public List<WorkerHandicapType> getHandicapType() {
		List<WorkerHandicapType> list = null;
		try {
			list = workerHandicapTypeService.getAll();
			if (list == null) {
				list = new ArrayList<WorkerHandicapType>();
			}
			WorkerHandicapType c = new WorkerHandicapType();
			c.setId(Constants.DEFAULT_TVALUE);
			c.setHandicapType(Constants.PLEASE_SELECT);
			list.add(0, c);
			logger.debug("getworkerHandicapType{}", list.toString());
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 返回 残疾等级
	 */
	@RequestMapping(value = "/workerHandicapLevelService", method = RequestMethod.POST)
	@ResponseBody
	public List<WorkerHandicapLevel> workerHandicapLevelService(HttpServletRequest request) {
		List<WorkerHandicapLevel> list = null;
		try {
			list = workerHandicapLevelService.getAll();
			if (list == null) {
				list = new ArrayList<WorkerHandicapLevel>();
			}
			WorkerHandicapLevel c = new WorkerHandicapLevel();
			c.setId(Constants.DEFAULT_TVALUE);
			c.setHandicapLevel(Constants.PLEASE_SELECT);
			list.add(0, c);
			logger.debug("getWorkerHandicapLevel{}", list.toString());
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 返回 地区
	 */
	@RequestMapping(value = "/getArea", method = RequestMethod.POST)
	@ResponseBody
	public List<Area> areaService(HttpServletRequest request) {
		logger.debug("getArea:{}", "getArea");
		List<Area> list = null;
		try {
			list = areaService.getHeiLongjiangCities();
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 返回 流程状态
	 */
	@RequestMapping(value = "/getStatus", method = RequestMethod.POST)
	@ResponseBody
	public List<AuditProcessStatus> getStatus(HttpServletRequest request) {
		List<AuditProcessStatus> list = null;
		try {
			list = auditProcessStatusService.getAll();
			if (list == null) {
				list = new ArrayList<AuditProcessStatus>();
			}
			AuditProcessStatus c = new AuditProcessStatus();
			c.setId(Constants.DEFAULT_TVALUE);
			c.setAuditProcessStatus(Constants.PLEASE_SELECT);
			list.add(0, c);
			logger.debug("getStatus{}", list.toString());
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 返回 缴款人
	 */
	@RequestMapping(value = "/getPayer", method = RequestMethod.POST)
	@ResponseBody
	public List<User> getPayer(HttpServletRequest request) {
		List<User> list = null;
		try {
			list = userService.getPayer();
			if (list == null) {
				list = new ArrayList<User>();
			}
			User c = new User();
			c.setId(Constants.DEFAULT_TVALUE);
			c.setUserName(Constants.PLEASE_SELECT);
			list.add(0, c);
			logger.debug("getPayer{}", list.toString());
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 返回 缴款特殊信息
	 */
	@RequestMapping(value = "/getExStatic", method = RequestMethod.POST)
	@ResponseBody
	public List<PaymentExceptional> getExStatic(HttpServletRequest request) {
		List<PaymentExceptional> list = null;
		try {
			list = paymentExceptionalService.getAll();
			if (list == null) {
				list = new ArrayList<PaymentExceptional>();
			}
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 返回 缴款方式
	 */
	@RequestMapping(value = "/getPaymentType", method = RequestMethod.POST)
	@ResponseBody
	public List<PaymentType> getPaymentType(HttpServletRequest request) {
		List<PaymentType> list = null;
		try {
			list = paymentTypeService.getAll();
			if (list == null) {
				list = new ArrayList<PaymentType>();
			}
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}

	/**
	 * 转到初审单位列表页面
	 */
	@RequestMapping(value = "/getReason", method = RequestMethod.POST)
	@ResponseBody
	public List<Reply> getReason(HttpServletRequest request) {
		List<Reply> list = null;
		try {
			list = replyService.getAll();
			if (list == null) {
				list = new ArrayList<Reply>();
			}
			return list;

		} catch (Exception e) {
			logger.error("error:{}", e);
		}
		return null;
	}
}
