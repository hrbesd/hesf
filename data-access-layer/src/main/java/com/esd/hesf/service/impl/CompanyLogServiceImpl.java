package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.CompanyLogDao;
import com.esd.hesf.model.CompanyLog;
import com.esd.hesf.service.CompanyLogService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 公司log信息 service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class CompanyLogServiceImpl implements CompanyLogService {

	@Autowired
	private CompanyLogDao dao;

	@Override
	public boolean save(CompanyLog t) {
		int i = dao.insertSelective(t);
		if (i != 1) {
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
	public boolean update(CompanyLog t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public CompanyLog getByPrimaryKey(Integer logId) {
		CompanyLog t = dao.retrieveByPrimaryKey(logId);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<CompanyLog, Number> getPaginationRecords(
			CompanyLog t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyLog", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<CompanyLog> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<CompanyLog, Number> prn = new PaginationRecordsAndNumber<CompanyLog, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}


}
