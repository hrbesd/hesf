package com.esd.cs.settings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.Constants;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.UserGroup;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.UserGroupService;
import com.esd.hesf.service.UserService;

/**
 * 年审参数
 */
@Controller
@RequestMapping(value = "/security/settings/yearAuditParameter")
public class YearAuditParameterController {
	private static final Logger logger = LoggerFactory.getLogger(YearAuditParameterController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private AuditParameterService auditParameterService;
	@Autowired
	private AuditService auditService;
	@Autowired
	private CompanyService companyService;

	/**
	 * 转到年审参数
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView user() {
		logger.debug("yearAuditParameter");
		return new ModelAndView("settings/parameter_list");
	}

	/**
	 * 查询用户列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "page") Integer page, @RequestParam(value = "rows") Integer rows, HttpServletRequest request) {
		Map<String, Object> entity = new HashMap<>();
		try {
			AuditParameter auditParameter = new AuditParameter();
			PaginationRecordsAndNumber<AuditParameter, Number> query = null;
			query = auditParameterService.getPaginationRecords(auditParameter, page, rows);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<AuditParameter> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				AuditParameter it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getId());// id
				map.put("year", it.getYear());// 年度
				map.put("areaCode", it.getArea().getName());// 地区
				map.put("averageSalary", it.getAverageSalary());// 计算基数
				map.put("putScale", it.getPutScale());// 安置比例
				map.put("payCloseDate", it.getPayCloseDate());// 支付截至日期
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("total:{},rows:{}", total, list.toString());

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return entity;
	}

	/**
	 * 获得用户组
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/group")
	@ResponseBody
	public List<UserGroup> getUserGroup() {
		List<UserGroup> list = userGroupService.getAll();
		logger.debug("UserGroups:{}", list);
		return list;
	}

	/**
	 * 检测添加年审时是否以经添加过
	 * 
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkYear() {
		String year = CalendarUtil.getNowYear();
		AuditParameter auditParameter = auditParameterService.getByYear(year);
		if (auditParameter != null) {
			return true;
		}
		return false;
	}

	/**
	 * 跳转到添加年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addGet(HttpServletRequest request) {
		String year = CalendarUtil.getNowYear();
		request.setAttribute("year", year);
		return new ModelAndView("settings/parameter_save");
	}

	/**
	 * 添加年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addPost(AuditParameter auditParameter, HttpServletRequest request) {
		logger.debug("auditParameter:{}", auditParameter);
		BigDecimal scale = auditParameter.getPutScale();
		BigDecimal puScale = scale.divide(new BigDecimal(Constants.PERCENTAGE));
		logger.debug("puScale:{}", puScale);
		auditParameter.setPutScale(puScale);
		String currentYear = auditParameter.getYear(); // 获取指定要审核的年
		String lastYear = companyService.getLastYear();// 获取上一次有效的年;
//		try {
//			auditService.initAuditData(currentYear, lastYear);
//		} catch (Exception e) {
//			logger.error("initAuditData", e);
//		}
		auditParameterService.save(auditParameter);
		return true;
	}

	/**
	 * 更新年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Boolean updatePost(AuditParameter auditParameter) {
		logger.debug(auditParameter.toString());
		return auditParameterService.update(auditParameter);
	}

	/**
	 * 批量删除年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Boolean deletePost(@RequestParam(value = "params[]") int[] ids) {
		logger.debug(ids.toString());
		for (int i = 0; i < ids.length; i++) {
			if (auditParameterService.delete(ids[i]) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 编辑年审参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editGet(@PathVariable("id") int id) {
		logger.debug("id:{}", id);
		AuditParameter auditParameter = auditParameterService.getByPrimaryKey(id);
		return new ModelAndView("settings/parameter_edit", "entity", auditParameter);
	}
}
