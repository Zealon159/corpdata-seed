package com.corpdata.core.datasource;

public enum DataSourceEnum {
	/**
	 * 默认数据源
	 */
	MASTER("db_master"),
	/**
	 * Activiti数据源
	 */
	ACTIVITI("db_activiti"),
	/**
	 * Activiti数据源
	 */
	SQLSERVER("db_sqlserver");
	
    private String datasource;

    DataSourceEnum( final String datasource){
        this.datasource=datasource;
    }

    public String getDataSource() {
        return datasource;
    }
}
