package com.cpda.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.cpda.common.utils.BDException;
import com.cpda.core.datasource.DataSourceEnum;
import com.cpda.core.datasource.RoutingDataSource;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis & DataSource & Mapper & PageHelper 配置
 */
@Configuration
@MapperScan("com.cpda.**.dao")
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        //factory.setDataSource(dataSource);  //单数据源方式
        factory.setDataSource(dataSourceConfig());

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
        factory.setMapperLocations(resolver.getResources("classpath*:mappers/**/*.xml"));

        return factory.getObject();
    }

    /**
     * 装配所有数据源
     */
    public RoutingDataSource dataSourceConfig(){
        //动态数据源
        RoutingDataSource myRoutingDataSource = new RoutingDataSource();
        //放入数据源
        Map<Object,Object> targetDataSources = new HashMap<Object,Object>();
        targetDataSources.put(DataSourceEnum.MASTER, dataSourceMaster());
        targetDataSources.put(DataSourceEnum.SQLSERVER, dataSourceSqlServer());
        targetDataSources.put(DataSourceEnum.BPM,dataSourceBPM());
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        myRoutingDataSource.setDefaultTargetDataSource(dataSourceMaster());
        myRoutingDataSource.afterPropertiesSet();
        return myRoutingDataSource;
    }

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

    @Bean
    public DataSource dataSourceBPM(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(getConfig().getString("bpm.driverClassName"));
        ds.setUrl(getConfig().getString("bpm.url"));
        ds.setUsername(getConfig().getString("bpm.username"));
        ds.setPassword(getConfig().getString("bpm.password"));
        ds.setInitialSize(Integer.parseInt(getConfig().getString("bpm.initialSize")));
        ds.setMinIdle(Integer.parseInt(getConfig().getString("bpm.minIdle")));
        ds.setMaxActive(Integer.parseInt(getConfig().getString("bpm.maxActive")));
        ds.setMaxWait(Long.parseLong(getConfig().getString("bpm.maxActive")));
        return ds;
    }

    /**
     * 其它数据源
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
     * 获取数据库链接配置信息
     */
    public static org.apache.commons.configuration.Configuration getConfig() {
        try {
            return new PropertiesConfiguration("datasources.properties");
        } catch (ConfigurationException e) {
            throw new BDException("获取配置文件失败，", e);
        }
    }
}
