package com.corpdata.common.result;

import java.io.Serializable;

/**
 * 树结构数据封装
 *
 */
public class JsTree implements Serializable {
	private static final long serialVersionUID = 1019326847986216990L;
	private String id;
	private String parent;
	private String text;
	private String icon;
	private JsTreeState state = new JsTreeState();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public JsTreeState getState() {
		return state;
	}

	public void setState(JsTreeState state) {
		this.state = state;
	}
}
