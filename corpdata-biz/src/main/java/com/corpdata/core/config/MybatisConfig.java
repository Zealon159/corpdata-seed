package com.corpdata.core.config;

import static com.corpdata.core.constant.ProjectConstant.MODEL_PACKAGE;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.alibaba.druid.pool.DruidDataSource;
import com.corpdata.common.utils.BDException;
import com.corpdata.core.datasource.DataSourceEnum;
import com.corpdata.core.datasource.RoutingDataSource;
import com.github.pagehelper.PageHelper;

/**
 * Mybatis & DataSource & Mapper & PageHelper 配置
 */
@Configuration
@MapperScan(basePackages = "com.corpdata.**.dao")
public class MybatisConfig {

    @Value("${properties.path.datasources}")
    private String dsPropertiesPath;

    @Autowired
    private org.apache.commons.configuration.Configuration config;

    /**
     * 配置默认数据源
     */
    @Primary
    @Bean
    public DataSource dataSourceMaster(){
        DruidDataSource ds = new DruidDataSource();

        ds.setDriverClassName(config.getString("master.driverClassName"));
        ds.setUrl(config.getString("master.url"));
        ds.setUsername(config.getString("master.username"));
        ds.setPassword(config.getString("master.password"));
        ds.setInitialSize(Integer.parseInt(config.getString("master.initialSize")));
        ds.setMinIdle(Integer.parseInt(config.getString("master.minIdle")));
        ds.setMaxActive(Integer.parseInt(config.getString("master.maxActive")));
        ds.setMaxWait(Long.parseLong(config.getString("master.maxActive")));
        return ds;
    }

    /**
     * 其它数据源
     */
    @Bean
    public DataSource dataSourceQuartz(){
        DruidDataSource ds = new DruidDataSource();

        ds.setDriverClassName(config.getString("quartz.driverClassName"));
        ds.setUrl(config.getString("quartz.url"));
        ds.setUsername(config.getString("quartz.username"));
        ds.setPassword(config.getString("quartz.password"));
        ds.setInitialSize(Integer.parseInt(config.getString("quartz.initialSize")));
        ds.setMinIdle(Integer.parseInt(config.getString("quartz.minIdle")));
        ds.setMaxActive(Integer.parseInt(config.getString("quartz.maxActive")));
        ds.setMaxWait(Long.parseLong(config.getString("quartz.maxActive")));
        return ds;
    }

    /**
     * sqlserver数据源
     */
    @Bean
    public DataSource dataSourceSqlServer(){
        DruidDataSource ds = new DruidDataSource();

        ds.setDriverClassName(config.getString("sqlserver.driverClassName"));
        ds.setUrl(config.getString("sqlserver.url"));
        ds.setUsername(config.getString("sqlserver.username"));
        ds.setPassword(config.getString("sqlserver.password"));
        ds.setInitialSize(Integer.parseInt(config.getString("sqlserver.initialSize")));
        ds.setMinIdle(Integer.parseInt(config.getString("sqlserver.minIdle")));
        ds.setMaxActive(Integer.parseInt(config.getString("sqlserver.maxActive")));
        ds.setMaxWait(Long.parseLong(config.getString("sqlserver.maxActive")));
        return ds;
    }

    /**
     * 装配所有数据源
     * 所有数据原都交给config类管理，再将config数据源交给spring，不再将每个数据源单独交给spring管理
     */
    @Bean(name = "dataSourceConfig")
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
        myRoutingDataSource.setDefaultTargetDataSource(targetDataSources.get(DataSourceEnum.MASTER));
        myRoutingDataSource.afterPropertiesSet();
        return myRoutingDataSource;
    }

    //事务管理器
    @Bean
    public PlatformTransactionManager masterTransactionManager(RoutingDataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(RoutingDataSource dataSourceConfig) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        //factory.setDataSource(dataSource);  //单数据源方式
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
    @Bean
    public org.apache.commons.configuration.Configuration getConfig() {
        try {
            return new PropertiesConfiguration(dsPropertiesPath);
        } catch (ConfigurationException e) {
            throw new BDException("获取配置文件失败，", e);
        }
    }
}

