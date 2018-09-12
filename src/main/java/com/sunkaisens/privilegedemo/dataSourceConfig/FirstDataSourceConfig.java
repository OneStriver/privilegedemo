package com.sunkaisens.privilegedemo.dataSourceConfig;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author HeYin
 * @date 2018/9/11
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = "com.sunkaisens.privilegedemo.dao.firstDataSource", sqlSessionTemplateRef = "firstSqlSessionTemplate")
public class FirstDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String MAPPER_LOCATION = "classpath:mapper/firstDataSource/*.xml";

    @Primary
    @Bean(name = "firstDataSource")
    public DataSource firstDataSource(FirstDataSourceProperty firstDataSourceProperty) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(firstDataSourceProperty.getUrl());
        mysqlXADataSource.setUser(firstDataSourceProperty.getUsername());
        mysqlXADataSource.setPassword(firstDataSourceProperty.getPassword());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("firstDataSourceProperty");
        xaDataSource.setMinPoolSize(firstDataSourceProperty.getMinPoolSize());
        xaDataSource.setMaxPoolSize(firstDataSourceProperty.getMaxPoolSize());
        xaDataSource.setMaxLifetime(firstDataSourceProperty.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(firstDataSourceProperty.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(firstDataSourceProperty.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(firstDataSourceProperty.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(firstDataSourceProperty.getMaxIdleTime());
        xaDataSource.setTestQuery(firstDataSourceProperty.getTestQuery());
        return xaDataSource;
    }

    @Bean(name = "firstSqlSessionFactory")
    public SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "firstSqlSessionTemplate")
    public SqlSessionTemplate firstSqlSessionTemplate(
            @Qualifier("firstSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

