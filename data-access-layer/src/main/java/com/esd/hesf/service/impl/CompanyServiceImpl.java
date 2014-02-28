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
import com.esd.hesf.dao.CompanyCodeDao;
import com.esd.hesf.dao.CompanyDao;
import com.esd.hesf.dao.CompanyWorkerViewDao;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyProperty;
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
	private static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

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

	@Autowired
	private CompanyCodeDao ccDao;

	@Override
	public boolean save(Company t) {
		t.setId(KitService.getUUID());
		if (t.getYear() == null) {
			new HesfException("company.year", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyCode() == null) {
			new HesfException("company.companyCode", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyCode() == null) {
			new HesfException("company.companyCode", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyName() == null) {
			new HesfException("company.companyName", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyLegal() == null) {
			new HesfException("company.companyLegal", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyOrganizationCode() == null) {
			new HesfException("company.companyOrganizationCode", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyType() == null) {
			new HesfException("company.companyType", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyType().getId() == null) {
			new HesfException("company.companyType.id", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyEconomyType() == null) {
			new HesfException("company.companyEconomyType", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyEconomyType().getId() == null) {
			new HesfException("company.companyEconomyType.id", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyProperty() == null) {
			new HesfException("company.companyProperty", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyProperty().getId() == null) {
			new HesfException("company.companyProperty.id", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyProperty() == null) {
			new HesfException("company.companyProperty", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompanyProperty().getId() == null) {
			new HesfException("company.companyProperty.id", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getArea() == null) {
			new HesfException("company.area", HesfException.type_null).printStackTrace();
		}
		if (t.getArea().getCode() == null) {
			new HesfException("company.companyProperty.id", HesfException.type_null).printStackTrace();
		}
		// 保存公司
		int i = dao.insertSelective(t);
		// 向审核表中插入信息
		Audit audit = new Audit();
		audit.setYear(t.getYear());
		audit.setCompany(t);
		audit.setArea(t.getArea());
		int k = auDao.insertSelective(audit);
		if(k!=1 || i!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(String id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(Company t) {
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public Company getByPrimaryKey(String id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public Company getByYearAndCode(String year, String companyCode) {
		if (year == null || "".equals(year) || companyCode == null || "".equals(companyCode)) {
			return null;
		}
		System.out.println(11111);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("companyCode", companyCode);
		return dao.retrieveByYearAndCode(map);
	}

	@Override
	public PaginationRecordsAndNumber<Company, Number> getPaginationRecords(Company t, Integer page, Integer pageSize) {
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

	// 得到所有审核年度
	@Override
	public String[] getYears() {
		String[] arr = dao.retrieveYears();
		if (arr == null) {
			return null;
		}
		return arr;
	}

	// 得到有数据的上一年份
	@Override
	public String getLastYear(String thisYear) {
		return dao.retrieveLastYear(thisYear);
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

	// 自动生成最新档案号
	public String getDocumentCode() {
		String oldCode = dao.getLatestDocumentCode();
		logger.debug("oldCode :" + oldCode);
		if (StringUtils.isBlank(oldCode)) {
			return null;
		}
		// 方法①
		int code = Integer.parseInt(oldCode);
		code++;
		return String.valueOf(code);

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
	}

	@Override
	public Company getCompanyByOrganizationCode(String companyOrganizationCode) {
		List<Company> list = dao.retrieveByOrganizationCode(companyOrganizationCode);
		if (list == null) {
			return null;
		}
		if (list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public Company getByPrimaryKey(int id) {
		return null;
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
	public boolean deleteWorkerFromCompany(String year, String companyCode, int workerId) {
		// 三个参数都不能为空
		if (year == null || "".equals(year)) {
			return false;
		}
		if (companyCode == null || "".equals(companyCode)) {
			return false;
		}
		if (workerId <= 0) {
			return false;
		}
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setYear(year);
		cyw.setCompanyCode(companyCode);
		cyw.setWorkerId(workerId);
		return cywDao.deleteBySelfObject(cyw) == 1 ? true : false;
	}

	@Override
	public PaginationRecordsAndNumber<Company, Number> getByMultiCondition(Map<String, Object> map) {
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
		if (map.get("year") != null) {
			t.setYear(map.get("year").toString());
		}
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
			t.setCompanyProperty(new CompanyProperty(Integer.parseInt(map.get("companyProperty").toString())));
		}
		if (map.get("companyEconomyType") != null) {
			t.setCompanyEconomyType(new CompanyEconomyType(Integer.parseInt(map.get("companyEconomyType").toString())));
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
			t.setCompanyOrganizationCode(map.get("companyOrganizationCode").toString());
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
	public int getWorkerHandicapTotal(String companyCode, String year) {
		if (companyCode == null || "".equals(companyCode) || year == null || "".equals(year)) {
			return -1;
		}
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setCompanyCode(companyCode);
		cyw.setYear(year);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyYearWorker", cyw);
		return cywDao.retrieveCount(map);
	}

	@Override
	public PaginationRecordsAndNumber<Worker, Number> getOverproofAge(String year, String companyCode, Integer page, Integer pageSize) {
		if (year == null || "".equals(year) || companyCode == null || "".equals(companyCode)) {
			return null;
		}
		// 得到对应年份的参数
		AuditParameter ap = apDao.retrieveByYear(year);
		// 计算男女各自的最大出生日期
		String maxMaleBirth = KitService.getBirthFromAge(ap.getRetireAgeMale() + "");
		String maxFemaleBirth = KitService.getBirthFromAge(ap.getRetireAgeFemale() + "");
		// 参数map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("companyCode", companyCode);
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
	public PaginationRecordsAndNumber<Worker, Number> getOverproofAge(String companyId, Integer page, Integer pageSize) {
		if (companyId == null || "".equals(companyId)) {
			return null;
		}
		Company company = dao.retrieveByPrimaryKey(companyId);
		String year = company.getYear();
		// 得到对应年份的参数
		AuditParameter ap = apDao.retrieveByYear(year);
		// 计算男女各自的最大出生日期
		String maxMaleBirth = KitService.getBirthFromAge(ap.getRetireAgeMale() + "");
		String maxFemaleBirth = KitService.getBirthFromAge(ap.getRetireAgeFemale() + "");
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
	public String[] getUnauditYearByCompanycode(String companyCode, String year) {
		if (companyCode == null || "".equals(companyCode) || year == null || "".equals(year)) {
			return null;
		}
		// 限定年份为4位长度
		if (year.length() != 4) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyCode", companyCode);
		map.put("year", year);
		String[] years = auDao.retireveUnauditYearByCompanycode(map);
		return years;
	}

	@Override
	public Integer gotCompanyCode() {
		Integer temp = ccDao.retrieveCompanyCode();
		int k = ccDao.update(temp);
		if (k == 1) {
			return temp;
		}
		return null;
	}

	@Override
	public boolean checkCompanyCode(String companyCode) {
		if (companyCode == null || "".equals(companyCode)) {
			return false;
		}
		return dao.retrieveCountByCompanyCode(companyCode) > 0 ? true : false;
	}

}
