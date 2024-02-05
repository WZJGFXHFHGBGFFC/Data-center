package org.wzj.statistics.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;


//@MapperScan(basePackages = "org.wzj.statistics.mapper", sqlSessionTemplateRef = "clickhouseSqlSessionTemplate")
public class ClickHouseDataSourceConfig {

    @Value("${spring.datasource.clickhouse.url}")
    private String clickhouseUrl;

    @Value("${spring.datasource.clickhouse.driver-class-name}")
    private String driverClassName;

    @Bean(name = "clickhouseDataSource")
    public DataSource clickhouseDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(clickhouseUrl);
        dataSourceBuilder.driverClassName(driverClassName);
        return dataSourceBuilder.build();
    }

//    @Bean(name = "clickhouseDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.clickhouse")
//    public DataSource clickhouseDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name = "clickhouseSqlSessionFactory")
    public SqlSessionFactory clickhouseSqlSessionFactory(@Qualifier("clickhouseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/clickhouse/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "clickhouseSqlSessionTemplate")
    public SqlSessionTemplate clickhouseSqlSessionTemplate(@Qualifier("clickhouseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
