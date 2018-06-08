package com.corpdata.core.datasource;

public enum DataSourceEnum {
	/**
	 * 默认数据源
	 */
	MASTER("db_master"),
	/**
	 * Quartz数据源
	 */
	QUARTZ("db_quartz"),
	/**
	 * sqlserver数据源
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
