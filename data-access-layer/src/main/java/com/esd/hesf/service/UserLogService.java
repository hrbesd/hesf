package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.UserLog;

/**
 * 用户log信息 service接口
 * 
 * @author Administrator
 * 
 */
public interface UserLogService extends BaseService<UserLog> {

	/**
	 * 获得所有数据--测试用
	 * 
	 * @return
	 */
	List<UserLog> getAll();
}
