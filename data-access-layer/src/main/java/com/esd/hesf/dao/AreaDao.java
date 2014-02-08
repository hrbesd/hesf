package com.esd.hesf.dao;

import java.util.List;

import com.esd.hesf.model.Area;

/**
 * 地区dao接口
 * 
 * @author Administrator
 * 
 */
public interface AreaDao extends BaseDao<Area> {

	// 根据地区code查询相关联的一组地区
	List<Area> retrieveByCode(String code);

	// 查询黑龙江地区省市列表
	List<Area> retrieveHLJ();
}