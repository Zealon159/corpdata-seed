package com.corpdata.core.datasource;

public enum DataSourceEnum {
	/**
	 * 默认数据源
	 */
	MASTER("sqlserver"),
	/**
	 * Quartz数据源
	 */
	QUARTZ("mysql"),
	/**
	 * sqlserver数据源
	 */
	SQLSERVER("mysql");
	
    private String datasource;

    DataSourceEnum( final String datasource){
        this.datasource=datasource;
    }

    public String getDataSource() {
        return datasource;
    }
}
