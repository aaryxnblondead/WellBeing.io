package com.pages;

import com.contracts.Page;
import com.elements.Diary;
import com.elements.Mood;
import com.elements.Profile;
import com.elements.Root;
import com.models.User;
import com.service.AccountManager;
import com.service.TimeObserver;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UserPage extends Page {
    private final User user = AccountManager.getUser();

    @Override
    public Scene getInterface() {
        Profile profile = new Profile(user);
        Diary diary = new Diary();

        Mood mood = new Mood();
        TimeObserver timeObserver = new TimeObserver();
        timeObserver.addWidget(mood);
        timeObserver.startTimerTask();

        VBox leftContainer = new VBox(profile, diary);
        leftContainer.setSpacing(20);
        leftContainer.setStyle("-fx-background-color: #130329; -fx-text-fill: #FCA7A5; -fx-control-inner-background: #A76286;");

        VBox rightContainer = new VBox(mood);
        rightContainer.setSpacing(20);
        rightContainer.setStyle("-fx-background-color: #130329; -fx-text-fill: #FCA7A5; -fx-control-inner-background: #A76286;");

        HBox container = new HBox(leftContainer, rightContainer);
        container.setSpacing(70);
        container.setStyle("-fx-background-color: #130329; -fx-text-fill: #FCA7A5; -fx-control-inner-background: #A76286;");

        Root root = new Root();
        root.setToTopCenter(container);
        root.setMenuBar();
        root.setStyle("-fx-background-color: #130329; -fx-text-fill: #FCA7A5; -fx-control-inner-background: #A76286;");

        Scene scene = new Scene(root);
        scene.setFill(Color.web("#130329"));
        return scene;
    }
}