package com.cpda.system.dic.entity;


import com.cpda.common.base.BaseEntity;

import java.io.Serializable;

/**
 * 系统数据字典实体
 *
 * @author zealon
 * @date 2018年2月26日
 */
public class SysDataDic extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dicName;

    private Integer sortNumber;

    private String dicType;

    private String typeName;

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName == null ? null : dicName.trim();
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getDicType() {
        return dicType;
    }

    public void setDicType(String dicType) {
        this.dicType = dicType == null ? null : dicType.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}