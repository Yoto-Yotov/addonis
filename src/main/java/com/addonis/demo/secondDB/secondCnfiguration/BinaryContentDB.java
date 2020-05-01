package com.addonis.demo.secondDB.secondCnfiguration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.addonis.demo.secondDB.secondRepository",
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager")
public class BinaryContentDB {

    private String secondUrl, secondUserName, secondPassword;

    @Autowired
    public BinaryContentDB(Environment env) {
        secondUrl = env.getProperty("binaryContent.database.url");
        secondUserName = env.getProperty("binaryContent.database.username");
        secondPassword = env.getProperty("binaryContent.database.password");
    }

    @Bean(name = "binary_addonis_db")
    public DataSource secondDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(secondUrl);
        dataSource.setUsername(secondUserName);
        dataSource.setPassword(secondPassword);
        return dataSource;
    }

    @Bean(name = "secondEntityManagerFactory")
    public EntityManagerFactory secondEntityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan("com.addonis.demo.secondDB.secondModels");
        factoryBean.setDataSource(secondDataSource());
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(secondEntityManagerFactory());
        return txManager;
    }


}
