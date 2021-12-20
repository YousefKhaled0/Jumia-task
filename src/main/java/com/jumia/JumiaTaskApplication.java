package com.jumia;

import com.jumia.exceptions.handler.MVCResponseExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(MVCResponseExceptionHandler.class)
public class JumiaTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumiaTaskApplication.class, args);
	}

}
