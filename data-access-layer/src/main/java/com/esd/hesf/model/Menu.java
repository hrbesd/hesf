package com.esd.hesf.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单类 id 不是主键 ,知识普通字段; primaryKey 才是主键
 * 
 * @author yufu
 * 
 */
public class Menu extends PrimaryKey_String {

	/**
	 * primaryKey 为主键, id不是主键
	 */
	private Integer primaryKey; // 自增主键
	private String text;
	private String state;
	private String url;
	private String checked;
	private String iconcls;
	private PermissionType permissionType; // 权限类型
	private Attribute attributes; // attributes属性

	// 子项
	List<Menu> children = new ArrayList<Menu>();

	public Menu() {
	}

	public Menu(String id) {
		super.setId(id);
	}

	@Override
	public String toString() {
		return "Menu [primaryKey=" + primaryKey + ", text=" + text + ", state=" + state + ", url=" + url + ", checked=" + checked + ", iconcls=" + iconcls + ", permissionType=" + permissionType
				+ ", attributes=" + attributes + ", children=" + children + ", getId()=" + getId() + ", getIsActive()=" + getIsActive() + ", getVersion()=" + getVersion() + "]";
	}

	// 添加一个子项
	public void add(Menu menu) {
		children.add(menu);
	}

	/**
	 * primaryKey 为主键, id不是主键
	 */
	public Integer getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * primaryKey 为主键, id不是主键
	 */
	public void setPrimaryKey(Integer primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getIconcls() {
		return iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}

	public Attribute getAttributes() {
		return attributes;
	}

	public void setAttributes(Attribute attributes) {
		this.attributes = attributes;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

}
