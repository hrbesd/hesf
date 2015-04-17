package com.esd.hesf.service.impl;

import java.util.ArrayList;
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
	public Boolean save(Area t) {
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail);
			return false;
		}
		return true;
	}

	@Override
	public Boolean delete(Integer id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail);
			return false;
		}
		return true;
	}

	@Override
	public Boolean update(Area t) {
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
			new HesfException(this.getClass().getName(),
					HesfException.type_fail);
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Area, Number> getPaginationRecords(
			Area t, Integer page, Integer pageSize) {
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
			new HesfException("code", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		code = KitService.areaCodeForSql(code);
		return dao.retrieveByCode(code);
	}

	@Override
	public List<Area> getHeiLongjiangCities() {
		return dao.retrieveHLJ();
	}

	public Boolean updatedata() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", 0);
		// 返回量
		map.put("size", 99999);
		List<Area> list = dao.retrieveByPage(map);
		// System.out.println(list.size());

		// 修改直辖市的
		List<Area> l1 = new ArrayList<Area>();
		for (Area a : list) {
			if ("市辖区".equals(a.getName()) && a.getCode().endsWith("00")) {
				// System.out.println(a);
				l1.add(a);
			}
		}
		// System.out.println("l1.size():"+l1.size());
		for (Area a : l1) {
			String code1 = a.getCode();
			for (Area all : list) {
				if (all.getCode().equals("10" + code1.substring(2, 4) + "0000")) {
					a.setName(all.getName() + " " + a.getName());
					System.out.println("a: " + a + "all: " + all);

				}
			}
		}
		for (Area a : l1) {
			System.out.println(dao.updateByPrimaryKey(a));
		}

		// //修改普通市辖区的
		// List<Area> l1 = new ArrayList<Area>();
		// for(Area a:list){
		// if(a.getName().endsWith("市辖区") && a.getCode().endsWith("01")){
		// System.out.println(a);
		// l1.add(a);
		// }
		// }
		// // System.out.println("l1.size():"+l1.size());
		// int k=0;
		// for(Area a:l1){
		// String code1 = a.getCode();
		// for(Area all:list){
		// if(all.getCode().equals("20"+code1.substring(2, 6)+"00")){
		// a.setName(all.getName()+" "+a.getName());
		// // System.out.println("a: "+a+"all: "+all);
		// // System.out.println(++k);
		// }
		// }
		// }
		// for(Area a:l1){
		// System.out.println(dao.updateByPrimaryKey(a));
		// }

		return null;
	}

}
