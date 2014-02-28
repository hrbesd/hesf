package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.UserLogDao;
import com.esd.hesf.model.UserLog;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.UserLogService;

/**
 * 用户service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class UserLogServiceImpl implements UserLogService {

	@Autowired
	private UserLogDao dao;

	@Override
	public boolean save(UserLog t) {
		int k = dao.insert(t);if(k!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(UserLog t) {
		// TODO Auto-generated method stub
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public UserLog getByPrimaryKey(int id) {
		// TODO Auto-generated method stub
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<UserLog, Number> getPaginationRecords(UserLog t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userLog", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<UserLog> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<UserLog, Number> prn = new PaginationRecordsAndNumber<UserLog, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<UserLog> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
