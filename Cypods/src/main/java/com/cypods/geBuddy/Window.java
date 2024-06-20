package com.cypods.geBuddy;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import java.awt.image.*;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

@Component
@ComponentScan("com.cypods.geBuddy")

public class Window extends Application {
	
	public static AnchorPane root = new AnchorPane();
//	public static Group group = new Group(root);
	Scene scene;
	Button bt  = new Button("");
	public static Stage primaryStage;

	double stageWidth;
	double stageheight;

	@Autowired
	public  ConfigurableApplicationContext ac;
	
    //@Autowired
    DisplayController dc; 

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage =  primaryStage;

			primaryStage.setTitle("Grand Exchange Central - By Ancient laws");
			Image icon = new Image 	(getClass().getClassLoader().getResource("images/icon.png").toString(), true);
			primaryStage.getIcons().add(icon);
			root.setStyle("-fx-border-color: yellow");

//			Platform.runLater(() -> {
//				stageWidth = 750;//primaryStage.getWidth();
//				stageheight = 1080;//primaryStage.getHeight();
//				root.setPrefSize( stageWidth,stageheight-28);
//			});
			root.setId("Bank-Screen");
            
			scene = new Scene(root, primaryStage.widthProperty().doubleValue(), 719.0,Color.BEIGE);
//			scene = new Scene(group);
			//root.autosize();
//			root.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight()-28);
//			group.prefWidth(primaryStage.widthProperty().doubleValue());
            dc = ac.getBean(DisplayController.class);
			dc.getTab();
			dc.setListeners();


			scene.getStylesheets().add((getClass().getResource("/application.css")).toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(false);
			
			primaryStage.setResizable(true);
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