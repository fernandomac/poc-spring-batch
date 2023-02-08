package com.api.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
		return dozerBeanMapper;
	}

}
