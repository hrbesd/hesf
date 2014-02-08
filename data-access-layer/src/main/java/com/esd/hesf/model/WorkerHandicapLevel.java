package com.esd.hesf.model;

/**
 * 残疾等级
 * 
 * @author Administrator
 * 
 */
public class WorkerHandicapLevel extends PrimaryKey_Int {

	private String handicapLevel; // 残疾等级

	public WorkerHandicapLevel() {
	}

	public WorkerHandicapLevel(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "WorkerHandicapLevel [handicapLevel=" + handicapLevel + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	public String getHandicapLevel() {
		return handicapLevel;
	}

	public void setHandicapLevel(String handicapLevel) {
		this.handicapLevel = handicapLevel;
	}

}