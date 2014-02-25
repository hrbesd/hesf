package com.esd.cs.audit;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.CalendarUtil;
import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.worker.QueryWorkerController;
import com.esd.hesf.model.Audit;
import com.esd.hesf.service.AuditService;

@Controller
@RequestMapping(value = "/security/query/audit")
public class QueryAuditController {
	private static final Logger logger = LoggerFactory.getLogger(QueryWorkerController.class);

	@Autowired
	private AuditService auditService;

	/**
	 * 转到查询审核数据列表页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView queryAuditList(HttpServletRequest request) {
		// 获取当前年份
		request.setAttribute("nowYear", CalendarUtil.getLastYear());
		logger.debug("goToPage:{}", "queryAudit");
		return new ModelAndView("query/audit");
	}

	

	/**
	 * 获取审核列表数据
	 * 
	 * @param page
	 * @param rows
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> provincePost(AuditParamModel params, HttpServletRequest request) {
		logger.debug("queryAuditParams:{}", params);

		Map<String, Object> entity = new HashMap<>();
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("year", params.getYear()); // 年度
			paramsMap.put("companyCode", params.getCompanyCode()); // 公司档案号
			paramsMap.put("companyTaxCode", params.getCompanyTaxCode()); // 公司税务编码
			paramsMap.put("companyOrganizationCode", params.getCompanyOrganizationCode()); // 组织机构代码证
			paramsMap.put("companyProperty", params.getCompanyProperty()); // 公司性质
			paramsMap.put("companyEconomyType", params.getCompanyEconomyType()); // 公司经济类型
			paramsMap.put("areaCode", params.getArea()); // 地区 对应地区 code
			paramsMap.put("minTotal", params.getCompanyEmpTotal_1()); // 查询范围中
			paramsMap.put("maxTotal", params.getCompanyEmpTotal_2()); // 查询范围中
			paramsMap.put("companyName", params.getCompanyName()); // 公司名称
			paramsMap.put("companyAddress", params.getCompanyAddress()); // 公司地址
			paramsMap.put("companyLegal", params.getCompanyLegal()); // 公司法人代表
			paramsMap.put("auditProcessStatus", params.getAuditProcessStatus()); // 流程状态
			paramsMap.put("paymentPerson", params.getPaymentPerson()); // 缴款人 id
			paramsMap.put("overYear", params.getOverYear());// 超过几年未初审的公司
			paramsMap.put("isExempt", Boolean.valueOf(params.getIsExempt())); // 是否免缴
																				// true免缴,
																				// false不免缴
		
			paramsMap.put("page", params.getPage()); // 分页--起始页
			paramsMap.put("pageSize", params.getRows());// 分页--返回量
			if (!params.getIsExempt().equals("")) {
				
			}
			logger.debug("queryAuditParamsEx:{}", params);
			PaginationRecordsAndNumber<Audit, Number> query = auditService.getByMultiCondition(paramsMap);
			Integer total = query.getNumber().intValue();// 数据总条数
			List<Map<String, Object>> list = new ArrayList<>();
			for (Iterator<Audit> iterator = query.getRecords().iterator(); iterator.hasNext();) {
				Audit it = iterator.next();
				Map<String, Object> map = new HashMap<>();
				map.put("id", it.getCompany().getId());// id
				map.put("companyCode", it.getCompany().getCompanyCode());// 企业档案编号
				map.put("companyTaxCode", it.getCompany().getCompanyTaxCode());// 税务编号
				map.put("companyName", it.getCompany().getCompanyName());// 企业名称
				map.put("auditProcessStatus", it.getAuditProcessStatus().getAuditProcessStatus());// 流程状态
				list.add(map);
			}
			entity.put("total", total);
			entity.put("rows", list);
			logger.debug("queryAuditResult:{}", total);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("queryAuditError{}", e.getMessage());
		}
		return entity;
	}
}
