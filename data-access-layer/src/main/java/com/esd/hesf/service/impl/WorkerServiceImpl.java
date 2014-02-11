package com.esd.hesf.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.CompanyWorkerViewDao;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.dao.WorkerDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.model.Worker;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.KitService;
import com.esd.hesf.service.WorkerService;
import com.esd.hesf.viewmodels.WorkerViewModel;

/**
 * 残疾职工service实现类
 * 
 * @author Administrator
 * 
 */
@Service
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	private WorkerDao dao;

	@Autowired
	private CompanyYearWorkerDao cywDao;

	@Autowired
	private CompanyWorkerViewDao cwvDao;

	@Override
	public boolean save(Worker t) {
		t.setArea(new Area("10230000"));
		return dao.insertSelective(t) == 1 ? true : false;
	}

	@Override
	public boolean save(Worker worker, String companyCode) {
		worker.setArea(new Area("10230000"));
		// ①保存到职工表
		int i = dao.insertSelective(worker);
		if (i != 1) {
			return false;
		}
		// ②获得当前年份
		// 获得当前年份
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		// ③保存到企业--职工 关系表
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setWorkerId(worker.getId());
		cyw.setCompanyCode(companyCode);
		cyw.setYear(String.valueOf(year));
		cyw.setCurrentJob(worker.getCurrentJob());
		cyw.setUserId(worker.getUserId());
		int k = cywDao.insertSelective(cyw);
		if (k == 1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		return dao.deleteByPrimaryKey(id) == 1 ? true : false;
	}

	@Override
	public boolean update(Worker t) {
		return dao.updateByPrimaryKey(t) == 1 ? true : false;
	}

	@Override
	public Worker getByPrimaryKey(int id) {
		return dao.retrieveByPrimaryKey(id);
	}

	@Override
	public PaginationRecordsAndNumber<Worker, Number> getPaginationRecords(Worker t, Integer page, Integer pageSize) {
		// 处理地区code,转化为适合sql语句的 xxxx 暂时不启用
		// if (t != null) {
		// if (t.getArea() != null) {
		// if (t.getArea().getCode() != null &&
		// !"".equals(t.getArea().getCode())) {
		// t.getArea().setCode(KitService.areaCodeForSql(t.getArea().getCode()));
		// }
		// }
		// }
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("worker", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<Worker> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Worker, Number> prn = new PaginationRecordsAndNumber<Worker, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<Worker> getAll(int size) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	@Override
	public PaginationRecordsAndNumber<Worker, Number> getPaginationRecords(Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		int page = 1;
		int pageSize = Constants.SIZE;
		if (map.get("page") != null) {
			page = Integer.parseInt(map.get("page").toString());
		}
		if (map.get("pageSize") != null) {
			pageSize = Integer.parseInt(map.get("pageSize").toString());
		}
		// 最小年龄转化为对应的出生年月
		if (map.get("minAge") != null) {
			map.put("minBirth", KitService.getBirthFromAge(map.get("minAge").toString()));
		}
		// 最大年龄转化为对应的出生年月
		if (map.get("maxAge") != null) {
			map.put("maxBirth", KitService.getBirthFromAge(map.get("maxAge").toString()));
		}
		// 将参数放入到map中
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		List<Worker> list = cwvDao.retrieveWorkerByCompany(map);
		// 数据条数
		int count = cwvDao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Worker, Number> prn = new PaginationRecordsAndNumber<Worker, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public PaginationRecordsAndNumber<WorkerViewModel, Number> getByMultiCondition(Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		int page = 1;
		int pageSize = Constants.SIZE;
		if (map.get("page") != null) {
			page = Integer.parseInt(map.get("page").toString());
		}
		if (map.get("pageSize") != null) {
			pageSize = Integer.parseInt(map.get("pageSize").toString());
		}
		// 处理地区code,转化为适合sql语句的 xxxx 暂时不启用
		// if (map.get("areaCode") != null) {
		// map.put("areaCode",
		// KitService.areaCodeForSql(map.get("areaCode").toString()));
		// }
		// 最小年龄转化为对应的出生年月
		if (map.get("minAge") != null) {
			map.put("minBirth", KitService.getBirthFromAge(map.get("minAge").toString()));
		}
		// 最大年龄转化为对应的出生年月
		if (map.get("maxAge") != null) {
			map.put("maxBirth", KitService.getBirthFromAge(map.get("maxAge").toString()));
		}
		// 将参数放入到map中
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<WorkerViewModel> list = cwvDao.retrieveWorkerByWorker(map);
		// 数据条数
		int count = cwvDao.retrieveWorkerByWorkerCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<WorkerViewModel, Number> prn = new PaginationRecordsAndNumber<WorkerViewModel, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public Company retrieveCompanyByWorker(String year, String workerIdCard) {
		if (year == null || workerIdCard == null || "".equals(year) || "".equals(workerIdCard)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("yead", year);
		map.put("workerIdCard", workerIdCard);
		return cwvDao.retrieveCompanyByWorker(map);
	}

	@Override
	public Worker getByWorkerIdCard(String workerIdCard) {
		Worker worker = dao.retrieveByWorkerIdCard(workerIdCard);
		return worker;
	}

	@Override
	public Worker getByWorkerHandicapCode(String workerHandicapCode) {
		Worker worker = dao.retrieveByWorkerHandicapCode(workerHandicapCode);
		return worker;
	}

	@Override
	public boolean changeCompany(int workerId, String targetCompanyCode, String currentYear, String currentJob) {
		if (workerId <= 0 || targetCompanyCode == null || "".equals(targetCompanyCode) || currentYear == null || "".equals(currentYear)) {
			return false;
		}
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setWorkerId(workerId);
		cyw.setCompanyCode(targetCompanyCode);
		cyw.setYear(currentYear);
		cyw.setCurrentJob(currentJob);
		return cywDao.insertSelective(cyw) == 1 ? true : false;
	}

}
