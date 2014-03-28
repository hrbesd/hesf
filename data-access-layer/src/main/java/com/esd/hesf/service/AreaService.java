package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.model.Area;

/**
 * 地区service接口
 * 
 * @author Administrator
 * 
 */
public interface AreaService extends BaseService<Area> {

	/**
	 * 获得所有地区数据
	 * 
	 * @return
	 */
	List<Area> getAll();

	/**
	 * 根据地区code, 查询他所下属的所有地区
	 * 
	 * @param code
	 * @return
	 * @throws HesfException
	 */
	List<Area> getSubArea(String code);

	/**
	 * 查询黑龙江地区省市列表
	 * 
	 * @return
	 */
	List<Area> getHeiLongjiangCities();
}
