package com.esd.hesf.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditCompanyViewDao;
import com.esd.hesf.dao.AuditDao;
import com.esd.hesf.dao.CompanyDao;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.dao.UserDao;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 审核service 实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	private AuditDao dao;

	// 公司dao接口
	@Autowired
	private CompanyDao cDao;

	// 审核表视图 dao接口
	@Autowired
	private AuditCompanyViewDao acvDao;

	// 公司--员工--年份 关系表dao接口
	@Autowired
	private CompanyYearWorkerDao cywDao;

	@Autowired
	private UserDao uDao;

	@Override
	public boolean save(Audit t) {
		if (t.getCompany() == null) {
			new HesfException("audit.company", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompany().getYear() == null) {
			new HesfException("audit.company.year", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompany().getCompanyCode() == null) {
			new HesfException("audit.company.companyCode", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getAuditProcessStatus() == null) {
			new HesfException("audit.auditProcessStatus", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getAuditProcessStatus().getId() == null) {
			new HesfException("audit.auditProcessStatus.id", HesfException.type_null).printStackTrace();
			return false;
		}
		int k = dao.insertSelective(t);if(k!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(Audit t) {
		if (t.getCompany() == null) {
			new HesfException("audit.company", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompany().getYear() == null) {
			new HesfException("audit.company.year", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getCompany().getCompanyCode() == null) {
			new HesfException("audit.company.companyCode", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getAuditProcessStatus() == null) {
			new HesfException("audit.auditProcessStatus", HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getAuditProcessStatus().getId() == null) {
			new HesfException("audit.auditProcessStatus.id", HesfException.type_null).printStackTrace();
			return false;
		}
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public Audit getByPrimaryKey(int id) {
		Audit audit = dao.retrieveByPrimaryKey(id);
		if (audit == null) {
			new HesfException("没有找到对应id的Audit对象", HesfException.type_null).printStackTrace();
			return null;
		}
		// 查询该公司当年度残疾职工人数
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setCompanyCode(audit.getCompany().getCompanyCode());
		cyw.setYear(audit.getYear());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyYearWorker", cyw);
		int companyHandicapTotal = cywDao.retrieveCount(map);
		// 放入审核对象公司的数据中
		audit.getCompany().setCompanyHandicapTotal(companyHandicapTotal);
		// 查询初审人对象
		if (audit.getInitAuditUser() != null) {
			if (audit.getInitAuditUser().getId() != null && audit.getInitAuditUser().getId() > 0) {
				audit.setInitAuditUser(uDao.retrieveByPrimaryKey(audit.getInitAuditUser().getId()));
			}
		}
		// 查询复审人对象
		if (audit.getVerifyAuditUser() != null) {
			if (audit.getVerifyAuditUser().getId() != null && audit.getVerifyAuditUser().getId() > 0) {
				audit.setVerifyAuditUser(uDao.retrieveByPrimaryKey(audit.getVerifyAuditUser().getId()));
			}
		}
		return audit;
	}

	@Override
	public Audit getByPrimaryKey(String year, String companyCode) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null).printStackTrace();
			return null;
		}
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null).printStackTrace();
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("companyCode", companyCode);
		Audit audit = dao.retrieveByYearAndCompanyCode(map);
		return audit;
	}

	@Override
	public PaginationRecordsAndNumber<Audit, Number> getPaginationRecords(Audit t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("audit", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Audit> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Audit, Number> prn = new PaginationRecordsAndNumber<Audit, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<Audit> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", 0);
		// 返回量
		map.put("size", Constants.SIZE);
		// 返回的数据
		List<Audit> list = dao.retrieveByPage(map);
		return list;
	}

	@Override
	public boolean initAuditData(String currentYear, String lastYear) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentYear", currentYear);
		map.put("lastYear", lastYear);
		// 向公司表里插入数据
		int j = cDao.insertLastYearData(map);
		if (j <= 0) {
			new HesfException("向公司表里插入数据		失败").printStackTrace();
		}
		// 向审核表里插入数据
		int k = dao.insertLastYearData(map);
		if (k <= 0) {
			new HesfException("向审核表里插入数据		失败").printStackTrace();
		}
		// 向公司-员工关系表中插入数据
		int l = cywDao.insertLastYearData(map);
		if (l <= 0) {
			new HesfException("向公司-员工关系表中插入数据		失败").printStackTrace();
		}
		return (j > 0 && k > 0 && l > 0) ? true : false;
	}

	@Override
	public PaginationRecordsAndNumber<Audit, Number> getByMultiCondition(Map<String, Object> map) {
		System.out.println("AuditServiceImpl		getByMultiCondition" + map);
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
		// 处理地区code,转化为适合sql语句的 xxxx 暂时不启用
		// if (map.get("areaCode") != null) {
		// map.put("areaCode",
		// KitService.areaCodeForSql(map.get("areaCode").toString()));
		// }
		if (map.get("overYear") != null) {
			int overYear = Integer.parseInt(map.get("overYear").toString());
			int currentYear = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")).get(Calendar.YEAR);
			map.put("overYear", currentYear - overYear);
		}
		// 将参数放入到map中
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Audit> list = acvDao.retrieveByCompany(map);
		// 数据条数
		int count = acvDao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Audit, Number> prn = new PaginationRecordsAndNumber<Audit, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

}
