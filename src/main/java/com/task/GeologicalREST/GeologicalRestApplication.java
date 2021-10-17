package com.task.GeologicalREST;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class GeologicalRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeologicalRestApplication.class, args);
	}

}
