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

	/**
	 * 查询所有权限列表
	 * @return
	 */
	List<PermissionType> getAll();

	/**
	 * 取得需要设置登录时间限定的权限类别 列表
	 * @return
	 */
	List<PermissionType> getNeedSetTime();

}
