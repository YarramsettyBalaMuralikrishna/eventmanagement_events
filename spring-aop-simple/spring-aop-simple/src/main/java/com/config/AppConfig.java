package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aspect.LoggerAspect;
import com.service.CallerService;

@Configuration
@EnableAspectJAutoProxy
//Spring's <aop:aspectj-autoproxy> XML element in the XML file. 
@ComponentScan(basePackages = "com")
public class AppConfig {

	/*@Bean
	public CallerService myService() {
		return new CallerService();
	}*/

	/*@Bean
	public LoggerAspect aspectBean() {
		return new LoggerAspect();
	}*/

}
