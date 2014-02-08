package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.UserGroup;

/**
 * 用户组 service接口
 * 
 * @author Administrator
 * 
 */
public interface UserGroupService extends BaseService<UserGroup> {

	// 获得说有数据
	public List<UserGroup> getAll();
}
