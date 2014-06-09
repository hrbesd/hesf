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
	List<User> getAll();

	/**
	 * 根据用户名查询对象
	 * 
	 * @param userName
	 * @return
	 */
	User getUserByUserName(String userName);

	/**
	 * 根据手机号码查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	User getUserByUserMobile(String mobile);

	/**
	 * 根据用户真实姓名查询用户
	 * 
	 * @param realName
	 * @return
	 */
	List<User> getUserByRealName(String realName);

	/**
	 * 根据用户组类型id, 得到该用户组的所有成员
	 * 
	 * @return
	 */
	List<User> getUserByGroup(Integer userGroupId);

}
