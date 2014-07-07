package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.WorkerTempDao;
import com.esd.hesf.model.WorkerTemp;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.WorkerTempService;

/**
 * 残疾职工缓存service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class WorkerTempServiceImpl implements WorkerTempService {

	@Autowired
	private WorkerTempDao dao;

	@Override
	public boolean save(WorkerTemp t) {
		if (t == null) {
			new HesfException("workerTemp", HesfException.type_null)
					.printStackTrace();
		}
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return true;
	}

	@Override
	public boolean delete(Integer id) {
		if (id <= 0) {
			new HesfException(id, HesfException.type_number_negative)
					.printStackTrace();
		}
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(id, HesfException.type_fail).printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(WorkerTemp t) {
		if (t == null) {
			new HesfException("workerTemp", HesfException.type_null)
					.printStackTrace();
		}
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
		}
		return false;
	}

	@Override
	public WorkerTemp getByPrimaryKey(Integer id) {
		if (id <= 0) {
			new HesfException(id, HesfException.type_number_negative)
					.printStackTrace();
		}
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<WorkerTemp, Number> getPaginationRecords(
			WorkerTemp t, Integer page, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workerTemp", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<WorkerTemp> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<WorkerTemp, Number> prn = new PaginationRecordsAndNumber<WorkerTemp, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public Boolean deleteAllData() {
		return (dao.deleteAllData() > 0) ? true : false;
	}

	@Override
	public Boolean deleteByUserId(Integer userId) {
		return (dao.deleteByUserId(userId) > 0) ? true : false;
	}

	@Override
	public List<WorkerTemp> getByCheck(Boolean isOk, Integer userId) {
		WorkerTemp wt = new WorkerTemp();
		wt.setIsOk(isOk);
		wt.setUserId(userId);
		// wt.setCheckCode(checkCode);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workerTemp", wt);
		return dao.retrieveByCheck(map);
	}

	@Override
	public Integer getCountByCheck(Boolean isOk, Integer userId) {
		WorkerTemp wt = new WorkerTemp();
		wt.setIsOk(isOk);
		wt.setUserId(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workerTemp", wt);
		return dao.retrieveCountByCheck(map);
	}

	@Override
	public Integer getCountByworkerIdCard(String workerIdCard) {
		Integer result =dao.retrieveCountByworkerIdCard(workerIdCard);
		if(result == null || result <0){
			result =0;
		}
		return result;
	}
}
