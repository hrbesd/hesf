package com.esd.hesf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditDao;
import com.esd.hesf.dao.AuditParameterDao;
import com.esd.hesf.dao.CompanyDao;
import com.esd.hesf.dao.CompanyWorkerViewDao;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyProperty;
import com.esd.hesf.model.CompanyType;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.model.Worker;
import com.esd.hesf.service.CompanyService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.KitService;

/**
 * 公司信息 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class CompanyServiceImpl implements CompanyService {
	private static Logger logger = LoggerFactory
			.getLogger(CompanyServiceImpl.class);

	@Autowired
	private CompanyDao dao;

	// 公司--员工--年份 关系表dao接口
	@Autowired
	private CompanyYearWorkerDao cywDao;

	// 审核表dao接口
	@Autowired
	private AuditDao auDao;

	// 公司--员工 视图表dao接口
	@Autowired
	private CompanyWorkerViewDao cwvDao;

	// 审核参数表dao接口
	@Autowired
	private AuditParameterDao apDao;

//	@Override
//	public boolean save(Company t) {
//		return false;
//	}

	@Override
	public boolean save(Company t) {
		if (t.getCompanyCode() == null) {
			new HesfException("company.companyCode", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompanyName() == null) {
			new HesfException("company.companyName", HesfException.type_null)
					.printStackTrace();
			return false;
		}
//		if (t.getCompanyLegal() == null) {
//			new HesfException("company.companyLegal", HesfException.type_null)
//					.printStackTrace();
//			return false;
//		}
//		if (t.getCompanyOrganizationCode() == null) {
//			new HesfException("company.companyOrganizationCode",
//					HesfException.type_null).printStackTrace();
//			return false;
//		}
		if (t.getCompanyType() == null) {
			new HesfException("company.companyType", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompanyType().getId() == null
				|| t.getCompanyType().getId() <= 0) {
			new HesfException("company.companyType.id", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompanyEconomyType() == null) {
			new HesfException("company.companyEconomyType",
					HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyEconomyType().getId() == null
				|| t.getCompanyEconomyType().getId() <= 0) {
			new HesfException("company.companyEconomyType.id",
					HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyProperty() == null) {
			new HesfException("company.companyProperty",
					HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyProperty().getId() == null
				|| t.getCompanyProperty().getId() <= 0) {
			new HesfException("company.companyProperty.id",
					HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getArea() == null) {
			new HesfException("company.area", HesfException.type_null)
					.printStackTrace();
		}
		if (t.getArea().getCode() == null) {
			new HesfException("company.companyProperty.id",
					HesfException.type_null).printStackTrace();
		}
		// 保存公司
		int i = dao.insertSelective(t);
		if (i != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	// 本来应该执行逻辑删除掉滴 *_*
	@Override
	public boolean delete(Integer id) {

		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Company t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Company getByPrimaryKey(Integer id) {
		Company t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Company, Number> getPaginationRecords(
			Company t, Integer page, Integer pageSize) {
		// 处理地区code,转化为适合sql语句的 xxxx 暂时不启用
		// if (t != null) {
		// if (t.getArea() != null) {
		// if (t.getArea().getCode() != null &&
		// !"".equals(t.getArea().getCode())) {
		// t.getArea().setCode(KitService.areaCodeForSql(t.getArea().getCode()));
		// }
		// }
		// }
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("company", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Company> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Company, Number> prn = new PaginationRecordsAndNumber<Company, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	// 测试用
	@Override
	public List<Company> getAll(int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	// 按ID数组查找多个企业列表
	@Override
	public List<Company> getMultiById(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		List<Company> list = new ArrayList<Company>();
		for (int i = 0; i < arr.length; i++) {
			Company company = dao.retrieveByPrimaryKey(arr[i]);
			list.add(company);
		}
		return list;
	}

	// 按ID数组删除多个企业
	@Override
	public boolean deleteMultiById(int[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		boolean check = true;
		for (int i = 0; i < arr.length; i++) {
			boolean bl = dao.deleteByPrimaryKey(arr[i]) == 1 ? true : false;
			if (!bl) {
				check = false;
				break;
			}
		}
		return check;
	}

//	// 自动生成最新档案号
//	public String getDocumentCode() {
//		String oldCode = dao.getLatestDocumentCode();
//		logger.debug("oldCode :" + oldCode);
//		if (StringUtils.isBlank(oldCode)) {
//			return null;
//		}
//		// 方法①
//		int code = Integer.parseInt(oldCode);
//		code++;
//		return String.valueOf(code);

		// 方法②
		// String sub = oldCode.substring(3);
		// int num = Integer.parseInt(sub);
		// num++;
		// String code = "301";
		// if (num < 10) {
		// code += "00000" + num;
		// } else if (num < 100 && num > 9) {
		// code += "0000" + num;
		// } else if (num < 1000 && num > 99) {
		// code += "000" + num;
		// } else if (num < 10000 && num > 999) {
		// code += "00" + num;
		// } else if (num < 100000 && num > 9999) {
		// code += "0" + num;
		// } else if (num < 1000000 && num > 99999) {
		// code += num;
		// }
		// logger.debug("code :"+code);
		// return code;
//	}

	@Override
	public Company getByCompanyOrganizationCode(String companyOrganizationCode) {
		Company company = dao
				.retrieveByOrganizationCode(companyOrganizationCode);
		return company;
	}

	// @Override
	// public boolean copyLatYearData(String currentYear, String lastYear) {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("currentYear", currentYear);
	// map.put("lastYear", lastYear);
	// int k = dao.insertLastYearData(map);
	// return k > 0 ? true : false;
	// }

	@Override
	public boolean deleteWorkerFromCompany(String year, Integer companyId,
			Integer workerId) {
		// 三个参数都不能为空
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (workerId <= 0) {
			new HesfException("workerId", HesfException.type_number_negative)
					.printStackTrace();
			return false;
		}
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setYear(year);
		cyw.setCompanyId(companyId);
		cyw.setWorkerId(workerId);
		boolean bl = cywDao.deleteBySelfObject(cyw) == 1 ? true : false;
		if (!bl) {
			new HesfException("year", HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public PaginationRecordsAndNumber<Company, Number> getByMultiCondition(
			Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		int page = 1;
		int pageSize = Constants.SIZE;
		if (map.get("page") != null) {
			page = Integer.parseInt(map.get("page").toString());
		}
		if (map.get("pageSize") != null) {
			pageSize = Integer.parseInt(map.get("pageSize").toString());
		}
		Company t = new Company();
		if (map.get("companyCode") != null) {
			t.setCompanyCode(map.get("companyCode").toString());
		}
		if (map.get("companyTaxCode") != null) {
			t.setCompanyTaxCode(map.get("companyTaxCode").toString());
		}
		if (map.get("companyLegal") != null) {
			t.setCompanyLegal(map.get("companyLegal").toString());
		}
		if (map.get("companyProperty") != null) {
			t.setCompanyProperty(new CompanyProperty(Integer.parseInt(map.get(
					"companyProperty").toString())));
		}
		if (map.get("companyEconomyType") != null) {
			t.setCompanyEconomyType(new CompanyEconomyType(Integer.parseInt(map
					.get("companyEconomyType").toString())));
		}
		if (map.get("companyType") != null) {
			t.setCompanyType(new CompanyType(Integer.parseInt(map.get(
					"companyType").toString())));
		}
		if (map.get("areaCode") != null) {
			// 地区处理, xxxx 暂时不启用
			// t.setArea(new
			// Area(KitService.areaCodeForSql(map.get("areaCode").toString())));
			t.setArea(new Area(map.get("areaCode").toString()));
		}
		if (map.get("companyName") != null) {
			t.setCompanyName(map.get("companyName").toString());
		}
		if (map.get("companyAddress") != null) {
			t.setCompanyAddress(map.get("companyAddress").toString());
		}
		if (map.get("companyOrganizationCode") != null) {
			t.setCompanyOrganizationCode(map.get("companyOrganizationCode")
					.toString());
		}
		// 将参数放入到map中
		map.put("company", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Company> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Company, Number> prn = new PaginationRecordsAndNumber<Company, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public Integer getWorkerHandicapTotal(Integer companyId, String year) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setCompanyId(companyId);
		cyw.setYear(year);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyYearWorker", cyw);
		return cywDao.retrieveCount(map);
	}
	
	@Override
	public Integer copyLastYearWorker(String currentYear, String lastYear, Integer companyId) {
		if (currentYear == null || "".equals(currentYear)) {
			new HesfException("currentYear", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (lastYear == null || "".equals(lastYear)) {
			new HesfException("lastYear", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("currentYear", currentYear);
		map.put("lastYear", lastYear);
		map.put("companyId", companyId);
		return cywDao.insertLastYearData(map);
	}

	@Override
	public PaginationRecordsAndNumber<Worker, Number> getOverproofAge(
			String year, Integer companyId, Integer page, Integer pageSize) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		// 得到对应年份的参数
		AuditParameter ap = apDao.retrieveByYear(year);
		// 计算男女各自的最大出生日期
		String maxMaleBirth = KitService.getBirthFromAge(ap.getRetireAgeMale()
				+ "");
		String maxFemaleBirth = KitService.getBirthFromAge(ap
				.getRetireAgeFemale() + "");
		// 参数map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("companyId", companyId);
		map.put("maxMaleBirth", maxMaleBirth);
		map.put("maxFemaleBirth", maxFemaleBirth);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Worker> list = cwvDao.retrieveRetiredWorkerByCompany(map);
		// 数据条数
		int count = cwvDao.retrieveRetiredWorkerByCompanyCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Worker, Number> prn = new PaginationRecordsAndNumber<Worker, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public String[] getUnauditYearByCompany(Integer companyId, String year) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("year", year);
		String[] years = auDao.retireveUnauditYearByCompanycode(map);
		return years;
	}

	@Override
	public Company getByCompanyCode(String companyCode) {
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null);
			return null;
		}
		return dao.retrieveByCompanyCode(companyCode);
	}

	@Override
	public List<Audit> getUnauditByCompany(Integer companyId, String year,
			Integer auditProcessStatus) {
		Audit audit = new Audit();
		audit.setCompany(new Company(companyId));
		audit.setYear(year);
		audit.setAuditProcessStatus(new AuditProcessStatus(auditProcessStatus));
		return auDao.retireveUnauditByCompany(audit);
	}

	@Override
	public List<Audit> getUnauditByCompany(String companyCode, String year,
			Integer auditProcessStatus) {
		Audit audit = new Audit();
		audit.setCompany(new Company(companyCode));
		audit.setYear(year);
		audit.setAuditProcessStatus(new AuditProcessStatus(auditProcessStatus));
		return auDao.retireveUnauditByCompany(audit);
	}

	@Override
	public String getNextCompanyCode() {
		String nextCompanyCode = dao.retrieveNextCompanyCode();
		if(nextCompanyCode == null || "".equals(nextCompanyCode)){
			nextCompanyCode = "100000";
		}
		return nextCompanyCode;
	}
	
	

}
