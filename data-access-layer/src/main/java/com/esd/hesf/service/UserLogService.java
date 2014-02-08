package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.UserLog;

/**
 * 用户 service接口
 * 
 * @author Administrator
 * 
 */
public interface UserLogService extends BaseService<UserLog> {

	public List<UserLog> getAll();
}
