package com.esd.hesf.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.LegalHolidayDao;
import com.esd.hesf.dao.PermissionTypeDao;
import com.esd.hesf.dao.UserGroupDao;
import com.esd.hesf.model.LegalHoliday;
import com.esd.hesf.model.PermissionType;
import com.esd.hesf.model.User;
import com.esd.hesf.model.UserGroup;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.KitService;
import com.esd.hesf.service.LegalHolidayService;

@Service
public class LegalHolidayServiceImpl implements LegalHolidayService {

	@Autowired
	private LegalHolidayDao dao;

	@Autowired
	private UserGroupDao userGroupDao;

	@Autowired
	private PermissionTypeDao permissionTypeDao;

	@Override
	public Boolean save(LegalHoliday t) {
		if (t == null) {
			new HesfException("legalHoliday对象", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		t.setId(KitService.getUUID());
		int k = dao.insertSelective(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Boolean delete(Integer id) {
		return null;
	}
	
	@Override
	public Boolean delete(String id) {
		int k = dao.deleteByPrimaryKey(id);
		if (k != 1) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
			return false;
		}
		return true;
	}
	

	@Override
	public Boolean update(LegalHoliday t) {
		int k = dao.updateByPrimaryKey(t);
		if (k != 1) {
			new HesfException(t.getClass().getName(), HesfException.type_fail)
					.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public LegalHoliday getByPrimaryKey(String id) {
		LegalHoliday t = dao.retrieveByPrimaryKey(id);
		if (t == null) {
			new HesfException(this.getClass().getName(),
					HesfException.type_fail).printStackTrace();
		}
		return t;
	}

	@Override
	public LegalHoliday getByPrimaryKey(Integer id) {
		return null;
	}

	@Override
	public PaginationRecordsAndNumber<LegalHoliday, Number> getPaginationRecords(
			LegalHoliday t, Integer page, Integer pageSize) {
		// 将参数放入到map中
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("legalHoliday", t);
		// 起始索引值
		map.put("start", page <= 1 ? Constants.START : (page - 1) * pageSize);
		// 返回量
		map.put("size", pageSize);
		// 返回的数据
		List<LegalHoliday> list = dao.retrieveByPage(map);
		// 数据条数
		int count = dao.retrieveCount(map);
		// 将信息和数据总条数放入PaginationRecordsAndNumber对象中
		PaginationRecordsAndNumber<LegalHoliday, Number> prn = new PaginationRecordsAndNumber<LegalHoliday, Number>();
		prn.setNumber(count);
		prn.setRecords(list);
		return prn;
	}

	@Override
	public List<LegalHoliday> getAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		return dao.retrieveByPage(map);
	}

	@Override
	public Boolean checkIsRestDay(User user) {
		// 按照可能遇到
		// ①检查周六周日
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		// 星期几
		String xingqi = sdf.format(date).substring(2);
		if ("六".equals(xingqi) || "日".equals(xingqi)) {
			return Boolean.FALSE;
		}
		// ②检查法定节假日, 看看是否在时间范围内
		// 当前日期
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
		String today = month + "月" + day + "日";
		LegalHoliday legalHoliday = dao.retrieveByLegalDate(today);
		if (legalHoliday != null) {
			return Boolean.FALSE;
		}
		// ③按用户所在的用户组查询他的时间是否在允许的工作时间范围内
		sdf = new SimpleDateFormat("HHmm");
		String nowtime = sdf.format(date); // 当前时间
		// 获得该用户所在用户组的权限值,这里仅过滤初审, 复审和缴款用户
		if (user.getUserGroup().getId() >= 2
				&& user.getUserGroup().getId() <= 4) {
			UserGroup userGroup = userGroupDao.retrieveByPrimaryKey(user
					.getUserGroup().getId());
			PermissionType permisstionType = permissionTypeDao
					.retrieveByPrimaryKey(userGroup.getPermissionType().getId());
			// 如果该用户的操作在 权限组规定的时间范围内的则返回TRUE, 其余都返回false.
			Boolean bl1 = nowtime.compareTo(permisstionType.getStartTime()) > 0;
			Boolean bl2 = nowtime.compareTo(permisstionType.getEndTime()) < 0;
			if ( bl1 && bl2) {
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public LegalHoliday getByLegaldate(String legaldate) {
		LegalHoliday LegalHoliday = dao.retrieveByLegalDate(legaldate);
		return LegalHoliday;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
	    int dow = cal.get(Calendar.DAY_OF_WEEK);
	    int dom = cal.get(Calendar.DAY_OF_MONTH);
	    int doy = cal.get(Calendar.DAY_OF_YEAR);

	    System.out.println("Current Date: " + cal.getTime());
	    System.out.println("Day: " + day);
	    System.out.println("Month: " + month);
	    System.out.println("Year: " + year);
	    System.out.println("Day of Week: " + dow);
	    System.out.println("Day of Month: " + dom);
	    System.out.println("Day of Year: " + doy);
	}
	
}
