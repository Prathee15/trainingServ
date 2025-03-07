package com.acf.trainingserv;

import com.acf.trainingserv.config.DatabaseUrlConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainingservApplication {

	public static void main(String[] args) {
		//DatabaseUrlConverter.getJdbcUrl();
		SpringApplication.run(TrainingservApplication.class, args);
	}

}
