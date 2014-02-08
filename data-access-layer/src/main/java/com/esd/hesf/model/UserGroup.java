package com.esd.hesf.model;

/**
 * 用户组 类
 * 
 * @author Administrator
 * 
 */
public class UserGroup extends PrimaryKey_Int {

	private String userGroupName; // 用户组名称

	private PermissionType permissionType; // 用户组对应的权限组名

	public UserGroup() {
	}

	public UserGroup(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "UserGroup [userGroupName=" + userGroupName + ", permissionType=" + permissionType + "]";
	}

	public String getUserGroupName() {
		return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}

}