package com.corpdata.system.org.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织实体
 * @author wu
 * @date 2018年2月26日
 */
public class OrgDept implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private String id;

    private String foldername;

    private String folderid;

    private String parentfolderid;

    private Integer sortNumber;

    private String creater;

    private Date created;

    private Date modified;

    public OrgDept(){}
    
    public OrgDept(String id) {
		super();
		this.id = id;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFoldername() {
        return foldername;
    }

    public void setFoldername(String foldername) {
        this.foldername = foldername == null ? null : foldername.trim();
    }

    public String getFolderid() {
        return folderid;
    }

    public void setFolderid(String folderid) {
        this.folderid = folderid == null ? null : folderid.trim();
    }

    public String getParentfolderid() {
        return parentfolderid;
    }

    public void setParentfolderid(String parentfolderid) {
        this.parentfolderid = parentfolderid == null ? null : parentfolderid.trim();
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}