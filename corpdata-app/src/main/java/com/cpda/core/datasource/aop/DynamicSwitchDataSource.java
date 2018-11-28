package com.cpda.core.datasource.aop;

import com.cpda.core.datasource.DataSourceEnum;
import java.lang.annotation.*;

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