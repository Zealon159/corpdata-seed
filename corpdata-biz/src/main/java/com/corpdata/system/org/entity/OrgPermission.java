package com.corpdata.system.org.entity;

import com.corpdata.core.base.BaseEntity;

public class OrgPermission extends BaseEntity {

	private static final long serialVersionUID = 1L;

    private String parentid;

    private String permissionname;

    private String type;

    private String resourceurl;

    private String permission;

    private String sortnum;


    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid == null ? null : parentid.trim();
    }

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname == null ? null : permissionname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getResourceurl() {
        return resourceurl;
    }

    public void setResourceurl(String resourceurl) {
        this.resourceurl = resourceurl == null ? null : resourceurl.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getSortnum() {
        return sortnum;
    }

    public void setSortnum(String sortnum) {
        this.sortnum = sortnum == null ? null : sortnum.trim();
    }
}