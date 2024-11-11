package com.pages;

import com.contracts.Page;
import com.elements.Root;
import com.elements.SuperLabel;
import com.service.AccountManager;
import com.service.PageManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LogInPage extends Page {
    @Override
    public Scene getInterface() {
        SuperLabel title = new SuperLabel("WellBeingIO");
        title.makeBold();
        title.setTextFill(Color.web("#FCA7A5"));

        TextField usernameInput = new TextField();
        usernameInput.setPromptText(localeRes.getString("username"));
        usernameInput.setStyle("-fx-text-fill: #FCA7A5; -fx-prompt-text-fill: #A76286; -fx-control-inner-background: #5A3E79; -fx-background-color: #5A3E79;");
        
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText(localeRes.getString("password"));
        passwordInput.setStyle("-fx-text-fill: #FCA7A5; -fx-prompt-text-fill: #A76286; -fx-control-inner-background: #5A3E79; -fx-background-color: #5A3E79;");

        HBox buttonsBlock = new HBox();
        Hyperlink createNewProfileButton = new Hyperlink(localeRes.getString("create_new_profile"));
        createNewProfileButton.setOnAction(event -> PageManager.loadPage(new RegPage()));
        createNewProfileButton.setTextFill(Color.web("#FCA7A5"));

        Hyperlink enterButton = new Hyperlink(localeRes.getString("enter"));
        enterButton.setTextFill(Color.web("#FCA7A5"));
        enterButton.setOnAction(event -> {
            if (usernameInput.getText().isEmpty()) {
                PageManager.showNotification(localeRes.getString("forgot_username_message"));
            } else if (passwordInput.getText().isEmpty()) {
                PageManager.showNotification(localeRes.getString("forgot_password_message"));
            } else {
                try {
                    AccountManager.logIn(usernameInput.getText(), passwordInput.getText());
                } catch (Exception e) {
                    if (e.getMessage().equals("not_found")) {
                        PageManager.showNotification(localeRes.getString("not_found"));
                    } else {
                        PageManager.showNotification(e.getMessage());
                    }
                }

                // If user was found
                if (AccountManager.getUser() != null) {
                    PageManager.loadPage(new UserPage());
                }
            }
        });

        buttonsBlock.getChildren().addAll(createNewProfileButton, enterButton);
        buttonsBlock.setSpacing(10);

        VBox container = new VBox(title, usernameInput, passwordInput, buttonsBlock);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(300);
        container.setSpacing(10);
        container.setStyle("-fx-background-color: #130329;");

        Root root = new Root();
        root.setToCenter(container);
        root.setStyle("-fx-background-color: #130329;");

        Scene scene = new Scene(root);
        scene.setFill(Color.web("#130329"));
        return scene;
    }
}