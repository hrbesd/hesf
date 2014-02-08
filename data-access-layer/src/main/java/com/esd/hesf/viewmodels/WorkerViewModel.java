package com.esd.hesf.viewmodels;

import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;

/**
 * 残疾职工视图模型 带着所在公司的信息
 * 
 * @author Administrator
 * 
 */
public class WorkerViewModel extends Worker {

	private Company company; // 当年度所在的公司

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
