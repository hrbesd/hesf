package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.User;

/**
 * 用户 service接口
 * 
 * @author Administrator
 * 
 */
public interface UserService extends BaseService<User> {

	/**
	 * 获得所有用户数据---测试用
	 * 
	 * @return
	 */
	public List<User> getAll();

	/**
	 * 根据用户名查询对象
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);

	/**
	 * 根据手机号码查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	public User getUserByUserMobile(String mobile);

	/**
	 * 根据用户真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	public List<User> getUserByRealName(String realName);

	/**
	 * 获得所有缴款用户数据
	 * 
	 * @return
	 */
	public List<User> getPayer();

}
