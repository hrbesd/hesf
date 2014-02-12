package com.esd.hesf.service.impl;

import java.util.Calendar;
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
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.WorkerCalculator;
import com.esd.hesf.service.AuditParameterService;
import com.esd.hesf.service.Constants;

/**
 * 年审参数 servcie接口实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class AuditParameterServiceImpl implements AuditParameterService {

	private static final Logger log = LoggerFactory.getLogger(AuditParameterServiceImpl.class);

	@Autowired
	private AuditParameterDao dao;

	@Autowired
	private WorkerCalculatorDao wcDao;

	@Override
	public boolean save(AuditParameter t) {
		return dao.insert(t) == 1 ? true : false;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(AuditParameter t) {
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public AuditParameter getByPrimaryKey(int id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<AuditParameter, Number> getPaginationRecords(AuditParameter t, Integer page, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("auditparameter", t);
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
			return null;
		}
		return dao.retrieveByYear(year);
	}

	@Override
	public List<WorkerCalculator> getSpecialSetting(String year) {
		//如果year为空的话, 则查询当前年份的
		if(year == null ||"".equals(year)){
			Calendar cal = Calendar.getInstance();
			year = String.valueOf(cal.get(Calendar.YEAR));
		}
		return wcDao.retrieveSpecialSetting(year);
	}

	@Override
	public int getSpecialCount(String companyCode, String year, int workerHandicapType, int workerHandicapLevel) {
		if (companyCode == null || "".equals(companyCode)) {
			return -1;
		}
		if (year == null || "".equals(year)) {
			return -1;
		}
		if (workerHandicapType <= 0) {
			return -1;
		}
		if (workerHandicapLevel <= 0) {
			return -1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("companyCode", companyCode);
		map.put("year", year);
		map.put("workerHandicapType", workerHandicapType);
		map.put("workerHandicapLevel", workerHandicapLevel);
		return wcDao.retrieveSpecialCount(map);
	}

}
