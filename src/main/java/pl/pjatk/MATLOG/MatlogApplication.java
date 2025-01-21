package pl.pjatk.MATLOG;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories
public class MatlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatlogApplication.class, args);
	}

}
