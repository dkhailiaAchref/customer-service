package com.example.addressModification.config;

import com.example.addressModification.AddressModificationApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *  defining a bean of type 'org.springframework.web.client.RestTemplate' in our configuration.
 *  to grant a creating bean with name 'subscriberServices': with satisfied dependency field 'restTemplate
 */
@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.restful.microservices.example.*"})
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AddressModificationApplication.class);
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		return restTemplate;
	}
}
