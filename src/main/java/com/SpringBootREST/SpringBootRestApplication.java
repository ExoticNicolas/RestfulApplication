package com.SpringBootREST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.SpringBootREST.config.FileStorageConfig;

@SpringBootApplication
public class SpringBootRestApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApplication.class, args);



	}

}
