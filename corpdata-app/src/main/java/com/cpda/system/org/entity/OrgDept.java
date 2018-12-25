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

    private String parentfolderid;

    private Integer sortNumber;

    private String state;
    
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

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

	public String getParentfolderid() {
		return parentfolderid;
	}

	public void setParentfolderid(String parentfolderid) {
		this.parentfolderid = parentfolderid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "OrgDept [id="+id+",foldername=" + foldername + ", folderid=" + folderid + ", parentfolderid=" + parentfolderid
				+ ", sortNumber=" + sortNumber + ", state=" + state + "]";
	}

}