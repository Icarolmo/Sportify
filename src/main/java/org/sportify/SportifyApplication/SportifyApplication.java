package org.sportify.SportifyApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Swagger Sportfy", version = "1", description = "API do Icão o brabo"))
public class SportifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportifyApplication.class, args);
	}

}
