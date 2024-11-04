package pl.pjatk.MATLOG;

import org.springframework.boot.SpringApplication;

public class TestMatlogApplication {

	public static void main(String[] args) {
		SpringApplication.from(MatlogApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
