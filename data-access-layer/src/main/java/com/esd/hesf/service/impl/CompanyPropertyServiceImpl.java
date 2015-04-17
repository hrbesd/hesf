package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.CompanyPropertyDao;
import com.esd.hesf.model.CompanyProperty;
import com.esd.hesf.service.CompanyPropertyService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 公司属性 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class CompanyPropertyServiceImpl implements CompanyPropertyService {

	private static final Logger log = LoggerFactory.getLogger(CompanyPropertyServiceImpl.class);

	@Autowired
	private CompanyPropertyDao dao;

	@Override
	public Boolean save(CompanyProperty t) {
		int k = dao.insert(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean update(CompanyProperty t) {
		// TODO Auto-generated method stub
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public CompanyProperty getByPrimaryKey(Integer id) {
		CompanyProperty t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<CompanyProperty, Number> getPaginationRecords(CompanyProperty t, Integer page, Integer pageSize) {
		log.debug(t.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyproperty", t);
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		map.put("size", pageSize);
		List<CompanyProperty> list = dao.retrieveByPage(map);
		int count = dao.retrieveCount(map);
		PaginationRecordsAndNumber<CompanyProperty, Number> prn = new PaginationRecordsAndNumber<CompanyProperty, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<CompanyProperty> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", Constants.START);
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
