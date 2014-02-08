package com.esd.hesf.model;

/**
 * 权限类
 * 
 * @author Administrator
 * 
 */
public class PermissionType extends PrimaryKey_Int {

	private String permissionType; // 权限类型名称

	public PermissionType() {
	}

	public PermissionType(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "PermissionType [permissionType=" + permissionType + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

}