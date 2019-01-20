package dev.mateusz.barber.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MybarberappApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MybarberappApplication.class, args);
	}
}

