package com.zenika.microservices.resanet.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(scanBasePackages={"com.zenika.microservices.resanet.controller",
			"com.zenika.microservices.resanet.services"})
@EnableJpaRepositories(basePackages={"com.zenika.microservices.resanet.repository"})
@EntityScan(basePackages={"com.zenika.microservices.resanet.domain"})
public class Application  {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
