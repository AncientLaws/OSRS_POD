package com.osrs.pod;

import com.osrs.pod.application.Window;
import com.osrs.pod.database.service.DatabaseUpdater;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "com.osrs.pod.database.domain.entities")
public class PodApplication {
    public static void main(String[] args) {
        Application.launch(Window.class, args);
    }

}
