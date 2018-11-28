package com.cpda.common.base;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected Long id;				//主键ID
	protected String creater; 		//创建人
	protected Date created;			//创建时间
	protected String modifier;		//修改人
	protected Date modified;		//修改时间
    
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
