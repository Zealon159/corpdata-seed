package com.corpdata.core.datasource.aop;

import java.lang.annotation.*;

import com.corpdata.core.datasource.DataSourceEnum;


/**
 * 创建拦截设置数据源的注解
 * 
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DynamicSwitchDataSource {
	DataSourceEnum dataSource() default DataSourceEnum.MASTER;
}