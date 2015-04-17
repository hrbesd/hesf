package com.esd.hesf.service.impl;

import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.AuditParameterDao;
import com.esd.hesf.dao.WorkerTempDao;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.WorkerTemp;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.KitService;
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

	@Autowired
	private AuditParameterDao apDao;

	@Override
	public Boolean save(WorkerTemp t) {
		if (t == null) {
			new HesfException("workerTemp", HesfException.type_null)
					.printStackTrace();
		}
		//保存
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
		}
		return true;
	}

	@Override
	public Boolean delete(Integer id) {
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
	public Boolean deleteByWorkerIdCard(String workerIdCard) {
		return dao.deleteByWorkerIdCard(workerIdCard)>0?true:false;
	}
	
	@Override
	public Boolean update(WorkerTemp t) {
		if (t == null) {
			new HesfException("workerTemp", HesfException.type_null)
					.printStackTrace();
		}
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
		}
		return true;
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
	public Boolean deleteByCompanyId(Integer companyId) {
		return (dao.deleteByCompanyId(companyId) > 0) ? true : false;
	}

	
	@Override
	public WorkerTemp getByWorkerIdCard(String WorkerIdCard) {
		return dao.retrieveByWorkerIdCard(WorkerIdCard);
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
		Integer result = dao.retrieveCountByworkerIdCard(workerIdCard);
		if (result == null || result < 0) {
			result = 0;
		}
		return result;
	}

	@Override
	public PaginationRecordsAndNumber<WorkerTemp, Number> getByMultiConditions(
			Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
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
		List<WorkerTemp> list = dao.retrieveByMultiConditions(map);
		// 数据条数
		int count = dao.retrieveByMultiConditionsCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<WorkerTemp, Number> prn = new PaginationRecordsAndNumber<WorkerTemp, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
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

	@Override
	public int getCountByCompanyId(Integer companyId) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("companyId", companyId);
		return dao.retrieveByMultiConditionsCount(map);
	}
	
	
}
