package com.esd.hesf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.hesf.dao.AreaDao;
import com.esd.hesf.dao.AuditReportDao;
import com.esd.hesf.dao.CompanyEconomyTypeDao;
import com.esd.hesf.dao.CompanyTypeDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyType;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.HesfException;
import com.esd.hesf.service.ReportViewService;
import com.esd.hesf.viewmodels.AuditTempModel;
import com.esd.hesf.viewmodels.ReportViewModel;

@Service
public class ReportViewServiceImpl implements ReportViewService {

	@Autowired
	private AuditReportDao arDao;

	@Autowired
	private CompanyTypeDao ctDao;

	@Autowired
	private CompanyEconomyTypeDao cetDao;

	@Autowired
	private AreaDao aDao;

	@Override
	public List<ReportViewModel> getByCompanyType(String year) {
		// Long now = System.currentTimeMillis();
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		// 1-先查出几个类别下能查出的基本数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SEARCH_KEY_YEAR, year);
		map.put(SEARCH_KEY, SEARCH_KEY_COMPANY_TYPE);
		List<ReportViewModel> list = arDao.retrieveReport(map);
		// 查询出该缴款年度不同公司类型已交款总额
		Integer y = Integer.parseInt(year);
		map.put(SEARCH_KEY_YEAR, y + 1);
		List<ReportViewModel> alreadyList = arDao.retrieveAlreadyPay(map);
		// 将查出的不同类型的已交款, 存放到上面的list中
		for (ReportViewModel rvm : list) {
			for (ReportViewModel alrvm : alreadyList) {
				// 如果两个类型id相同, 则将已交款数存放到list的rvm对象中
				if (rvm.getCompanyType().equals(alrvm.getCompanyType())) {
					rvm.setAlreadyAmount(alrvm.getAlreadyAmount());
					break;
				} else {
					continue;
				}
			}
		}

		// 2-循环已经查出的数据 , 补全不同审核阶段的公司数量 根据 公司性质列表 得到对应种类的不同审核阶段的数据
		map.put(SEARCH_KEY_YEAR, y);
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			map.put(SEARCH_KEY_COMPANY_TYPE, rvm.getCompanyType());
			List<AuditTempModel> atmList = arDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未/待初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				case 7:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				case 4:
					rvm.setUnauditOk(atm.getNum());
					break;
				case 5:
					rvm.setUnauditOk(atm.getNum());
					break;
				}
			}
		}
		// 3-使用最笨的方法, 将其余没有数据的公司类型补全
		// 清空该字段 值
		map.put(SEARCH_KEY_COMPANY_TYPE, null);
		map.put("start", Constants.START);
		map.put("size", Constants.SIZE);
		List<CompanyType> tList = ctDao.retrieveByPage(map);
		List<ReportViewModel> tempList = new ArrayList<ReportViewModel>();
		for (CompanyType t : tList) {
			ReportViewModel r = new ReportViewModel();
			r.setCompanyType(t.getId());
			r.setReportName(t.getCompanyType());
			tempList.add(r);
		}
		// 补全其余没有数据的类别
		for (ReportViewModel t : tempList) {
			if (!list.contains(t)) {
				list.add(t);
			}
		}
		// System.out.println("cost time: " + (System.currentTimeMillis() -
		// now));
		return list;
	}

	@Override
	public List<ReportViewModel> getByArea(String year) {
		// Long now = System.currentTimeMillis();
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		// 1-先查出几个类别下能查出的基本数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SEARCH_KEY_YEAR, year);
		map.put(SEARCH_KEY, SEARCH_KEY_AREA);
		List<ReportViewModel> list = arDao.retrieveReport(map);
		// 查询出该缴款年度不同 地区 已交款总额
		Integer y = Integer.parseInt(year);
		map.put(SEARCH_KEY_YEAR, y + 1);
		List<ReportViewModel> alreadyList = arDao.retrieveAlreadyPay(map);
		// 将查出的不同类型的已交款, 存放到上面的list中
		for (ReportViewModel rvm : list) {
			for (ReportViewModel alrvm : alreadyList) {
				// 如果两个类型id相同, 则将已交款数存放到list的rvm对象中
				if (rvm.getAreaCode().equals(alrvm.getAreaCode())) {
					rvm.setAlreadyAmount(alrvm.getAlreadyAmount());
					break;
				} else {
					continue;
				}
			}
		}

		// 2-循环已经查出的数据 , 补全不同审核阶段的公司数量 根据 公司性质列表, 得到对应种类的不同审核阶段的数据
		map.put(SEARCH_KEY_YEAR, y);
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			map.put(SEARCH_KEY_AREA, rvm.getAreaCode());
			List<AuditTempModel> atmList = arDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未/待初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				case 7:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				case 4:
					rvm.setUnauditOk(atm.getNum());
					break;
				case 5:
					rvm.setUnauditOk(atm.getNum());
					break;
				}
			}
			// // 查询对应的已经缴款总额
			// BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			// rvm.setAlreadyAmount(alreadyAmount);
		}
		// 3-使用最笨的方法, 将其余没有数据的公司类型补全
		// 清空该字段 值
		map.put(SEARCH_KEY_AREA, null);
		map.put("start", Constants.START);
		map.put("size", Constants.SIZE);
		List<Area> tList = aDao.retrieveHLJ();
		List<ReportViewModel> tempList = new ArrayList<ReportViewModel>();
		for (Area t : tList) {
			ReportViewModel r = new ReportViewModel();
			r.setAreaCode(t.getCode());
			r.setReportName(t.getName());
			tempList.add(r);
		}
		// 补全其余没有数据的类别
		for (ReportViewModel t : tempList) {
			if (!list.contains(t)) {
				list.add(t);
			}
		}
		// System.out.println("cost time: " + (System.currentTimeMillis() -
		// now));
		return list;
	}

	@Override
	public List<ReportViewModel> getByCompanyEconomyType(String year) {
		// Long now = System.currentTimeMillis();
		if (year == null || "".equals(year)) {
			new HesfException("year", HesfException.type_null)
					.printStackTrace();
			return null;
		}
		// 1-先查出几个类别下能查出的基本数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SEARCH_KEY_YEAR, year);
		map.put(SEARCH_KEY, SEARCH_KEY_COMPANY_ECONOMY_TYPE);
		List<ReportViewModel> list = arDao.retrieveReport(map);
		// 查询出该缴款年度不同经济类型已交款总额
		Integer y = Integer.parseInt(year);
		map.put(SEARCH_KEY_YEAR, y + 1);
		List<ReportViewModel> alreadyList = arDao.retrieveAlreadyPay(map);
		// 将查出的不同类型的已交款, 存放到上面的list中
		for (ReportViewModel rvm : list) {
			for (ReportViewModel alrvm : alreadyList) {
				// 如果两个类型id相同, 则将已交款数存放到list的rvm对象中
				if (rvm.getCompanyEconomyType().equals(
						alrvm.getCompanyEconomyType())) {
					rvm.setAlreadyAmount(alrvm.getAlreadyAmount());
					break;
				} else {
					continue;
				}
			}
		}

		// 2-循环已经查出的数据 , 补全不同审核阶段的公司数量 根据 公司性质列表, 得到对应种类的不同审核阶段的数据
		map.put(SEARCH_KEY_YEAR, y);
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			map.put(SEARCH_KEY_COMPANY_ECONOMY_TYPE,
					rvm.getCompanyEconomyType());
			List<AuditTempModel> atmList = arDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未/待初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				case 7:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				case 4:
					rvm.setUnauditOk(atm.getNum());
					break;
				case 5:
					rvm.setUnauditOk(atm.getNum());
					break;
				}
			}
			// // 查询对应的已经缴款总额
			// BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			// rvm.setAlreadyAmount(alreadyAmount);
		}
		// 3-使用最笨的方法, 将其余没有数据的公司类型补全
		// 清空该字段 值
		map.put(SEARCH_KEY_COMPANY_ECONOMY_TYPE, null);
		map.put("start", Constants.START);
		map.put("size", Constants.SIZE);
		List<CompanyEconomyType> tList = cetDao.retrieveByPage(map);
		List<ReportViewModel> tempList = new ArrayList<ReportViewModel>();
		for (CompanyEconomyType t : tList) {
			ReportViewModel r = new ReportViewModel();
			r.setCompanyEconomyType(t.getId());
			r.setReportName(t.getCompanyEconomyType());
			tempList.add(r);
		}
		// 补全其余没有数据的类别
		for (ReportViewModel t : tempList) {
			if (!list.contains(t)) {
				list.add(t);
			}
		}
		// System.out.println("cost time: " + (System.currentTimeMillis() -
		// now));
		return list;
	}

}
