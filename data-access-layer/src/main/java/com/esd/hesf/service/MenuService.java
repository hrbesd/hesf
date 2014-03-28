package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.Menu;

public interface MenuService extends BaseService<Menu> {

	/**
	 * 获得树形结构菜单数据
	 * 
	 * @return
	 */
	List<Menu> getTreeMap();

	/**
	 * 根据权限值获得菜单列表
	 */
	List<Menu> getByPermissionType(int pid);

}
