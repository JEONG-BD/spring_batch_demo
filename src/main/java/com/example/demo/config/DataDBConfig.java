package com.example.demo.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.demo.repository",
        entityManagerFactoryRef = "dataEntityManager",
        transactionManagerRef = "targetTransactionManager"
)
public class DataDBConfig {

    @Primary
    @Bean(name="targetDatasource")
    @ConfigurationProperties(prefix = "spring.datasource-data")
    public DataSource dataDBSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataEntityManager")
    public LocalContainerEntityManagerFactoryBean dataEntityManager() {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataDBSource());
        em.setPackagesToScan(new String[]{"com.example.demo.entity"});
        em. setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        em.setJpaPropertyMap(properties);

        return em;
    }

    //@Primary
    //@Bean(name = "targetTransactionManager")
    //public PlatformTransactionManager metaTransactionManager(@Qualifier("targetDatasource")DataSource ds){
    //    return new DataSourceTransactionManager(ds);
    //}

    //@Bean
    //public PlatformTransactionManager dataTransactionManager(
    //        @Qualifier("dataEntityManager") EntityManagerFactory emf) {
    //    return new JpaTransactionManager(emf);
    //}
    @Primary
    @Bean(name = "targetTransactionManager")
    public PlatformTransactionManager dataTransactionManager( @Qualifier("dataEntityManager") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
