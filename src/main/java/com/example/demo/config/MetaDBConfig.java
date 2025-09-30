package com.example.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class  MetaDBConfig {


    @Bean(name = "metaDatasource")
    @ConfigurationProperties(prefix = "spring.batch.datasource-meta")
    public DataSource metaDBSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "metaTransactionManager")
    public PlatformTransactionManager metaTransactionManager(@Qualifier("metaDatasource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}