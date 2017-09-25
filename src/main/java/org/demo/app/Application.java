package org.demo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application.java
 * 
 * @author jay
 */
@SpringBootApplication
@ComponentScan("org.demo")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
