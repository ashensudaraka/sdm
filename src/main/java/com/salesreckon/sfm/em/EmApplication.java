package com.salesreckon.sfm.em;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;

@EnableCaching
@EnableFeignClients
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EntityScan({"com.salesreckon.microservices.core", "com.salesreckon.sfm.em"})
@SpringBootApplication(scanBasePackages = {"com.salesreckon.sfm.em", "com.salesreckon.microservices.core"})
public class EmApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmApplication.class, args);
	}

}
