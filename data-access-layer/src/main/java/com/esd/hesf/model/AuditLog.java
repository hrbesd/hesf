package com.esd.hesf.model;

/**
 * 审核信息log类
 * 
 * @author Administrator
 * 
 */
public class AuditLog extends Audit {

	private Integer logId; // log id

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

}