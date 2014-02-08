package com.esd.hesf.model;

/**
 * 残疾类型
 * 
 * @author Administrator
 * 
 */
public class WorkerHandicapType extends PrimaryKey_Int {

	private String handicapType; // 残疾等级

	public WorkerHandicapType() {
	}

	public WorkerHandicapType(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "WorkerHandicapType [handicapType=" + handicapType + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	public String getHandicapType() {
		return handicapType;
	}

	public void setHandicapType(String handicapType) {
		this.handicapType = handicapType;
	}

}