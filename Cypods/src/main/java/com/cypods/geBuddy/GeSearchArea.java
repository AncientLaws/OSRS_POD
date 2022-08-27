package com.cypods.geBuddy;

import org.springframework.stereotype.Component;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//@Component
public class GeSearchArea extends PaneInterface {

ImageView geSearch;

	protected void pane_drawItemScrollArea() {
		geSearch = new ImageView(new Image("/images/GE_SEARCH_V6.png"));
		tabInterface.setTranslateX(0);
		tabInterface.setTranslateY(91);
		geSearch.setX(0);
		geSearch.setY(405);
		geSearch.setFitWidth(750);
		geSearch.setFitHeight(225);
		geSearch.setRotate(180);
		tabInterface.getChildren().add(geSearch);
	}	
	

}
