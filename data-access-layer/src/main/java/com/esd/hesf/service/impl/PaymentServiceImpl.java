package com.esd.hesf.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.PaymentDao;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Payment;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.PaymentService;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDao dao;

	@Override
	public boolean save(Payment t) {
		if (t == null) {
			return false;
		}
		// 验证缴款人
		if (t.getPaymentPerson() == null) {
			return false;
		}
		// 验证缴款公司
		if (t.getPaymentCompany() == null) {
			return false;
		}
		if (t.getPaymentCompany().getId() == null) {
			return false;
		}
		// 验证缴款金额

		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Payment t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Payment getByPrimaryKey(int id) {
		Payment t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaginationRecords(Payment t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public BigDecimal getAlreadyPay(Integer auditId) {
		if (auditId == null || auditId <= 0) {
			new HesfException("auditId", HesfException.type_number_negative).printStackTrace();
			return null;
		}
		Payment payment = new Payment();
		payment.setAudit(new Audit(auditId));
		return dao.retrieveAlreadyPay(payment);
	}

	@Override
	public BigDecimal getAlreadyPay(String companyId) {
		if (companyId == null || "".equals(companyId)) {
			new HesfException("companyId", HesfException.type_null).printStackTrace();
			return null;
		}
		Payment payment = new Payment();
		payment.setPaymentCompany(new Company(companyId));
		return dao.retrieveAlreadyPay(payment);
	}

	@Override
	public BigDecimal getAlreadyPay(String year, String companyCode) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null).printStackTrace();
			return null;
		}
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null).printStackTrace();
			return null;
		}
		Payment payment = new Payment();
		payment.setPaymentCompany(new Company(year, companyCode));
		return dao.retrieveAlreadyPay(payment);
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecord(Integer auditId, Integer page, Integer pageSize) {
		if (auditId == null || auditId <= 0) {
			new HesfException("auditId", HesfException.type_number_negative).printStackTrace();
			return null;
		}
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		Payment t = new Payment();
		t.setAudit(new Audit(auditId));
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecord(String companyId, Integer page, Integer pageSize) {
		if (companyId == null || "".equals(companyId)) {
			new HesfException("companyId", HesfException.type_null).printStackTrace();
			return null;
		}
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		Payment t = new Payment();
		t.setPaymentCompany(new Company(companyId));
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public PaginationRecordsAndNumber<Payment, Number> getPaymentRecord(String year, String companyCode, Integer page, Integer pageSize) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null).printStackTrace();
			return null;
		}
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null).printStackTrace();
			return null;
		}
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		Payment t = new Payment();
		t.setPaymentCompany(new Company(year, companyCode));
		map.put("payment", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Payment> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Payment, Number> prn = new PaginationRecordsAndNumber<Payment, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<Payment> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
