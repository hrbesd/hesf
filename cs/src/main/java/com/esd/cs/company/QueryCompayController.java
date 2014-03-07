package com.esd.cs.company;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.cs.common.ParameterController;
import com.esd.cs.common.PoiCreateExcel;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.CompanyService;

@Controller
@RequestMapping(value = "/security/query/company")
public class QueryCompayController {
	private static final Logger logger = LoggerFactory.getLogger(QueryCompayController.class);
	@Autowired
	private CompanyService companyService;// 企业

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView company_list(HttpServletRequest request) {
		// 获取当前年份
		request.setAttribute("nowYear", ParameterController.getYear());
		logger.debug("goToPage:{}", "queryCompany");
		return new ModelAndView("query/company");

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> companyPost(CompanyParamModel params, HttpServletRequest request) {

		logger.debug("queryCompanyParams{}", params);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("companyCode", params.getCompanyCode()); // 公司档案号
		paramsMap.put("companyTaxCode", params.getCompanyTaxCode()); // 公司税务编码
		paramsMap.put("companyLegal", params.getCompanyLegal()); // 公司法人代表
		paramsMap.put("companyProperty", params.getCompanyProperty()); // 公司性质
																		// 对应的id
		paramsMap.put("companyEconomyType", params.getCompanyEconomyType()); // 公司经济类型
																				// 对应的id
		paramsMap.put("areaCode", params.getArea()); // 地区 对应地区 code
		paramsMap.put("minTotal", params.getCompanyEmpTotal_1()); // 查询范围中
																	// 公司最少人数
		paramsMap.put("maxTotal", params.getCompanyEmpTotal_2()); // 查询范围中
																	// 公司最多人数
		paramsMap.put("minHandicapTotal", params.getCompanyHandicapTotal_1()); // 查询范围中
																				// 残疾职工最少人数
		paramsMap.put("maxHandicapTotal", params.getCompanyHandicapTotal_2()); // 查询范围中
																				// 残疾职工最多人数
		paramsMap.put("companyName", params.getCompanyName()); // 公司名称
		paramsMap.put("companyAddress", params.getCompanyAddress()); // 公司地址
		paramsMap.put("companyOrganizationCode", params.getCompanyOrganizationCode()); // 组织机构代码证
		paramsMap.put("page", params.getPage()); // 分页--起始页
													// ******************************
		paramsMap.put("pageSize", params.getRows());// 分页--返回量
													// ******************************

		Map<String, Object> entity = new HashMap<>();
		PaginationRecordsAndNumber<Company, Number> query = companyService.getByMultiCondition(paramsMap);

		Integer total = query.getNumber().intValue();// 数据总条数
		List<Map<String, Object>> list = new ArrayList<>();
		for (Iterator<Company> iterator = query.getRecords().iterator(); iterator.hasNext();) {
			Company it = iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id", it.getId());// id
			map.put("companyCode", it.getCompanyCode());// 档案编码
			map.put("companyTaxCode", it.getCompanyTaxCode());// 税务编码
			map.put("companyName", it.getCompanyName());// 企业名称
			map.put("companyArea", it.getArea().getName());// 地区
			list.add(map);
		}
		entity.put("total", total);
		entity.put("rows", list);
		logger.debug("queryCompanyResult:{}", list.toString());
		return entity;
	}

	/**
	 * 批量导出企业信息
	 * 
	 * @param idArr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportCompany", method = RequestMethod.POST)
	@ResponseBody
	public String exportCompany(@RequestParam(value = "params[]") Integer idArr[], HttpServletRequest request) {
		logger.debug("deleteCompany:{}", idArr.toString());
		boolean b = true;
		List<Company> company = new ArrayList<Company>();
		for (int i = 0; i < idArr.length; i++) {
			company.add(companyService.getByPrimaryKey(idArr[i]));
		}
		String url = request.getServletContext().getRealPath("/");

		// 创建导出文件夹
		File uploadPath = new File(url + "upload");
		// 导出文件夹
		String exportFolder = uploadPath + File.separator + "company";
		File companyPath = new File(exportFolder);
		if (!(uploadPath.exists())) {
			uploadPath.mkdir();
		}
		if (!(companyPath.exists())) {
			companyPath.mkdir();
		}

		// 创建文件唯一名称
		String uuid = UUID.randomUUID().toString();
		String exportPath = exportFolder + File.separator + uuid + ".xls";
		String FileDownloadPath = "null";
		// 导出文件
		b = PoiCreateExcel.createComapnyExcel(exportPath, company);
		if (b) {
			String destPath = request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath();
			FileDownloadPath = "http://" + destPath + "/upload/company/" + uuid + ".xls";
		}
		logger.debug("ecportCompanyResults:{},paramsId:{}", b, idArr);
		return FileDownloadPath;
	}

}
