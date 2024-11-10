package com;

import com.contracts.Page;
import com.models.Locale;
import com.pages.LogInPage;
import com.service.PageManager;
import com.service.ResourceManager;
import com.service.SettingsManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Mental extends Application {
    private final static int WINDOW_HEIGHT = 660;
    private final static int WINDOW_WIDTH = 960;
    public final static Locale APP_LOCALE = SettingsManager.getLocale();
    @Override
    public void start(Stage stage) {
        stage.setTitle("Mental");

        Image icon = new Image(ResourceManager.loadImage("/img/icon.png"));
        stage.getIcons().add(icon);

        Page.localeRes = ResourceBundle.getBundle(APP_LOCALE.getFile());

        PageManager.setStage(stage);
        PageManager.setWindowSize(WINDOW_HEIGHT, WINDOW_WIDTH);
        PageManager.loadPage(new LogInPage());
    }

    public static void main(String[] args) {
        launch();
    }
}