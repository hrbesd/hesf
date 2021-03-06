package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.PaymentExceptionalDao;
import com.esd.hesf.model.PaymentExceptional;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.PaymentExceptionalService;

@Service
public class PaymentExceptionalServiceImpl implements PaymentExceptionalService {

	@Autowired
	private PaymentExceptionalDao dao;

	@Override
	public boolean save(PaymentExceptional t) {
		int k = dao.insert(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Integer id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(PaymentExceptional t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public PaymentExceptional getByPrimaryKey(Integer id) {
		PaymentExceptional t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<PaymentExceptional, Number> getPaginationRecords(PaymentExceptional t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentExceptional", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<PaymentExceptional> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<PaymentExceptional, Number> prn = new PaginationRecordsAndNumber<PaymentExceptional, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<PaymentExceptional> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
