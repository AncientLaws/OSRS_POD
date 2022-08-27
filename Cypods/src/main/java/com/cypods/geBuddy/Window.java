package com.cypods.geBuddy;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


@Component
@ComponentScan("com.cypods.geBuddy")

public class Window extends Application {
	
	public static Pane root = new Pane();
	public static Group group = new Group(root);
	Scene scene;
	Button bt  = new Button("");

	@Autowired
	public  ConfigurableApplicationContext ac;
	
    //@Autowired
    DisplayController dc; 
		
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Grand Exchange Central");
			Image icon = new Image ("/images/icon.png");
			primaryStage.getIcons().add(icon);
			
			root.setPrefSize(1080, 720);
			root.setId("Bank-Screen");
            scene = new Scene(group, 1080, 720,Color.BEIGE);
          
            dc = ac.getBean(DisplayController.class);
			dc.getTab();
			dc.setListeners();
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(false);
			
			primaryStage.setResizable(false);
			primaryStage.show();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void init() throws Exception{
		ac = new SpringApplicationBuilder(Window.class).run();
		System.out.println("Init");
	}
	
	@Override
	public void stop() throws Exception{
		ac.close();
		Platform.exit();
	}
	static class StageReadyEvent extends ApplicationEvent {

		public StageReadyEvent(Stage primaryStage) {
			super(primaryStage);
			// TODO Auto-generated constructor stub
		}

	}

}

//VBOX to add list of items in ge after search?