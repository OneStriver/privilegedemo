package com.sunkaisens.privilegedemo.dataSourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author HeYin
 * @date 2018/9/11
 */
@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = "com.sunkaisens.privilegedemo.dao.secondDataSource", sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class SecondDataSourceConfig {

    // 精确到 master 目录，以便跟其他数据源隔离
    static final String MAPPER_LOCATION = "classpath:mapper/secondDataSource/*.xml";

    @Bean(name = "secondDataSource")
    public DataSource firstDataSource(SecondDataSourceProperty secondDataSourceProperty) throws SQLException {
        MysqlXADataSource mysqlXADataSource = new MysqlXADataSource();
        mysqlXADataSource.setUrl(secondDataSourceProperty.getUrl());
        mysqlXADataSource.setUser(secondDataSourceProperty.getUsername());
        mysqlXADataSource.setPassword(secondDataSourceProperty.getPassword());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXADataSource);
        xaDataSource.setUniqueResourceName("secondDataSourceProperty");
        xaDataSource.setMinPoolSize(secondDataSourceProperty.getMinPoolSize());
        xaDataSource.setMaxPoolSize(secondDataSourceProperty.getMaxPoolSize());
        xaDataSource.setMaxLifetime(secondDataSourceProperty.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(secondDataSourceProperty.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(secondDataSourceProperty.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(secondDataSourceProperty.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(secondDataSourceProperty.getMaxIdleTime());
        xaDataSource.setTestQuery(secondDataSourceProperty.getTestQuery());
        return xaDataSource;
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(
            @Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

