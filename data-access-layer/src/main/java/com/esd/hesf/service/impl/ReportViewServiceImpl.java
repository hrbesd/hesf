package com.esd.hesf.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.hesf.dao.AreaDao;
import com.esd.hesf.dao.AuditCompanyViewDao;
import com.esd.hesf.dao.CompanyEconomyTypeDao;
import com.esd.hesf.dao.CompanyTypeDao;
import com.esd.hesf.dao.PaymentDao;
import com.esd.hesf.model.Area;
import com.esd.hesf.model.CompanyEconomyType;
import com.esd.hesf.model.CompanyType;
import com.esd.hesf.service.Constants;
import com.esd.hesf.service.ReportViewService;
import com.esd.hesf.viewmodels.AuditTempModel;
import com.esd.hesf.viewmodels.ReportViewModel;

@Service
public class ReportViewServiceImpl implements ReportViewService {

	@Autowired
	private AuditCompanyViewDao acvDao;

	@Autowired
	private PaymentDao pDao;

	@Autowired
	private CompanyTypeDao ctDao;

	@Autowired
	private CompanyEconomyTypeDao cetDao;

	@Autowired
	private AreaDao aDao;

	@Override
	public List<ReportViewModel> getByCompanyType(String year) {
		// 1-先查询所有公司性质列表
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		List<CompanyType> tempList = ctDao.retrieveByPage(map);
		// 2-从审核-公司 是视图表中查出对应公司性质的数据数据列表
		List<ReportViewModel> list = new ArrayList<ReportViewModel>();
		map.put("year", year);
		for (CompanyType t : tempList) {
			map.put("companyType", t.getId());
			ReportViewModel rvm = acvDao.retrieveReport(map);
			rvm.setReportName(t.getCompanyType());
			rvm.setCompanyType(t.getId());
			list.add(rvm);
		}
		// 3-根据 公司性质列表, 得到对应种类的不同审核阶段的数据
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			map.put("companyType", rvm.getCompanyType());
			List<AuditTempModel> atmList = acvDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				}
			}
			// 查询对应的已经缴款总额
			BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			rvm.setAlreadyAmount(alreadyAmount);
		}
		return list;
	}

	@Override
	public List<ReportViewModel> getByArea(String year) {
		System.out.println("year : " + year);
		// 1-先查询所有黑龙江地区列表
		Map<String, Object> map = new HashMap<String, Object>();
		List<Area> tempList = aDao.retrieveHLJ();
		// 2-从审核-公司 是视图表中查出对应黑龙江地区的数据数据列表
		List<ReportViewModel> list = new ArrayList<ReportViewModel>();
		map.put("year", year);
		for (Area t : tempList) {
			map.put("areaCode", t.getCode());
			ReportViewModel rvm = acvDao.retrieveReport(map);
			rvm.setReportName(t.getName());
			rvm.setAreaCode(t.getCode());
			list.add(rvm);
		}
		// 3-根据 黑龙江地区列表, 得到对应种类的不同审核阶段的数据
		for (ReportViewModel rvm : list) {
			// 循环公司性质的公司, 分别给其中的不同审核阶段的公司复制
			map.put("areaCode", rvm.getAreaCode());
			List<AuditTempModel> atmList = acvDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				}
			}
			// 查询对应的已经缴款总额
			BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			rvm.setAlreadyAmount(alreadyAmount);
		}
		return list;
	}

	@Override
	public List<ReportViewModel> getByCompanyEconomyType(String year) {
		System.out.println("year : " + year);
		// 1-先查询所有公司经济类型列表
		Map<String, Object> map = new HashMap<String, Object>();
		// 起始索引值
		map.put("start", Constants.START);
		// 返回量
		map.put("size", Constants.SIZE);
		List<CompanyEconomyType> tempList = cetDao.retrieveByPage(map);
		// 2-从审核-公司 是视图表中查出对应公司经济类型的数据数据列表
		List<ReportViewModel> list = new ArrayList<ReportViewModel>();
		map.put("year", year);
		for (CompanyEconomyType t : tempList) {
			map.put("companyEconomyType", t.getId());
			ReportViewModel rvm = acvDao.retrieveReport(map);
			rvm.setReportName(t.getCompanyEconomyType());
			rvm.setCompanyEconomyType(t.getId());
			list.add(rvm);
		}
		// 3-根据 公司经济类型列表, 得到对应种类的不同审核阶段的数据
		for (ReportViewModel rvm : list) {
			// 循环公司经济类型的公司, 分别给其中的不同审核阶段的公司复制
			map.put("companyEconomyType", rvm.getCompanyEconomyType());
			List<AuditTempModel> atmList = acvDao.retrieveAupCount(map);
			for (AuditTempModel atm : atmList) {
				switch (atm.getAupId()) {
				// 未初审
				case 1:
					rvm.setUnAudit(atm.getNum());
					break;
				// 已初审, 待复核单位数
				case 2:
					rvm.setUnReAudit(atm.getNum());
					break;
				// 已复核, 未达标单位数
				case 3:
					rvm.setUnauditOk(atm.getNum());
					break;
				// 已复核, 达标单位数
				case 6:
					rvm.setAuditOk(atm.getNum());
					break;
				}
			}
			// 查询对应的已经缴款总额
			BigDecimal alreadyAmount = pDao.retrieveByCompanyAndYear(map);
			rvm.setAlreadyAmount(alreadyAmount);
		}
		return list;
	}

}
