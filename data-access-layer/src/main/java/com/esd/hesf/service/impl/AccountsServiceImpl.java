package com.esd.hesf.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AccountsDao;
import com.esd.hesf.model.Accounts;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.model.Company;
import com.esd.hesf.service.AccountsService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class AccountsServiceImpl implements AccountsService {

	@Autowired
	private AccountsDao dao;

	@Override
	public boolean save(Accounts t) {
		if (t == null) {
			new HesfException("accounts对象", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getYear() == null) {
			new HesfException("审核年度year", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompany() == null) {
			new HesfException("company对象", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getCompany().getId() == null || t.getCompany().getId() <= 0) {
			new HesfException("company.id", HesfException.type_null)
					.printStackTrace();
		}
		if (t.getAudit() == null) {
			new HesfException("audit对象", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (t.getAudit().getId() == null || t.getAudit().getId() <= 0) {
			new HesfException("audit.id", HesfException.type_null)
					.printStackTrace();
		}
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

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
	public boolean update(Accounts t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Accounts getByPrimaryKey(Integer id) {
		Accounts t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public Accounts getByYearAndCompany(String year, Integer companyId) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
		}
		Accounts t = new Accounts();
		t.setYear(year);
		t.setCompany(new Company(companyId));
		t = dao.retrieveByCompanyGroup(t);
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Accounts, Number> getPaginationRecords(
			Accounts t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accounts", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Accounts> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Accounts, Number> prn = new PaginationRecordsAndNumber<Accounts, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<Accounts> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	@Override
	public List<Accounts> getCompanyAccount(String year, Integer companyId) {
		Accounts t = new Accounts();
		t.setYear(year);
		t.setCompany(new Company(companyId));
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accounts", t);
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		// 返回的数据
		List<Accounts> list = dao.retrieveByPage(map);
		return list;
	}

	@Override
	public List<Accounts> getCompanyAccount(String year, String companyCode) {
		Accounts t = new Accounts();
		t.setYear(year);
		t.setCompany(new Company(companyCode));
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accounts", t);
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		// 返回的数据
		List<Accounts> list = dao.retrieveByPage(map);
		return list;
	}

	@Override
	public String[] getAccountsYears() {
		return dao.retrieveAccountsYears();
	}

	@Override
	public List<Accounts> getByYearAndCompany(String year, Integer companyId,
			Integer auditProcessStatus) {
		if (auditProcessStatus == null || auditProcessStatus <= 0) {
			new HesfException("auditProcessStatus", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		Accounts accounts = new Accounts();
		accounts.setYear(year);
		accounts.setCompany(new Company(companyId));
		accounts.setAuditProcessStatus(new AuditProcessStatus(
				auditProcessStatus));
		return dao.retrieveByAccounts(accounts);
	}

	@Override
	public PaginationRecordsAndNumber<Accounts, Number> getPaginationRecordsGroupByCompany(
			Accounts t, String belongsType, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accounts", t);
		//所属类型--是否以6字开头
		map.put(Constants.BELONGS_TYPE, belongsType);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Accounts> list = dao.retrievePageByCompanyGroup(map);
		// 数据条数
		int count = dao.retrievePageByCompanyGroupCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Accounts, Number> prn = new PaginationRecordsAndNumber<Accounts, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public String[] getAuditYears(String accountsYear, Integer companyId) {
		if (accountsYear == null || "".equals(accountsYear)) {
			new HesfException("accountsYear", HesfException.type_null)
					.printStackTrace();
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountsYear", accountsYear);
		map.put("companyId", companyId);
		return dao.retrieveAuditYears(map);
	}

	@Override
	public BigDecimal getCompanyAuditYear(String auditYear,
			String accountsYear, Integer companyId) {
		if (accountsYear == null || "".equals(accountsYear)) {
			new HesfException("accountsYear", HesfException.type_null)
					.printStackTrace();
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
		}
		Accounts ac = new Accounts();
		ac.setAudit(new Audit(auditYear));
		ac.setYear(accountsYear);
		ac.setCompany(new Company(companyId));
		return dao.retrieveByCompanyAuditYear(ac);
	}

	@Override
	public Accounts getOneByCompanyAuditYear(String auditYear,
			String accountsYear, String companyCode) {
		if (auditYear == null || "".equals(auditYear)) {
			new HesfException("auditYear", HesfException.type_null)
					.printStackTrace();
		}
		if (accountsYear == null || "".equals(accountsYear)) {
			new HesfException("accountsYear", HesfException.type_null)
					.printStackTrace();
		}
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null)
					.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditYear", auditYear);
		map.put("accountsYear", accountsYear);
		map.put("companyCode", companyCode);
		return dao.retrieveOneByCompanyAuditYear(map);
	}

	@Override
	public Accounts getOneByCompanyAuditYear(String auditYear,
			String accountsYear, Integer companyId) {
		if (auditYear == null || "".equals(auditYear)) {
			new HesfException("auditYear", HesfException.type_null)
					.printStackTrace();
		}
		if (accountsYear == null || "".equals(accountsYear)) {
			new HesfException("accountsYear", HesfException.type_null)
					.printStackTrace();
		}
		if (companyId == null || companyId <= 0) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditYear", auditYear);
		map.put("accountsYear", accountsYear);
		map.put("companyId", companyId);
		return dao.retrieveOneByCompanyAuditYear(map);
	}

}
