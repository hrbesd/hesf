package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.PermissionType;

/**
 * 权限类别 service接口
 * 
 * @author Administrator
 * 
 */
public interface PermissionTypeService extends BaseService<PermissionType> {

	/*
	 * 8 查询所有权限列表
	 */
	List<PermissionType> getAll();

}
