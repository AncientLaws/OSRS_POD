package com.cypods.geBuddy;

import javafx.application.Application;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan({"com.*"})
@EntityScan({"com.cypods.geBuddy.Database"})
public class GeApplication {
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		//SpringApplication.run(GeApplication.class, args);
		ApplicationContext context;
		Application.launch(Window.class, args);
	}

}