package com.esd.hesf.model;

/**
 * 权限类
 * 
 * @author Administrator
 * 
 */
public class PermissionType extends PrimaryKey_Int {

	private String permissionType; // 权限类型名称
	private String startTime; // 工作日限定开始时间
	private String endTime; // 工作日限定结束时间

	public PermissionType() {
	}

	public PermissionType(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "PermissionType [permissionType=" + permissionType
				+ ", getId()=" + getId() + ", getIsActive()=" + getIsActive()
				+ ", getVersion()=" + getVersion() + "]";
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}