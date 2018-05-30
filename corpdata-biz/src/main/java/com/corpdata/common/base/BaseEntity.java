package com.corpdata.common.base;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 基础 entity
 * 
 */
public abstract class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String id;
	protected Date created;
	protected Date modified;
	
	public String getId() {
        return id;
    }

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreated() {
        return created;
    }

    public void setCreateDate(Date created) {
        this.created = created;
    }
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
