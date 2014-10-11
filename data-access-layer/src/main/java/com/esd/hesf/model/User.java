package com.esd.hesf.model;

/**
 * 用户类
 * 
 * @author Administrator
 * 
 */
public class User extends PrimaryKey_Int {

	private String userName; // 用户名
	private String userRealName; // 真实姓名
	private String userPassword; // 密码
	private String userEmail; // 邮箱
	private String userMobile; // 手机
	private String userPhone; // 固话
	private UserGroup userGroup; // 所属用户组
	private Boolean userStatus; // 用户状态 true-激活  false-禁用
	private String userRemark; // 备注

	public User() {
	}

	public User(int id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", userPassword=" + userPassword + ", userEmail=" + userEmail + ", userMobile=" + userMobile + ", userPhone=" + userPhone + ", userGroup=" + userGroup
				+ ", userStatus=" + userStatus + ", userRemark=" + userRemark + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public Boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

}