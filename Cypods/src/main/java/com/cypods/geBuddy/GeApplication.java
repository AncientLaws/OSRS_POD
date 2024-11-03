package com.cypods.geBuddy;

import javafx.application.Application;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class }, scanBasePackages = {"com.cypods.geBuddy", "com.cypods.dbupdater"})
public class GeApplication {
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static void main(String[] args) {
		Application.launch(Window.class, args);
	}

}