package com.cpda.system.org.entity;

import com.cpda.common.base.BaseEntity;

/**
 * 组织实体
 * @author wu
 * @date 2018年2月26日
 */
public class OrgDept extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
    private String foldername;

    private String folderid;

    private Long parentfolderid;

    private Integer sortNumber;


    public OrgDept(){}
    
    public OrgDept(Long id) {
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

    public Long getParentfolderid() {
        return parentfolderid;
    }

    public void setParentfolderid(Long parentfolderid) {
        this.parentfolderid = parentfolderid;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

}