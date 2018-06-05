package com.corpdata.system.org.entity;

import com.corpdata.core.base.BaseEntity;

/**
 * 组织实体
 * @author wu
 * @date 2018年2月26日
 */
public class OrgDept extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
    private String foldername;

    private String folderid;

    private String parentfolderid;

    private Integer sortNumber;


    public OrgDept(){}
    
    public OrgDept(String id) {
		super();
		this.id = id;
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
}