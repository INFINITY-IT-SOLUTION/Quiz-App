package com.quizapp.quiz_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SpringBootApplication
@EnableFeignClients("com.quizapp")
@OpenAPIDefinition(
		info=@Info(title="Quiz Service API",
				version="1.0",
				description="This API is to play Quiz"),
		servers=@Server(
				url="http://localhost:5004/",
				description="This API URL will help to play quiz"

		)
)
public class QuizServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizServiceApplication.class, args);
	}

}
