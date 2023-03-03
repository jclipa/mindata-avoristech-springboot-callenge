package es.mindata.avoristech.application;

import static es.mindata.avoristech.constants.AppConstants.PACKAGE_BASE;
import static es.mindata.avoristech.constants.AppConstants.PACKAGE_DOMAIN;
import static es.mindata.avoristech.constants.AppConstants.PACKAGE_REPOSITORY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { PACKAGE_BASE })
@EntityScan(basePackages = { PACKAGE_DOMAIN })
@EnableJpaRepositories(basePackages = { PACKAGE_REPOSITORY })
public class Application {

	public static void main(String[] args) {
		// log.info("Starting application...");
		SpringApplication.run(Application.class, args);
	}

}
