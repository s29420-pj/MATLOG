package pl.pjatk.MATLOG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication()
@EnableMongoRepositories
public class MatlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatlogApplication.class, args);
	}

}
