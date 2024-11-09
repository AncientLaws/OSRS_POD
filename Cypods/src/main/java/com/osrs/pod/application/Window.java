package com.osrs.pod.application;


import com.osrs.pod.PodApplication;
import com.osrs.pod.application.controllers.DisplayController;
import com.osrs.pod.database.controller.DatabaseUpdaterController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import static com.osrs.pod.application.ApplicationConstant.BORDERS;
@Component
public class Window extends Application {

	private static ApplicationContext context;
	public static AnchorPane root = new AnchorPane();
	Scene scene;
	Button bt  = new Button("");
	public static Stage primaryStage;
	public ConfigurableApplicationContext ac ;

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

			dc = new DisplayController();
			dc.getTab();
			dc.setListeners();

			scene.getStylesheets().add((getClass().getResource("/application.css")).toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(false);
			
			primaryStage.setResizable(true);
			primaryStage.show();

			ac.getBean(DatabaseUpdaterController.class);

	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void init() throws Exception{
//		ac = new SpringApplicationBuilder(Window.class).run();
		ac = new SpringApplicationBuilder(PodApplication.class).run();
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