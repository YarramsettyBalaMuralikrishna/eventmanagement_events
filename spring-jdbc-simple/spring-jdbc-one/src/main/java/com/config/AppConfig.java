package com.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@PropertySource("classpath:app.yml")
@Configuration
@EnableTransactionManagement   //A reqd to make @Transactional to work
@ComponentScan(basePackages = "com")
public class AppConfig {
	
	/*@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
	    return new PropertySourcesPlaceholderConfigurer();
	}*/
	
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/db");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }
    
    @Bean //B reqd for the underlying commit or rollback functionality
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
    

    //Inversion of Control - Example
    /*@Bean
    public BookDao bookDao(JdbcTemplate jt) {
        return new BookDaoImpl(jt);
    }*/
	
	
}