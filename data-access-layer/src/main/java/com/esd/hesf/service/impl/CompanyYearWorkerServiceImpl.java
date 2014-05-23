package com.esd.hesf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.dao.CompanyYearWorkerDao;
import com.esd.hesf.model.CompanyYearWorker;
import com.esd.hesf.service.CompanyYearWorkerService;
import com.esd.hesf.service.HesfException;

@Service
public class CompanyYearWorkerServiceImpl implements CompanyYearWorkerService {

	@Autowired
	private CompanyYearWorkerDao dao;

	@Override
	public boolean save(CompanyYearWorker t) {
		if (t == null) {
			new HesfException("companyYearWork", HesfException.type_null)
					.printStackTrace();
			return false;
		}
		return dao.insertSelective(t)==1?true:false;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(CompanyYearWorker t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CompanyYearWorker getByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaginationRecordsAndNumber<CompanyYearWorker, Number> getPaginationRecords(
			CompanyYearWorker t, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
