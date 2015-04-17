package com.esd.hesf.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditCompanyViewDao;
import com.esd.hesf.dao.AuditDao;
import com.esd.hesf.dao.AuditLogDao;
import com.esd.hesf.dao.CompanyDao;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.dao.UserDao;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.KitService;

/**
 * 审核service 实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AuditServiceImpl implements AuditService {

	public static final Logger log = LoggerFactory
			.getLogger(AuditServiceImpl.class);
	@Autowired
	private AuditDao dao;

	// 审核表视图 dao接口
	@Autowired
	private AuditCompanyViewDao acvDao;

	// 公司--员工--年份 关系表dao接口
	@Autowired
	private CompanyYearWorkerDao cywDao;

	@Autowired
	private UserDao uDao;

	@Autowired
	private CompanyDao cDao;

	@Autowired
	private AuditLogDao logDao;

	@Autowired
	private CompanyDao companyDao;

	@Override
	public Boolean save(Audit t) {
		if (t.getYear() == null || "".equals(t.getYear())) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompany() == null) {
			new HesfException("audit.company", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompany().getId() == null && t.getCompany().getId() <= 0) {
			new HesfException("audit.company.id", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		// 保存日志--暂时去掉
		// logDao.insertSelective(KitService.getLogObjectFromEntity(t));
		return true;
	}

	@Override
	public Boolean save(String year, String companyCode, Integer userId) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (userId == null || userId <= 0) {
			new HesfException("userId", HesfException.type_number_negative)
					.printStackTrace();
			return false;
		}
		// 先得到公司
		Company company = cDao.retrieveByCompanyCodeAndYear(new Company(
				companyCode, year));
		if (company == null) {
			new HesfException("公司不存在!").printStackTrace();
			return false;
		}
		// 验证那一年的审核数据是否已经存在
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("companyId", company.getId());
		Audit t = dao.retrieveByYearAndCompanyId(map);
		if (t != null) {
			log.debug("该公司 " + year + " 年的审核数据已经存在!");
			return false;
		}
		t = new Audit();
		t.setYear(year);
		t.setCompany(company);
		t.setAuditProcessStatus(new AuditProcessStatus(1));
		t.setUserId(userId);
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		// 保存日志--暂时去掉
		// logDao.insertSelective(KitService.getLogObjectFromEntity(t));
		return true;
	}

	@Override
	public Boolean delete(Integer id) {
		Audit t = dao.retrieveByPrimaryKey(id);
		t.setIsActive(true);
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
			return false;
		}
		// 保存日志
		logDao.insertSelective(KitService.getLogObjectFromEntity(t));
		return true;
	}

	@Override
	public Boolean update(Audit t) {
		if (t.getId() == null) {
			new HesfException("audit.id", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getYear() == null || "".equals(t.getYear())) {
			new HesfException("audit.company", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompany() == null) {
			new HesfException("audit.company", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompany().getId() == null && t.getCompany().getId() <= 0) {
			new HesfException("audit.company.id", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getAuditProcessStatus() == null) {
			new HesfException("audit.auditProcessStatus",
					HesfException.type_null).printStackTrace();
			return false;
		}
		if (t.getAuditProcessStatus().getId() == null) {
			new HesfException("audit.auditProcessStatus.id",
					HesfException.type_null).printStackTrace();
			return false;
		}
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		// 保存日志--暂时去掉
		// logDao.insertSelective(KitService.getLogObjectFromEntity(t));
		return true;
	}

	@Override
	public Audit getByPrimaryKey(Integer id) {
		Audit audit = dao.retrieveByPrimaryKey(id);
		if (audit == null) {
			new HesfException("没有找到对应id的Audit对象", HesfException.type_no_data)
					.printStackTrace();
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
		audit.setCompanyHandicapTotal(companyHandicapTotal);
		// 查询初审人对象
		if (audit.getInitAuditUser() != null) {
			if (audit.getInitAuditUser().getId() != null
					&& audit.getInitAuditUser().getId() > 0) {
				audit.setInitAuditUser(uDao.retrieveByPrimaryKey(audit
						.getInitAuditUser().getId()));
			}
		}
		// 查询复审人对象
		if (audit.getVerifyAuditUser() != null) {
			if (audit.getVerifyAuditUser().getId() != null
					&& audit.getVerifyAuditUser().getId() > 0) {
				audit.setVerifyAuditUser(uDao.retrieveByPrimaryKey(audit
						.getVerifyAuditUser().getId()));
			}
		}
		return audit;
	}

	@Override
	public Audit getByPrimaryKey(String year, Integer companyId) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("companyCode", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("companyId", companyId);
		Audit audit = dao.retrieveByYearAndCompanyId(map);
		return audit;
	}

	@Override
	public PaginationRecordsAndNumber<Audit, Number> getPaginationRecords(
			Audit t, Integer page, Integer pageSize) {
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
	public Boolean initAuditData(String year, Integer logUserId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 当前审核年度
		paramMap.put("thisYear", year);
		// 去年审核年度
		Integer currentYear = Integer.parseInt(year);
		paramMap.put("lastYear", (currentYear - 1));
		// log操作人id
		paramMap.put("userId", logUserId);
		// ① 初始化 企业数据
		Integer result1 = companyDao.insertLastYearData(paramMap);
		// ②初始化审核数据
		Integer result2 = dao.insertLastYearData(paramMap);
//		// ③初始化公司-员工关系数据
//		Integer result3 = cywDao.insertLastYearData(paramMap);
		if (result1 < 0 || result2 < 0) {
			new HesfException("初始化 审核表数据 	*****失败*****").printStackTrace();
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public PaginationRecordsAndNumber<Audit, Number> getByMultiCondition(
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
		// 处理地区code,转化为适合sql语句的 xxxx 暂时不启用
		// if (map.get("areaCode") != null) {
		// map.put("areaCode",
		// KitService.areaCodeForSql(map.get("areaCode").toString()));
		// }
		if (map.get("overYear") != null) {
			int overYear = Integer.parseInt(map.get("overYear").toString());
			int currentYear = Calendar.getInstance(
					TimeZone.getTimeZone("GMT+08:00")).get(Calendar.YEAR);
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

	@Override
	public String[] getAuditYears() {
		return dao.retrieveAuditYears();
	}

	@Override
	public Integer getCountByYear(String year) {
		if (year == null) {
			return null;
		}
		return dao.retrieveCountByYear(year);
	}

	@Override
	public List<Audit> getByIds(Integer[] ids) {
		if (ids == null) {
			return null;
		}
		if (ids.length < 1) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.ARRAY, ids);
		return dao.retrieveByPrimaryKeys(map);
	}

	@Override
	public PaginationRecordsAndNumber<Audit, Number> getRepealPredict(
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
		// 处理地区code,转化为适合sql语句的 xxxx 暂时不启用
		// if (map.get("areaCode") != null) {
		// map.put("areaCode",
		// KitService.areaCodeForSql(map.get("areaCode").toString()));
		// }
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

	@Override
	public PaginationRecordsAndNumber<Audit, Number> getHistory(
			Integer companyId, String year) {
		if (companyId == null || year == "") {
			return null;
		}
		// 将参数放入到audit对象中
		Audit audit = new Audit();
		audit.setCompany(new Company(companyId));
		audit.setYear(year);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("audit", audit);
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		// 返回的数据
		List<Audit> list = dao.retrieveHistory(map);
		// 数据条数
		int count = dao.retrieveHistoryCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Audit, Number> prn = new PaginationRecordsAndNumber<Audit, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

}
