package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.WorkerHandicapLevelDao;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.WorkerHandicapLevelService;

/**
 * 残疾等级 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class WorkerHandicapLevelServiceImpl implements WorkerHandicapLevelService {

	@Autowired
	private WorkerHandicapLevelDao dao;

	@Override
	public boolean save(WorkerHandicapLevel t) {
		int k = dao.insert(t);if(k!=1){new HesfException(t.getClass().getName(),HesfException.type_fail);return false;}return true;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(WorkerHandicapLevel t) {
		int k = dao.updateByPrimaryKey(t);if(k!=1){ new HesfException(t.getClass().getName(),HesfException.type_fail); return false; } return true;
	}

	@Override
	public WorkerHandicapLevel getByPrimaryKey(int id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<WorkerHandicapLevel, Number> getPaginationRecords(WorkerHandicapLevel t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workerHandicapLevel", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<WorkerHandicapLevel> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<WorkerHandicapLevel, Number> prn = new PaginationRecordsAndNumber<WorkerHandicapLevel, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<WorkerHandicapLevel> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
