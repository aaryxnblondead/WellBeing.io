package com.pages;

import com.contracts.Page;
import com.elements.Root;
import com.elements.SuperLabel;
import com.models.User;
import com.service.AccountManager;
import com.service.PageManager;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EditProfilePage extends Page {
    private final User user;
    public EditProfilePage() {
        this.user = AccountManager.getUser();
    }

    @Override
    public Scene getInterface() {
        SuperLabel secondNameLabel = new SuperLabel(localeRes.getString("surname"));
        TextField secondNameInput = new TextField(user.getSecondName());
        secondNameLabel.setTextFill(Color.web("#FCA7A5"));
        secondNameInput.setStyle("-fx-text-fill: #FCA7A5; -fx-background-color: #5A3E79; -fx-control-inner-background: #5A3E79;");

        SuperLabel nameLabel = new SuperLabel(localeRes.getString("name"));
        TextField nameInput = new TextField(user.getName());
        nameLabel.setTextFill(Color.web("#FCA7A5"));
        nameInput.setStyle("-fx-text-fill: #FCA7A5; -fx-background-color: #5A3E79; -fx-control-inner-background: #5A3E79;");

        SuperLabel additionalNameLabel = new SuperLabel(localeRes.getString("additional_name"));
        TextField additionalNameInput = new TextField(user.getAdditionalName());
        additionalNameLabel.setTextFill(Color.web("#FCA7A5"));
        additionalNameInput.setStyle("-fx-text-fill: #FCA7A5; -fx-background-color: #5A3E79; -fx-control-inner-background: #5A3E79;");

        Hyperlink saveButton = new Hyperlink(localeRes.getString("save"));
        saveButton.setOnAction(event -> updateUser(nameInput.getText(), secondNameInput.getText(),
                additionalNameInput.getText()));
        saveButton.setTextFill(Color.web("#A76286"));

        Hyperlink exitButton = new Hyperlink(localeRes.getString("exit"));
        exitButton.setOnAction(event -> PageManager.loadPage(new UserPage()));
        exitButton.setTextFill(Color.web("#A76286"));

        HBox buttonsBox = new HBox(saveButton, exitButton);
        buttonsBox.setSpacing(10);

        VBox container = new VBox(secondNameLabel, secondNameInput, nameLabel, nameInput,
                additionalNameLabel, additionalNameInput, buttonsBox);
        container.setSpacing(10);
        container.setMaxWidth(300);
        container.setStyle("-fx-background-color: #130329;");

        Root root = new Root();
        root.setToCenter(container);
        root.setMenuBar();
        root.setStyle("-fx-background-color: #130329;");

        Scene scene = new Scene(root);
        scene.setFill(Color.web("#130329"));
        return scene;
    }

    private void updateUser(String name, String secondName, String additionalName) {
        if (name.isEmpty()) {
            PageManager.showNotification(localeRes.getString("forgot_name"));
        } else {
            User user = new User(name, secondName, additionalName, this.user.getUsername(), this.user.getPassword());
            try {
                AccountManager.updateUser(user);
                PageManager.loadPage(new UserPage());
            } catch (Exception e) {
                PageManager.showNotification(e.getMessage());
            }
        }
    }
}