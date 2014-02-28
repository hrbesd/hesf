package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.CompanyEconomyTypeDao;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.service.CompanyEconomyTypeService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 公司类型 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class CompanyEconomyTypeServiceImpl implements CompanyEconomyTypeService {

	private static final Logger log = LoggerFactory.getLogger(CompanyEconomyTypeServiceImpl.class);

	@Autowired
	private CompanyEconomyTypeDao dao;

	@Override
	public boolean save(CompanyEconomyType t) {
		int k = dao.insert(t);if(k!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(CompanyEconomyType t) {
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public CompanyEconomyType getByPrimaryKey(int id) {
		// 此处可以编写相应的逻辑代码
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<CompanyEconomyType, Number> getPaginationRecords(CompanyEconomyType t, Integer page, Integer pageSize) {
		log.debug(t.toString());
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyEconomyType", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<CompanyEconomyType> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<CompanyEconomyType, Number> prn = new PaginationRecordsAndNumber<CompanyEconomyType, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<CompanyEconomyType> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
