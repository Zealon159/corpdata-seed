package com.cpda.system.menu.entity;


import com.cpda.common.base.BaseEntity;

/**
 * 系统菜单
 */
public class SysMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String menuName;
	private String icon;
	private String menuUrl;
	private Integer sortNumber;
	private String parentId;
	private Boolean showState;
	private String state;

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
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}
	public void setShowState(Boolean showState) {
		this.showState = showState;
	}

	public Boolean getShowState() {
		return showState;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
