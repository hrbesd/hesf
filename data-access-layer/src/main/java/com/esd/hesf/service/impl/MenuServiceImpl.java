package com.esd.hesf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.MenuDao;
import com.esd.hesf.model.Attribute;
import com.esd.hesf.model.Menu;
import com.esd.hesf.model.PermissionType;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	private MenuDao dao;

	// 获得树形结构菜单数据
	@Override
	public List<Menu> getTreeMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", 0);
		map.put("size", 999);
		List<Menu> list = dao.retrieveByPage(map);
		return changeToTree(list);
	}

	@Override
	public boolean save(Menu t) {
		int k = dao.insertSelective(t);if(k!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(Menu t) {
		int k = dao.updateByPrimaryKey(t);if(k!=1){ new HesfException(t.getClass().getName(),HesfException.type_fail); return false; } return true;
	}

	@Override
	public Menu getByPrimaryKey(int id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<Menu, Number> getPaginationRecords(Menu t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menu", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Menu> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Menu, Number> prn = new PaginationRecordsAndNumber<Menu, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<Menu> getByPermissionType(int pid) {
		// 设定参数
		Menu menu = new Menu();
		menu.setPermissionType(new PermissionType(pid));
		// 放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menu", menu);
		map.put("start", 0);
		map.put("size", 999);
		List<Menu> list = dao.retrieveByPage(map);
		return changeToTree(list);
	}

	private List<Menu> changeToTree(List<Menu> list) {
		if (list == null) {
			return null;
		}
		log.debug("list.size() : " + list.size());
		// 找出所有一级菜单
		List<Menu> lv1 = new ArrayList<Menu>();
		for (Menu m : list) {
			String id = m.getId();
			if (id.startsWith("10")) {
				lv1.add(m);
			}
		}
		log.debug("lv1 : " + lv1.size());
		// 找出所有二级菜单
		List<Menu> lv2 = new ArrayList<Menu>();
		for (Menu m : list) {
			String id = m.getId();
			if (id.startsWith("20")) {
				lv2.add(m);
			}
		}
		log.debug("lv2 : " + lv2.size());
		// 找出所有三级菜单
		List<Menu> lv3 = new ArrayList<Menu>();
		for (Menu m : list) {
			String id = m.getId();
			if (id.startsWith("30")) {
				m.setAttributes(new Attribute(m.getUrl()));
				lv3.add(m);
			}
		}
		log.debug("lv3 : " + lv3.size());
		// 将所有三级菜单归纳到对应的二级菜单里
		for (Menu m2 : lv2) {
			for (Menu m3 : lv3) {
				if (m3.getId().substring(2, 6).equals(m2.getId().substring(2, 6))) {
					log.debug("lv3 m3 :" + m3);
					m2.add(m3);
				}
			}
		}
		// //将所有二级菜单归纳到对应的一级菜单里
		for (Menu m1 : lv1) {
			for (Menu m2 : lv2) {
				if (m2.getId().substring(2, 4).equals(m1.getId().substring(2, 4))) {
					m1.add(m2);
				}
			}
		}
		// 返回带有子菜单的一级菜单
		return lv1;
	}
}
