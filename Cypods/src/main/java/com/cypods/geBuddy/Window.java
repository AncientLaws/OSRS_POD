package com.cypods.geBuddy;

import com.cypods.dbupdater.DatabaseUpdater;
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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import java.awt.image.*;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

import static com.cypods.geBuddy.ApplicationConstant.BORDERS;

@Component
@ComponentScan(basePackages = {"com.cypods.geBuddy", "com.cypods.dbupdater"})
public class Window extends Application {
	
	public static AnchorPane root = new AnchorPane();
	Scene scene;
	Button bt  = new Button("");
	public static Stage primaryStage;

	@Autowired
	public DatabaseUpdater databaseUpdater;

	@Autowired
	public  ConfigurableApplicationContext ac ;

    DisplayController dc; 

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage =  primaryStage;

			primaryStage.setTitle("Grand Exchange Central - By Ancient laws");
			Image icon = new Image 	(getClass().getClassLoader().getResource("images/icon.png").toString(), true);
			primaryStage.getIcons().add(icon);
			if(BORDERS) {
				root.setStyle("-fx-border-color: yellow");
			}
			root.setId("Bank-Screen");

			scene = new Scene(root, primaryStage.widthProperty().doubleValue(), 714.0, Color.BEIGE);

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
		}

	}

	public static AnchorPane getRoot() {
		return root;
	}

	public static void setRoot(AnchorPane root) {
		Window.root = root;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public Button getBt() {
		return bt;
	}

	public void setBt(Button bt) {
		this.bt = bt;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Window.primaryStage = primaryStage;
	}

	public DatabaseUpdater getDatabaseUpdater() {
		return databaseUpdater;
	}

	public void setDatabaseUpdater(DatabaseUpdater databaseUpdater) {
		this.databaseUpdater = databaseUpdater;
	}

	public ConfigurableApplicationContext getAc() {
		return ac;
	}

	public void setAc(ConfigurableApplicationContext ac) {
		this.ac = ac;
	}

	public DisplayController getDc() {
		return dc;
	}

	public void setDc(DisplayController dc) {
		this.dc = dc;
	}
}