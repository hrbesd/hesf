package com.esd.hesf.viewmodels;

/**
 * 中转数据用的缓存model --前台不用
 * 
 * @author Administrator
 * 
 */
public class AuditTempModel {

	private Integer num; // 数量
	private Integer aupId; // 审核状态ID

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getAupId() {
		return aupId;
	}

	public void setAupId(Integer aupId) {
		this.aupId = aupId;
	}

}
