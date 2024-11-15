package com.pages;

import com.Mental;
import com.contracts.Page;
import com.elements.Root;
import com.elements.SuperLabel;
import com.models.Locale;
import com.models.User;
import com.service.AccountManager;
import com.service.PageManager;
import com.service.SettingsManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.scene.paint.Color;

public class SettingsPage extends Page {
    private final VBox languageBox = new VBox(10);
    private final VBox securityBox = new VBox(10);
    private final VBox notificationsBox = new VBox(10);

    @Override
    public Scene getInterface() {
        SuperLabel title = new SuperLabel(localeRes.getString("settings"));
        title.makeTitle();
        Hyperlink languageButton = new Hyperlink(localeRes.getString("language"));
        languageButton.setOnAction(event -> {
            deactivateAll();
            getLanguageBox();
        });

        Hyperlink securityButton = new Hyperlink(localeRes.getString("security"));
        securityButton.setOnAction(event -> {
            deactivateAll();
            getSecurityBox();
        });

        Hyperlink notificationButton = new Hyperlink(localeRes.getString("notifications"));
        notificationButton.setOnAction(event -> {
            deactivateAll();
            getNotificationsBox();
        });

        SuperLabel explanation = new SuperLabel(localeRes.getString("settings_explanation"));
        explanation.setWrapText(true);

        VBox menuBox = new VBox(5);
        menuBox.setMinWidth(300);
        menuBox.getChildren().addAll(title, languageButton, securityButton,
                notificationButton, explanation);

        VBox settingsBox = new VBox(languageBox, securityBox, notificationsBox);
        settingsBox.setMinWidth(300);

        HBox container = new HBox(menuBox, settingsBox);
        container.setSpacing(20);

        Root root = new Root();
        root.setToTopCenter(container);
        root.setMenuBar();

        Scene scene = new Scene(root);
        scene.getRoot().setStyle("-fx-background-color: #130329; -fx-text-fill: #FCA7A5;");
        menuBox.setStyle("-fx-text-fill: #FCA7A5;");
        settingsBox.setStyle("-fx-text-fill: #FCA7A5;");
        title.setStyle("-fx-text-fill: #FCA7A5;");
        explanation.setStyle("-fx-text-fill: #FCA7A5;");
        languageButton.setStyle("-fx-text-fill: #FCA7A5;");
        securityButton.setStyle("-fx-text-fill: #FCA7A5;");
        notificationButton.setStyle("-fx-text-fill: #FCA7A5;");

        return scene;
    }

    private void getLanguageBox() {
        SuperLabel title = new SuperLabel(localeRes.getString("app_language"));
        title.setStyle("-fx-text-fill: #FCA7A5;");

        ComboBox<Locale> chooseLanguage = new ComboBox<>();
        chooseLanguage.getItems().addAll(Locale.values());
        chooseLanguage.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locale locale) {
                return locale.getName();
            }

            @Override
            public Locale fromString(String string) {
                return null;
            }
        });
        chooseLanguage.setValue(Mental.APP_LOCALE);
        chooseLanguage.setMinWidth(300);
        chooseLanguage.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");

        Hyperlink saveButton = new Hyperlink(localeRes.getString("save"));
        saveButton.setStyle("-fx-text-fill: #FCA7A5;");

        saveButton.setOnAction(event -> {
            if (chooseLanguage.getSelectionModel().getSelectedItem() != null) {
                SettingsManager.setLocale(chooseLanguage.getValue());
                PageManager.showNotification(localeRes.getString("language_has_been_changed"));
            }
        });

        languageBox.getChildren().addAll(title, chooseLanguage, saveButton);
        languageBox.setStyle("-fx-text-fill: #FCA7A5;");
    }

    public void getSecurityBox() {
        SuperLabel currentPasswordLabel = new SuperLabel(localeRes.getString("current_password"));
        currentPasswordLabel.setStyle("-fx-text-fill: #FCA7A5;");
        PasswordField currentPassword = new PasswordField();
        currentPassword.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");

        SuperLabel newPasswordLabel = new SuperLabel(localeRes.getString("new_password"));
        newPasswordLabel.setStyle("-fx-text-fill: #FCA7A5;");
        TextField newPassword = new TextField();
        newPassword.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");

        Hyperlink saveButton = new Hyperlink(localeRes.getString("save"));
        saveButton.setStyle("-fx-text-fill: #FCA7A5;");
        saveButton.setOnAction(event -> {
            if (currentPassword.getText().equals(AccountManager.getUser().getPassword())) {
                if (newPassword.getText().length() >= 8) {
                    User user = AccountManager.getUser();
                    user.setPassword(newPassword.getText());
                    AccountManager.updatePassword(newPassword.getText());

                    currentPassword.setText(null);
                    newPassword.setText(null);
                    PageManager.showNotification(localeRes.getString("password_has_been_updated"));
                } else {
                    PageManager.showNotification(localeRes.getString("password_too_short"));
                }
            } else {
                PageManager.showNotification(localeRes.getString("password_is_not_correct"));
            }
        });

        securityBox.getChildren().addAll(currentPasswordLabel, currentPassword,
                newPasswordLabel, newPassword, saveButton);
        securityBox.setStyle("-fx-text-fill: #FCA7A5;");
    }

    public void getNotificationsBox() {
        CheckBox notifyMe = new CheckBox(localeRes.getString("notify_me"));
        notifyMe.setSelected(SettingsManager.getShowNotification());
        notifyMe.setStyle("-fx-text-fill: #FCA7A5;");

        Hyperlink saveButton = new Hyperlink(localeRes.getString("save"));
        saveButton.setStyle("-fx-text-fill: #FCA7A5;");
        saveButton.setOnAction(event -> {
            SettingsManager.setShowNotification(notifyMe.isSelected());

            if (notifyMe.isSelected()) {
                PageManager.showNotification(localeRes.getString("you_will_receive_reminder"));
            } else {
                PageManager.showNotification(localeRes.getString("you_will_not_receive_reminder"));
            }
        });

        notificationsBox.getChildren().addAll(notifyMe, saveButton);
        notificationsBox.setStyle("-fx-text-fill: #FCA7A5;");
    }

    private void deactivateAll() {
        languageBox.getChildren().clear();
        securityBox.getChildren().clear();
        notificationsBox.getChildren().clear();
    }
}