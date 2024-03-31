package jmaster.io.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@SpringBootApplication
public class EmailserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailserviceApplication.class, args);
	}

	@Bean
	JsonMessageConverter converter() {
		return new JsonMessageConverter();
	}

}
