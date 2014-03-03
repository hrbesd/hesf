package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditProcessStatusDao;
import com.esd.hesf.model.AuditProcessStatus;
import com.esd.hesf.service.AuditProcessStatusService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 审核进程状态 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AuditProcessStatusServiceImpl implements AuditProcessStatusService {

	@Autowired
	private AuditProcessStatusDao dao;

	@Override
	public boolean save(AuditProcessStatus t) {
		int k = dao.insert(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(int id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(AuditProcessStatus t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public AuditProcessStatus getByPrimaryKey(int id) {
		AuditProcessStatus t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(), HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<AuditProcessStatus, Number> getPaginationRecords(AuditProcessStatus t, Integer page, Integer pageSize) {
		return null;
	}

	@Override
	public List<AuditProcessStatus> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

}
