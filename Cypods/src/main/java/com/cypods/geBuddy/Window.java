package com.cypods.geBuddy;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@ComponentScan("com.cypods.geBuddy")

public class Window extends Application {
	
	public static Pane root = new Pane();
	public static Group group = new Group(root);
	Scene scene;
	Button bt  = new Button("");
	public static Stage primaryStage;

	@Autowired
	public  ConfigurableApplicationContext ac;
	
    //@Autowired
    DisplayController dc; 

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage =  primaryStage;
			primaryStage.setTitle("Grand Exchange Central");
			Image icon = new Image 	(getClass().getClassLoader().getResource("images/icon.png").toString(), true);
			primaryStage.getIcons().add(icon);
			//primaryStage.setWidth(1080);
			//primaryStage.setHeight(720);
			
			root.setPrefSize(primaryStage.getWidth(),primaryStage.getHeight()-28);
			
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	root.setPrefSize( primaryStage.getWidth(),primaryStage.getHeight()-28);
			    	System.out.println("PrimaryStage Height: "+ (primaryStage.getHeight()-28)+ " PrimaryStage Width: " + primaryStage.getWidth());
					root.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight()-28);
					group.prefWidth(primaryStage.getWidth());

			    	//scene = new Scene(group, primaryStage.getWidth(), primaryStage.getHeight()-28,Color.BEIGE);
			    }
			});
			root.setId("Bank-Screen");
            
			scene = new Scene(group, primaryStage.getWidth(), primaryStage.getHeight()-28,Color.BEIGE);
			//root.autosize();
			root.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight()-28);
			group.prefWidth(primaryStage.getWidth());
            dc = ac.getBean(DisplayController.class);
			dc.getTab();
			dc.setListeners();

			
			//scene.getStylesheets().add((getClass().getResource("application.css")).toExternalForm());
			scene.getStylesheets().add((getClass().getResource("/application.css")).toExternalForm());
			//scene.getStylesheets().add("application.css");
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