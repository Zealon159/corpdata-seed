package com.corpdata.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.corpdata.common.utils.BDException;
import com.corpdata.core.datasource.DataSourceEnum;
import com.corpdata.core.datasource.RoutingDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import javax.sql.DataSource;
import static com.corpdata.core.constant.ProjectConstant.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis & DataSource & Mapper & PageHelper 配置
 */
@Configuration
@MapperScan("com.corpdata.**.dao")
public class MybatisConfig {
    
	/**
     * 配置默认数据源
     */
    @Primary
    @Bean
    public DataSource dataSourceMaster(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(getConfig().getString("master.driverClassName"));
        ds.setUrl(getConfig().getString("master.url"));
        ds.setUsername(getConfig().getString("master.username"));
        ds.setPassword(getConfig().getString("master.password"));
        ds.setInitialSize(Integer.parseInt(getConfig().getString("master.initialSize")));
        ds.setMinIdle(Integer.parseInt(getConfig().getString("master.minIdle")));
        ds.setMaxActive(Integer.parseInt(getConfig().getString("master.maxActive")));
        ds.setMaxWait(Long.parseLong(getConfig().getString("master.maxActive")));
        return ds;
    }

    /**
     * 其它数据源
     */
    @Bean
    public DataSource dataSourceQuartz(){            
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(getConfig().getString("quartz.driverClassName"));
        ds.setUrl(getConfig().getString("quartz.url"));
        ds.setUsername(getConfig().getString("quartz.username"));
        ds.setPassword(getConfig().getString("quartz.password"));
        ds.setInitialSize(Integer.parseInt(getConfig().getString("quartz.initialSize")));
        ds.setMinIdle(Integer.parseInt(getConfig().getString("quartz.minIdle")));
        ds.setMaxActive(Integer.parseInt(getConfig().getString("quartz.maxActive")));
        ds.setMaxWait(Long.parseLong(getConfig().getString("quartz.maxActive")));
        return ds;
    }
    
    /**
     * sqlserver数据源
     */
    @Bean
    public DataSource dataSourceSqlServer(){            
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(getConfig().getString("sqlserver.driverClassName"));
        ds.setUrl(getConfig().getString("sqlserver.url"));
        ds.setUsername(getConfig().getString("sqlserver.username"));
        ds.setPassword(getConfig().getString("sqlserver.password"));
        ds.setInitialSize(Integer.parseInt(getConfig().getString("sqlserver.initialSize")));
        ds.setMinIdle(Integer.parseInt(getConfig().getString("sqlserver.minIdle")));
        ds.setMaxActive(Integer.parseInt(getConfig().getString("sqlserver.maxActive")));
        ds.setMaxWait(Long.parseLong(getConfig().getString("sqlserver.maxActive")));
        return ds;
    }
    
    /**
     * 装配所有数据源
     */
    @Bean
    public RoutingDataSource dataSourceConfig(){
    	//动态数据源
    	RoutingDataSource myRoutingDataSource = new RoutingDataSource();
    	//放入数据源
    	Map<Object,Object> targetDataSources = new HashMap<Object,Object>();
    	targetDataSources.put(DataSourceEnum.MASTER, dataSourceMaster());
    	targetDataSources.put(DataSourceEnum.QUARTZ, dataSourceQuartz());
    	targetDataSources.put(DataSourceEnum.SQLSERVER, dataSourceSqlServer());
    	myRoutingDataSource.setTargetDataSources(targetDataSources);
    	//设置默认数据源
    	myRoutingDataSource.setDefaultTargetDataSource(dataSourceMaster());
    	myRoutingDataSource.afterPropertiesSet();
    	return myRoutingDataSource;
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(RoutingDataSource dataSourceConfig) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSourceConfig);
        factory.setTypeAliasesPackage(MODEL_PACKAGE);

        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("params", "count=countSql");
        properties.setProperty("autoRuntimeDialect", "true"); //切换数据源，自动解析不同数据库的分页
        pageHelper.setProperties(properties);

        //添加插件
        factory.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:com/corpdata/**/mapper/*.xml"));
        return factory.getObject();
    }

    /**
     * 获取数据库链接配置信息
     */
    public static org.apache.commons.configuration.Configuration getConfig() {
        try {
            return new PropertiesConfiguration("config/properties/datasources.properties");
        } catch (ConfigurationException e) {
            throw new BDException("获取配置文件失败，", e);
        }
    }
}

