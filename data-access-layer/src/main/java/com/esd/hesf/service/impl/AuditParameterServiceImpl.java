package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditParameterDao;
import com.esd.hesf.dao.WorkerCalculatorDao;
import com.esd.hesf.dao.WorkerTempDao;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;

/**
 * 年审参数 servcie接口实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AuditParameterServiceImpl implements AuditParameterService {

	private static final Logger log = LoggerFactory
			.getLogger(AuditParameterServiceImpl.class);

	@Autowired
	private AuditParameterDao dao;

	@Autowired
	private WorkerCalculatorDao wcDao;
	
	@Override
	public Boolean save(AuditParameter t) {
		log.debug("t: " + t);
		if (t.getYear() == null) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		int k = dao.insert(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean delete(Integer id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean update(AuditParameter t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public AuditParameter getByPrimaryKey(Integer id) {
		AuditParameter t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<AuditParameter, Number> getPaginationRecords(
			AuditParameter t, Integer page, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditParameter", t);
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		map.put("size", pageSize);
		List<AuditParameter> list = dao.retrieveByPage(map);
		int count = dao.retrieveCount(map);
		PaginationRecordsAndNumber<AuditParameter, Number> prn = new PaginationRecordsAndNumber<AuditParameter, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<AuditParameter> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", Constants.START);
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	@Override
	public AuditParameter getByYear(String year) {
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		return dao.retrieveByYear(year);
	}

	@Override
	public List<WorkerCalculator> getSpecialSetting(String year) {
		// 如果year为空的话, 则查询当前年份的
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		return wcDao.retrieveSpecialSetting(year);
	}

	@Override
	public int getSpecialCount(String companyCode, String year,
			int workerHandicapType, int workerHandicapLevel) {
		if (companyCode == null || "".equals(companyCode)) {
			new HesfException("companyCode", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (workerHandicapType <= 0) {
			new HesfException("workerHandicapType",
					HesfException.type_number_negative).printStackTrace();
			return -1;
		}
		if (workerHandicapLevel <= 0) {
			new HesfException("workerHandicapLevel",
					HesfException.type_number_negative).printStackTrace();
			return -1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyCode", companyCode);
		map.put("year", year);
		map.put("workerHandicapType", workerHandicapType);
		map.put("workerHandicapLevel", workerHandicapLevel);
		return wcDao.retrieveSpecialCount(map);
	}

	@Override
	public int getSpecialCountFromWorkerTemp(Integer companyId, String year,
			int workerHandicapType, int workerHandicapLevel) {
		if (companyId == null || companyId <= 0) {
			new HesfException("companyId", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return -1;
		}
		if (workerHandicapType <= 0) {
			new HesfException("workerHandicapType",
					HesfException.type_number_negative).printStackTrace();
			return -1;
		}
		if (workerHandicapLevel <= 0) {
			new HesfException("workerHandicapLevel",
					HesfException.type_number_negative).printStackTrace();
			return -1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyId", companyId);
		map.put("year", year);
		map.put("workerHandicapType", workerHandicapType);
		map.put("workerHandicapLevel", workerHandicapLevel);
		return wcDao.retrieveSpecialCountFromWorkerTemp(map);
	}
	
	@Override
	public String getLastestYear() {
		return dao.retrieveLastestYear();
	}

	// 得到所有审核年度
	@Override
	public String[] getYears() {
		String[] arr = dao.retrieveYears();
		if (arr == null) {
			return null;
		}
		return arr;
	}

}
