package com.thoughtworks.basicQuiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// GTB: - 包名一般小写，Controller、Service 等改成小写

@SpringBootApplication
public class BasicQuizApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicQuizApplication.class, args);
	}

}
