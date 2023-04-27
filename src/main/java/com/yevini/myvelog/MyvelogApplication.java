package com.yevini.myvelog;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyvelogApplication {

	public static void main(String[] args) throws InterruptedException, JsonProcessingException {
		SpringApplication.run(MyvelogApplication.class, args);
	}

}
