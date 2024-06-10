package com.cypods.geBuddy;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class GeSearchResultLabel {
    Image image;
    private Label label = new Label("");
    private String labelInstanceName;
    private ImageView labelImage = new ImageView(image);
    private Integer translateX;
    private Integer translateY;

    GeSearchResultLabel(){
    }

    GeSearchResultLabel(String labelInstanceName){
        this.labelInstanceName = labelInstanceName;
    }


    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getLabelInstanceName() {
        return labelInstanceName;
    }

    public void setLabelInstanceName(String labelInstanceName) {
        this.labelInstanceName = labelInstanceName;
    }

    public ImageView getLabelImage() {
        return labelImage;
    }

    public void setLabelImage(ImageView labelImage) {
        this.labelImage = labelImage;
    }

    public void setLabelLocation(int x , int y, Pane geSearchAreaPane){
        getLabel().layoutXProperty().bind(geSearchAreaPane.layoutXProperty().add(x));
        getLabel().layoutYProperty().bind(geSearchAreaPane.layoutYProperty().add(y));
//        getLabel().setTranslateX(x);
//        getLabel().setTranslateY(y);
    }
}
