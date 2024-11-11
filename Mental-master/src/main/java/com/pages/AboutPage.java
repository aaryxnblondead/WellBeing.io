package com.pages;

import com.contracts.Page;
import com.elements.Root;
import com.elements.SuperLabel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AboutPage extends Page {
    @Override
    public Scene getInterface() {
        Image icon = new Image("img/icon.png");
        ImageView imageView = new ImageView(icon);
        SuperLabel title = new SuperLabel("Well-Being 1.0.2024");
        title.makeTitle();
        title.setTextFill(Color.web("#FCA7A5"));
        title.setStyle("-fx-control-inner-background: #A76286;");

        VBox container = new VBox(imageView, title);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);
        container.setStyle("-fx-background-color: #130329; -fx-control-inner-background: #5A3E79;");

        Root root = new Root();
        root.setMenuBar();
        root.setToCenter(container);

        return new Scene(root);
    }
}