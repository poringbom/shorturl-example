package com.bluebik.app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BackendServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendServerApplication.class, args);
	}
}
