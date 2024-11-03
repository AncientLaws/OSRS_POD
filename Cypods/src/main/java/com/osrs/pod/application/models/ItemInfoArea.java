package com.osrs.pod.application.models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Component;

import static com.osrs.pod.application.ApplicationConstant.*;

public class ItemInfoArea {

    VBox vBox = new VBox();
    GridPane itemGridPane = new GridPane();
    GridPane gridPane  = new GridPane();

    ImageView itemIconPaneImage;
    ImageView inventory;
    HBox imageContainer;
    Label id;
    Label description;
    Label members;
    Label currentPrice_bigLabel;
    Label currentPrice_priceLabel;
    Label currentPrice_descLabel;
    Label todayPrice;
    Label day30_change;
    Label day90_change;
    Label day180_change;

    Label changeToday;
    Label change30Days;
    Label change90Days;
    Label change180Days;

    Rectangle clipRect = new Rectangle();

    public ItemInfoArea(){
        drawInventoryMenu();
        layoutSettings();

    }

    private void layoutSettings(){
        //Setting size for the pane
        gridPane.setMinSize(307, 165);
        gridPane.setMaxSize(307, 165);

        if(transparentBackground){
            vBox.setMinSize(307,232);
            vBox.setMaxSize(307,232);
        }
        else{
            vBox.setMinSize(309,232);
            vBox.setMaxSize(309,232);
        }
//        vBox.setStyle("-fx-border-color: rgba(90, 82, 66, 1); -fx-border-width: 2;");

        //Setting the padding
        gridPane.setPadding(new Insets(0, 10, 5, 15));
        itemGridPane.setPadding(new Insets(0, 10, 0, 10));

        //Setting the vertical and horizontal gaps between the columns
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        itemGridPane.setVgap(0);
        itemGridPane.setHgap(5);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.BASELINE_LEFT);
        itemGridPane.setAlignment(Pos.BASELINE_LEFT);

        clipRect.widthProperty().bind(vBox.widthProperty());
        clipRect.heightProperty().bind(vBox.heightProperty());
        vBox.setClip(clipRect);

