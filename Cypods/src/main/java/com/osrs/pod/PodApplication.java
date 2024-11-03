package com.osrs.pod;

import com.osrs.pod.application.Window;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.osrs.pod.database.domain.entities")
public class PodApplication {

    public static void main(String[] args) {
        Application.launch(Window.class, args);
    }

}
