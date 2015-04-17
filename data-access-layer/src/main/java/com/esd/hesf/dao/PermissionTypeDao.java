package com.esd.hesf.dao;

import java.util.List;

import com.esd.hesf.model.PermissionType;

/**
 * 权限类别 dao接口
 * 
 * @author Administrator
 * 
 */
public interface PermissionTypeDao extends BaseDao<PermissionType> {

	/**
	 * 取得需要设置登录时间限定的权限类别 列表
	 * 
	 * @return
	 */
	List<PermissionType> retrieveneedsettime();
}