package com.esd.hesf.service.impl;

import java.math.BigDecimal;
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
import com.esd.hesf.dao.PaymentDao;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.model.Payment;
import com.esd.hesf.service.AuditService;
import com.esd.hesf.service.Constants;

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

	// 付款表 dao接口
	@Autowired
	private PaymentDao pDao;

	@Override
	public boolean save(Audit t) {
		return dao.insertSelective(t) == 1 ? true : false;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(Audit t) {
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public Audit getByPrimaryKey(int id) {
		Audit audit = dao.retrieveByPrimaryKey(id);
		if (audit == null) {
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
		return audit;
	}

	@Override
	public Audit getByPrimaryKey(String year, String companyCode) {
		if (year == null || "".equals(year) || companyCode == null || "".equals(companyCode)) {
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
		Map<String, String> map = new HashMap<String, String>();
		map.put("currentYear", currentYear);
		map.put("lastYear", lastYear);
		// 向公司表里插入数据
		int k = cDao.insertLastYearData(map);
		// 向审核表里插入数据
		int j = dao.insertLastYearData(map);
		return (k > 0 && j > 0) ? true : false;
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

	@Override
	public BigDecimal getAlreadyPayByAudit(int auditId) {
		return pDao.retrieveAlreadyPayByAudit(auditId);
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecord(Integer auditId, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		Payment t = new Payment();
		t.setAuditId(auditId);
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = pDao.retrieveByPage(map);
		// 数据条数
		int count = pDao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}
}
