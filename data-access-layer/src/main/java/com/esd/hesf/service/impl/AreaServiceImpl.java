package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AreaDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.service.AreaService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.KitService;

/**
 * 地区service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao dao;

	@Override
	public boolean save(Area t) {
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail);
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Integer id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(), HesfException.type_fail);
			return false;
		}
		return true;
	}

	@Override
	public boolean update(Area t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail);
			return false;
		}
		return true;
	}

	@Override
	public Area getByPrimaryKey(Integer id) {
		Area t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(), HesfException.type_fail);
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Area, Number> getPaginationRecords(Area t, Integer page, Integer pageSize) {
		return null;
	}

	@Override
	public List<Area> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", Constants.START);
		map.put("size", 9999);
		return dao.retrieveByPage(map);
	}

	@Override
	public List<Area> getSubArea(String code) {
		if (code == null || "".equals(code)) {
			new HesfException("code", HesfException.type_null).printStackTrace();
			return null;
		}
		code = KitService.areaCodeForSql(code);
		return dao.retrieveByCode(code);
	}

	@Override
	public List<Area> getHeiLongjiangCities() {
		return dao.retrieveHLJ();
	}

}
