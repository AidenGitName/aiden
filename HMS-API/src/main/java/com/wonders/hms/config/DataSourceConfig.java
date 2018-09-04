package com.wonders.hms.config;

import com.wonders.hms.util.KMSProperties;
import com.wonders.hms.util.vo.database.Cluster;
import com.wonders.hms.util.vo.database.Reader;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mybatis.replication.datasource.ReplicationRoutingDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig {

    /**
     * KMS properties 데이터 bean 설정
     * @return
     */
    @Bean
    @ConfigurationProperties("kms")
    public KMSProperties kmsPropertis() {
        return new KMSProperties();
    }

    @Bean
    @Primary
    public DataSourceProperties readWriteDataSourceProperties() {
        KMSProperties kmsProp = kmsPropertis(); // KMS properties 정보
        Cluster cluster = kmsProp.getMessage().getDatabase().getCluster(); // read/wirte DataSource 정보

        DataSourceProperties dsp = new DataSourceProperties();

        String url = "jdbc:mysql://" + cluster.getHost() + ":" + cluster.getPort() + "/" + cluster.getDbname();
        url += "?verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf8";

        dsp.setUrl(url);
        dsp.setUsername(cluster.getUsername());
        dsp.setPassword(cluster.getPassword());
        return dsp;
    }

    @Primary
    @Bean(name = "readWriteDataSource", destroyMethod = "close")
    public DataSource readWriteDataSource() {
        return readWriteDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSourceProperties readOnlyDataSourceProperties() {
        KMSProperties kmsProp = kmsPropertis(); // KMS properties 정보
        Reader reader = kmsProp.getMessage().getDatabase().getReader(); // read/only DataSource 정보
        DataSourceProperties dsp = new DataSourceProperties();

        String url = "jdbc:mysql://" + reader.getHost() + ":" + reader.getPort() + "/" + reader.getDbname();
        url += "?verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf8";

        dsp.setUrl(url);
        dsp.setUsername(reader.getUsername());
        dsp.setPassword(reader.getPassword());

        return dsp;
    }

    @Bean(name = "readOnlyDataSource", destroyMethod = "close")
    public DataSource readOnlyDataSource() {
        return readOnlyDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(@Qualifier("readWriteDataSource") DataSource readWriteDataSource, @Qualifier("readOnlyDataSource") DataSource readOnlyDataSource) {
        List<DataSource> readOnlyDataSources = new ArrayList<>();
        readOnlyDataSources.add(readOnlyDataSource);

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource(readWriteDataSource, readOnlyDataSources);
        return routingDataSource;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
