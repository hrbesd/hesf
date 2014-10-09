package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditLogDao;
import com.esd.hesf.model.AuditLog;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.AuditLogService;

/**
 * 残疾职工log信息 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	private AuditLogDao dao;

	@Override
	public boolean save(AuditLog t) {
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delete(Integer id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean update(AuditLog t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public AuditLog getByPrimaryKey(Integer id) {
		AuditLog t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<AuditLog, Number> getPaginationRecords(
			AuditLog t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditLog", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<AuditLog> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<AuditLog, Number> prn = new PaginationRecordsAndNumber<AuditLog, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

}
