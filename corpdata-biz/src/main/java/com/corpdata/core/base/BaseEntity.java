package com.corpdata.core.base;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String id;			//主键ID
	protected String creater; 		//创建人
	protected Date created;			//创建时间
	protected String modifier;		//修改人
	protected Date modified;		//修改时间
    
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
    
}
