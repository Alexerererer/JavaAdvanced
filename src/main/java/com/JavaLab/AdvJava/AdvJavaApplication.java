package com.JavaLab.AdvJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Necessary configuration to use H2 with JPA
@EnableJpaRepositories("com.JavaLab.AdvJava.models")
@EntityScan("com.JavaLab.AdvJava.models")
@SpringBootApplication
public class AdvJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvJavaApplication.class, args);
	}

}
