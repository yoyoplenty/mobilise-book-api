package Mobilise.bookapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BookApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookApiApplication.class, args);

		System.out.println("Mobilise Book API started successfully");
	}
}
