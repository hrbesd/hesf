package com.esd.hesf.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditParameterDao;
import com.esd.hesf.dao.CompanyWorkerViewDao;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.dao.WorkerDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.model.Worker;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
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

	@Autowired
	private AuditParameterDao apDao;

	@Override
	public boolean save(Worker t) {
		if (t.getArea() == null) {
			t.setArea(new Area("10230000"));
		}
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean save(Worker worker, Integer companyId, String year) {
		// ①保存到职工表
		int i = dao.insertSelective(worker);
		if (i != 1) {
			return false;
		}
		// ②保存到企业--职工 关系表
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setWorkerId(worker.getId());
		cyw.setCompanyId(companyId);
		cyw.setYear(year);
		cyw.setCurrentJob(worker.getCurrentJob());
		cyw.setUserId(worker.getUserId());
		int k = cywDao.insertSelective(cyw);
		if (k == 1) {
			return true;
		}
		return false;
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
	public boolean update(Worker t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Worker getByPrimaryKey(Integer id) {
		Worker t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public PaginationRecordsAndNumber<Worker, Number> getPaginationRecords(
			Worker t, Integer page, Integer pageSize) {
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
	public PaginationRecordsAndNumber<Worker, Number> getPaginationRecords(
			Map<String, Object> map) {
		if (map == null) {
			new HesfException("map参数", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		if (map.get("year") == null) {
			new HesfException("map--year", HesfException.type_null)
					.printStackTrace();
			return null;
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
			map.put("minBirth",
					KitService.getBirthFromAge(map.get("minAge").toString()));
		}
		// 最大年龄转化为对应的出生年月
		if (map.get("maxAge") != null) {
			map.put("maxBirth",
					KitService.getBirthFromAge(map.get("maxAge").toString()));
		}
		// 是否获得全部达到退休年龄人员
		if (map.get("getOverproof") != null) {
			Boolean bl = Boolean.parseBoolean(map.get("getOverproof")
					.toString());
			if (bl) {
				String year = map.get("year").toString();
				// 得到对应年份的参数
				AuditParameter ap = apDao.retrieveByYear(year);
				// 计算 如果达到退休
				// 男女职工各自的最大出生日期
				String maxMaleBirth = KitService.getBirthFromAge(ap
						.getRetireAgeMale() + "");
				String maxFemaleBirth = KitService.getBirthFromAge(ap
						.getRetireAgeFemale() + "");
				// 男女干部各自的最大出生日期
				String maxMaleCadreBirth = KitService.getBirthFromAge(ap
						.getRetireAgeCadreMale() + "");
				String maxFemaleCadreBirth = KitService.getBirthFromAge(ap
						.getRetireAgeCadreFemale() + "");
				map.put("maxMaleBirth", maxMaleBirth);
				map.put("maxMaleCadreBirth", maxMaleCadreBirth);
				map.put("maxFemaleBirth", maxFemaleBirth);
				map.put("maxFemaleCadreBirth", maxFemaleCadreBirth);
			}
		}
		// 是否是干部
		if (map.get("isCadre") != null) {
			Boolean bl = Boolean.parseBoolean(map.get("isCadre").toString());
			map.put("isCadre", bl);
		}
		// 将参数放入到map中
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		List<Worker> list = cwvDao.retrieveWorkerByCompany(map);
		// 数据条数
		int count = cwvDao.retrieveWorkerByCompanyCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<Worker, Number> prn = new PaginationRecordsAndNumber<Worker, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public PaginationRecordsAndNumber<WorkerViewModel, Number> getByMultiCondition(
			Map<String, Object> map) {
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
			map.put("minBirth",
					KitService.getBirthFromAge(map.get("minAge").toString()));
		}
		// 最大年龄转化为对应的出生年月
		if (map.get("maxAge") != null) {
			map.put("maxBirth",
					KitService.getBirthFromAge(map.get("maxAge").toString()));
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
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		if (workerIdCard == null || "".equals(workerIdCard)) {
			new HesfException("workerIdCard", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("year", year);
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
	public boolean changeCompany(Integer workerId, Integer targetCompanyId,
			String currentYear, String currentJob) {
		if (workerId == null || workerId <= 0) {
			new HesfException("workerId", HesfException.type_number_negative)
					.printStackTrace();
			return false;
		}
		if (targetCompanyId == null || targetCompanyId <= 0) {
			new HesfException("targetCompanyId", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		if (currentYear == null || "".equals(currentYear)) {
			new HesfException("currentYear", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		CompanyYearWorker cyw = new CompanyYearWorker();
		cyw.setWorkerId(workerId);
		cyw.setCompanyId(targetCompanyId);
		cyw.setYear(currentYear);
		cyw.setCurrentJob(currentJob);
		boolean bl = cywDao.insertSelective(cyw) == 1 ? true : false;
		if (!bl) {
			new HesfException("向员工--公司关系表  插入数据失败!").printStackTrace();
		}
		return bl;
	}

	@Override
	public void deleteAllData() {
		// ①删除 公司-员工关系表数据
		cywDao.deleteAllData();
		// ②删除员工数据
		dao.deleteAllData();
	}

	@Override
	public List<Worker> getByIds(Integer[] ids) {
		if (ids == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constants.ARRAY, ids);
		return dao.retrieveByPrimaryKeys(map);
	}

	@Override
	public byte[] getPicByPrimaryKey(Integer id) {
		if (id == null || id <= 0) {
			return null;
		}
		HashMap resultMap = dao.retrievePic(id);
		byte[] pic = (byte[]) resultMap.get("pic");
		return pic;
	}

}
