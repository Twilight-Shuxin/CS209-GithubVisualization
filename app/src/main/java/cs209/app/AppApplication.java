package cs209.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

	public static int pageSize = 2;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
