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
		return dao.insertSelective(t) == 1 ? true : false;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(AuditProcessStatus t) {
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public AuditProcessStatus getByPrimaryKey(int id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<AuditProcessStatus, Number> getPaginationRecords(AuditProcessStatus t, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
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
