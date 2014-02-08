package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.PaymentDao;
import com.esd.hesf.model.Payment;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.PaymentService;

/**
 * 
 * @author Administrator
 * 
 */
@Service
public class PaymentTypeServiceImpl implements PaymentService {

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

		return dao.insertSelective(t) == 1 ? true : false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(Payment t) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public Payment getByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return dao.retrieveByPrimaryKey(id);
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
	public List<Payment> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