        vBox.getChildren().addAll(itemGridPane,gridPane);
    }

    private void clearLabels(){
        if(DEBUG == true) {System.out.println("pane_initLabels");}
        if(BORDERS){
            gridPane.setStyle("-fx-border-color: cyan");
            itemGridPane.setStyle("-fx-border-color: cyan");
            vBox.setStyle("-fx-border-color: green");
        }
        gridPane.getChildren().removeAll(currentPrice_bigLabel,currentPrice_priceLabel,changeToday,todayPrice,change30Days,day30_change
                                            ,change90Days,day90_change,change180Days,day180_change);
        itemGridPane.getChildren().removeAll(description);

        currentPrice_bigLabel = new Label(null);
        currentPrice_bigLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold");
        currentPrice_bigLabel.getStyleClass().add("labelAll");

        id = new Label("");

        description = new Label(null);
        description.setWrapText(true);
        description.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        description.setMaxWidth(260);
        description.getStyleClass().add("labelAll");
        itemGridPane.add(description,1,0);

        members = new Label("");

        currentPrice_descLabel = new Label("Current Price: ");
        currentPrice_descLabel.setStyle("-fx-text-fill: orange;");
        currentPrice_descLabel.getStyleClass().add("labelAll");
        gridPane.add(currentPrice_descLabel,0,0);

        currentPrice_priceLabel = new Label("null");
        currentPrice_priceLabel.getStyleClass().add("labelAll");
        gridPane.add(currentPrice_priceLabel,1,0);

        changeToday = new Label("Change today:");
        changeToday.setStyle("-fx-text-fill: orange;");
        changeToday.getStyleClass().add("labelAll");
        gridPane.add(changeToday,0,1);

        todayPrice = new Label(null);
        todayPrice.getStyleClass().add("labelAll");
        gridPane.add(todayPrice,1,1);

        change30Days = new Label("30 days: ");
        change30Days.setStyle("-fx-text-fill: orange;");
        change30Days.getStyleClass().add("labelAll");
        gridPane.add(change30Days,0,2);

        day30_change = new Label(null);
        day30_change.getStyleClass().add("labelAll");
        gridPane.add(day30_change,1,2);

        change90Days = new Label("90 days: ");
        change90Days.setStyle("-fx-text-fill: orange;");
        change90Days.getStyleClass().add("labelAll");
        gridPane.add(change90Days,0,3);

        day90_change = new Label(null);
        day90_change.getStyleClass().add("labelAll");
        gridPane.add(day90_change,1,3);

        change180Days = new Label("180 days: ");
        change180Days.setStyle("-fx-text-fill: orange;");
        change180Days.getStyleClass().add("labelAll");
        gridPane.add(change180Days,0,4);

        day180_change = new Label(null);
        day180_change.getStyleClass().add("labelAll");
        gridPane.add(day180_change,1,4);
    }

    public void updateLabels(String name1, String id1, String description1, String members1, String currentPrice1,
                             String currentTrend1, String todayPrice1, String todayTrend1, String day30_trend1, String day30_change1,
                             String day90_trend1, String day90_change1, String day180_trend1, String day180_change1) {

        clearLabels();
        getDescription().setText(description1);
        getCurrentPrice_bigLabel().setText(currentPrice1);
        getCurrentPrice_priceLabel().setText(currentPrice1);

        if (members1.equals("true")) {
            getMembers().setText("Members");
        } else {
            getMembers().setText("Free-to-Play");
        }

        if (todayTrend1.equals("negative")) {
            getTodayPrice().setText(todayPrice1);
            getTodayPrice().setStyle("-fx-text-fill: red;");
        } else {
            getTodayPrice().setText(todayPrice1);
            getTodayPrice().setStyle("-fx-text-fill: rgb(0,255,0);");
        }
        ;

        if (day30_trend1.equals("negative")) {
            getDay30_change().setText(day30_change1);
            getDay30_change().setStyle("-fx-text-fill: red;");
        } else {
            getDay30_change().setText(day30_change1);
            getDay30_change().setStyle("-fx-text-fill: rgb(0,255,0);");
        }
        ;

        if (day90_trend1.equals("negative")) {
            getDay90_change().setText(day90_change1);
            getDay90_change().setStyle("-fx-text-fill: red;");
        } else {
            getDay90_change().setText(day90_change1);
            getDay90_change().setStyle("-fx-text-fill: rgb(0,255,0);");
        }
        ;

        if (day180_trend1.equals("negative")) {
            getDay180_change().setText(day180_change1);
            getDay180_change().setStyle("-fx-text-fill: red;");
        } else {
            getDay180_change().setText(day180_change1);
            getDay180_change().setStyle("-fx-text-fill: rgb(0,255,0);");
        }
        ;

        if (currentPrice1.equals("negative")) {
            getCurrentPrice_priceLabel().setText(currentPrice1);
            getCurrentPrice_priceLabel().setStyle("-fx-text-fill: white;");
        } else {
            getCurrentPrice_priceLabel().setText(currentPrice1);
            getCurrentPrice_priceLabel().setStyle("-fx-text-fill: white;");
        }
        ;

    }

    private void drawInventoryMenu() {
        if(DEBUG == true) {System.out.println("pane_drawInventoryMenu");}
        if(BORDERS){
            gridPane.setGridLinesVisible(true);
        }
          vBox.setStyle("-fx-background-color: linear-gradient(to right, rgba(95, 73, 43,.4) 100%, rgba(95, 73, 43,.4) 100%); -fx-border-color: rgba(90, 82, 66, 1); -fx-border-width: 2;");
    }

    public void setSelectedItemIcon(Image input){
        if(getItemGridPane().getChildren().contains(imageContainer)){
            getItemGridPane().getChildren().remove(imageContainer);
        }
        try {
            if(DEBUG == true) {System.out.println("setItemTopMenu InputStream: " + input);}
            itemIconPaneImage = new ImageView(input);
            itemIconPaneImage.setPreserveRatio(true);
            itemIconPaneImage.setFitHeight(50);
            itemIconPaneImage.setFitWidth(50);
            imageContainer = new HBox(itemIconPaneImage);
            imageContainer.setPadding(Insets.EMPTY);
            imageContainer.setSpacing(0);
            itemGridPane.add(imageContainer,0,0);
            itemIconPaneImage.setOnMousePressed((mouseEvent) -> System.out.println("Teeehee clicked me"));
            if(DEBUG == true) {System.out.println("END - pane_setItemTopMenu(Image input)");}
        } catch (Exception e) {
            System.out.println("ERROR - pane_setItemTopMenu(Image input)");
        }
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    public GridPane getItemGridPane() {
        return itemGridPane;
    }

    public void setItemGridPane(GridPane itemGridPane) {
        this.itemGridPane = itemGridPane;
    }

    public ImageView getInventory() {
        return inventory;
    }

    public void setInventory(ImageView inventory) {
        this.inventory = inventory;
    }

    public HBox getImageContainer() {
        return imageContainer;
    }

    public void setImageContainer(HBox imageContainer) {
        this.imageContainer = imageContainer;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(GridPane gridPane) {
        this.gridPane = gridPane;
    }

    public ImageView getItemIconPaneImage() {
        return itemIconPaneImage;
    }

    public void setItemIconPaneImage(ImageView itemIconPaneImage) {
        this.itemIconPaneImage = itemIconPaneImage;
    }

    public Label getId() {
        return id;
    }

    public void setId(Label id) {
        this.id = id;
    }

    public Label getDescription() {
        return description;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public Label getMembers() {
        return members;
    }

    public void setMembers(Label members) {
        this.members = members;
    }

    public Label getCurrentPrice_bigLabel() {
        return currentPrice_bigLabel;
    }

    public void setCurrentPrice_bigLabel(Label currentPrice_bigLabel) {
        this.currentPrice_bigLabel = currentPrice_bigLabel;
    }

    public Label getCurrentPrice_priceLabel() {
        return currentPrice_priceLabel;
    }

    public void setCurrentPrice_priceLabel(Label currentPrice_priceLabel) {
        this.currentPrice_priceLabel = currentPrice_priceLabel;
    }

    public Label getCurrentPrice_descLabel() {
        return currentPrice_descLabel;
    }

    public void setCurrentPrice_descLabel(Label currentPrice_descLabel) {
        this.currentPrice_descLabel = currentPrice_descLabel;
    }

    public Label getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(Label todayPrice) {
        this.todayPrice = todayPrice;
    }

    public Label getDay30_change() {
        return day30_change;
    }

    public void setDay30_change(Label day30_change) {
        this.day30_change = day30_change;
    }

    public Label getDay90_change() {
        return day90_change;
    }

    public void setDay90_change(Label day90_change) {
        this.day90_change = day90_change;
    }

    public Label getDay180_change() {
        return day180_change;
    }

    public void setDay180_change(Label day180_change) {
        this.day180_change = day180_change;
    }

    public Label getChangeToday() {
        return changeToday;
    }

    public void setChangeToday(Label changeToday) {
        this.changeToday = changeToday;
    }

    public Label getChange30Days() {
        return change30Days;
    }

    public void setChange30Days(Label change30Days) {
        this.change30Days = change30Days;
    }

    public Label getChange90Days() {
        return change90Days;
    }

    public void setChange90Days(Label change90Days) {
        this.change90Days = change90Days;
    }

    public Label getChange180Days() {
        return change180Days;
    }

    public void setChange180Days(Label change180Days) {
        this.change180Days = change180Days;
    }

    public Rectangle getClipRect() {
        return clipRect;
    }

    public void setClipRect(Rectangle clipRect) {
        this.clipRect = clipRect;
    }
}
