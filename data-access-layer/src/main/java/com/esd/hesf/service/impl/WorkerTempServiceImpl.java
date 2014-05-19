package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.WorkerTempDao;
import com.esd.hesf.model.WorkerTemp;
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
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAllData() {
		return (dao.deleteAllData() > 0) ? true : false;
	}

	@Override
	public List<WorkerTemp> getByCheck(Boolean isOk,Integer userId,String checkCode) {
		WorkerTemp wt = new WorkerTemp();
		wt.setIsOk(isOk);
		wt.setUserId(userId);
		wt.setCheckCode(checkCode);
		Map<String,Object> map = new HashMap<String,Object>();
		return dao.retrieveByCheck(map);
	}

	@Override
	public Integer getCountByCheck(Boolean isOk,Integer userId,String checkCode) {
		WorkerTemp wt = new WorkerTemp();
		wt.setIsOk(isOk);
		wt.setUserId(userId);
		wt.setCheckCode(checkCode);
		Map<String,Object> map = new HashMap<String,Object>();
		return dao.retrieveCountByCheck(map);
	}

}
