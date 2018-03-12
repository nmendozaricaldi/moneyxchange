package com.moneyxchange.io.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;




@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "emFactory", transactionManagerRef = "transactionManager", basePackages = {
		"com.moneyxchange.io.repository" })
@PropertySource({ "classpath:persistence.properties" })
public class PersistenceConfig {

	@Autowired
	private Environment env;

	@Bean(name = "entityManager")
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

	@Primary
	@Bean(name = "emFactory")
	public EntityManagerFactory entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.FALSE);
		vendorAdapter.setShowSql(Boolean.FALSE);
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.moneyxchange.io.model");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		jpaProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
//		 jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
		factory.setJpaProperties(jpaProperties);
		factory.setPersistenceUnitName("persistenceUnit");
		factory.afterPropertiesSet();
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return factory.getObject();
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory factory = entityManagerFactory();
		return new JpaTransactionManager(factory);
	}
	

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		return dataSource;
	}
}