package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.UserDao;
import com.esd.hesf.model.User;
import com.esd.hesf.model.UserGroup;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.UserService;

/**
 * 用户service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	// @Autowired
	// private PermissionTypeDao ptDao;
	//
	// @Autowired
	// private UserGroupDao ugDao;

	@Override
	public boolean save(User t) {
		int k = dao.insertSelective(t);if(k!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(User t) {
		int k = dao.updateByPrimaryKey(t);if(k!=1){ new HesfException(t.getClass().getName(),HesfException.type_fail); return false; } return true;
	}

	@Override
	public User getByPrimaryKey(int id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<User, Number> getPaginationRecords(User t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<User> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<User, Number> prn = new PaginationRecordsAndNumber<User, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<User> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	@Override
	public User getUserByUserName(String userName) {
		// 获得user对象
		return dao.retrieveByUserName(userName);
	}

	@Override
	public User getUserByUserMobile(String userMobile) {
		// 获得user对象
		return dao.retrieveByUserMobile(userMobile);
	}

	@Override
	public List<User> getUserByRealName(String realName) {
		// 获得user对象
		return dao.retrieveByRealName(realName);
	}

	@Override
	public List<User> getPayer() {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		user.setUserGroup(new UserGroup(4));
		map.put("user", user);
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
