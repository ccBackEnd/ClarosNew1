package com.tempKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KafkaTestProjecttApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTestProjecttApplication.class, args);
	}

}
