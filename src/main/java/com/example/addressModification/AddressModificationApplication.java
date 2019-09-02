package com.example.addressModification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,SecurityAutoConfiguration.class })
//@EnableJpaRepositories(basePackages = "com.example.addressModification.dao",entityManagerFactoryRef = "entityManagerFactory")
@PropertySource(value = {"classpath:application.properties"})
//public class AddressModificationApplication   extends SpringBootServletInitializer{
	public class AddressModificationApplication   {


		public static void main(String[] args) {
		SpringApplication.run(AddressModificationApplication.class, args);
	}

}
