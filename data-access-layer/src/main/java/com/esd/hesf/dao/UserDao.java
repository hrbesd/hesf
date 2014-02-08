package com.esd.hesf.dao;

import java.util.List;

import com.esd.hesf.model.User;

/**
 * 用户 dao接口
 * 
 * @author Administrator
 * 
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 根据用户名查询用户对象是否存在
	 * 
	 * @param userName
	 * @return
	 */
	public User retrieveByUserName(String userName);

	/**
	 * 根据手机号码查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	public User retrieveByUserMobile(String mobile);

	/**
	 * 根据用户真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	public List<User> retrieveByRealName(String realName);

}