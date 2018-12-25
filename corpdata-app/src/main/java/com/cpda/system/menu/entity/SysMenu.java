package com.cpda.system.menu.entity;

import com.cpda.common.base.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * 系统菜单
 */
public class SysMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@NotBlank(message="menuName 不能为空.")
	private String menuName;
	private String icon;
	private String menuUrl;
	private Integer sortNumber;
	private String permission;
	private long parentId;
	private Boolean showState;
	@Length(min = 1, max = 1,message="menuType 长度必须为1.")
	private String menuType;
	private String state;

	public SysMenu(){}

	public SysMenu(long id, String state) {
		this.id = id;
		this.state = state;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuName() {
		return menuName;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuUrl() {
		return menuUrl;
	}
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public long getParentId() {
		return parentId;
	}
	public void setShowState(Boolean showState) {
		this.showState = showState;
	}

	public Boolean getShowState() {
		return showState;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
