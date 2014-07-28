package com.esd.cs.payment;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Accounts;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.AccountsService;

/**
 * 账目controller
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/security/accounts")
public class AccountsController {

	@Autowired
	AccountsService acService;

	private DecimalFormat df = new DecimalFormat("0.00");
	
	/**
	 * 根据查询条件获得账目列表(缴款列表)
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> post_list(HttpServletRequest request) {
		String year = request.getParameter("year");
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("rows"));
		Integer process = Integer.valueOf(request.getParameter("process"));
		String money = request.getParameter("money");
		String companyCode = request.getParameter("companyCode");
		String companyTaxCode = request.getParameter("companyTaxCode");
		String companyName = request.getParameter("companyName");
		String belongsType = request.getParameter("belongsType"); // 所属类型--在省残联缴款或者地税缴款(6字开头)
		String isReceipt = request.getParameter("isReceipt"); // 是否已开发票

		// 账目对象
		Accounts ac = new Accounts();
		ac.setYear(year);
		Company company = new Company();
		company.setCompanyCode(companyCode);
		company.setCompanyTaxCode(companyTaxCode);
		company.setCompanyName(companyName);
		ac.setCompany(company);
		ac.setAuditProcessStatus(new AuditProcessStatus(process));
		if(money != null && !"".equals(money)){
			ac.setTotalMoney(new BigDecimal(money));
		}
		if (isReceipt != null && !"".equals(isReceipt)) {
			if ("1".equals(isReceipt)) {
				ac.setIsReceipt(Boolean.TRUE);
			}else{
				ac.setIsReceipt(Boolean.FALSE);
			}
		}
		// 根据公司的相关信息, 查询账目表
		PaginationRecordsAndNumber<Accounts, Number> query = acService
				.getPaginationRecordsGroupByCompany(ac, belongsType, page,
						pageSize);
		Map<String, Object> entity = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Iterator<Accounts> iterator = query.getRecords().iterator(); iterator
				.hasNext();) {
			Accounts it = iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("year", it.getYear()); // 出账年份
			map.put("companyCode", it.getCompany().getCompanyCode());// 企业档案编号
			map.put("companyTaxCode", it.getCompany().getCompanyTaxCode());// 税务编号
			// map.put("accountsId", it.getId());// 账目id名称
			map.put("companyName", it.getCompany().getCompanyName());// 企业名称
			map.put("companyId", it.getCompany().getId()); // 公司id
			map.put("auditProcessStatus", it.getAuditProcessStatus()
					.getAuditProcessStatus()); // 审核状态
			map.put("auditProcessStatusId", it.getAuditProcessStatus().getId()); // 审核状态id
			list.add(map);
		}
		entity.put("total", query.getNumber().intValue());
		entity.put("rows", list);
		return entity;
	}
}
