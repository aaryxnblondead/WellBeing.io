package com.pages;

import com.contracts.Page;
import com.elements.Root;
import com.elements.SuperLabel;
import com.models.Note;
import com.service.DataBaseAccess;
import com.service.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class DiaryPage extends Page {
    private final int pageNumber;

    public DiaryPage(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public Scene getInterface() {
        int pagesAmount = DataBaseAccess.getDiaryPagesAmount(9);
        HBox pagination = new HBox(15);
        pagination.setStyle("-fx-background-color: #130329;");

        if (pagesAmount != 1) {
            if (pageNumber == 1) {
                Hyperlink nextButton = new Hyperlink(localeRes.getString("forward"));
                nextButton.setTextFill(Color.web("#130329"));
                nextButton.setStyle("-fx-background-color: #FCA7A5;");
                nextButton.setOnAction(event -> PageManager.loadPage(new DiaryPage(pageNumber + 1)));
                pagination.getChildren().add(nextButton);
            } else if (pageNumber == pagesAmount) {
                Hyperlink backButton = new Hyperlink(localeRes.getString("back"));
                backButton.setTextFill(Color.web("#130329"));
                backButton.setStyle("-fx-background-color: #FCA7A5;");
                backButton.setOnAction(event -> PageManager.loadPage(new DiaryPage(pageNumber - 1)));
                pagination.getChildren().add(backButton);
            } else {
                Hyperlink backButton = new Hyperlink(localeRes.getString("back"));
                backButton.setTextFill(Color.web("#130329"));
                backButton.setStyle("-fx-background-color: #FCA7A5;");
                backButton.setOnAction(event -> PageManager.loadPage(new DiaryPage(pageNumber - 1)));

                Hyperlink nextButton = new Hyperlink(localeRes.getString("forward"));
                nextButton.setTextFill(Color.web("#130329"));
                nextButton.setStyle("-fx-background-color: #FCA7A5;");
                nextButton.setOnAction(event -> PageManager.loadPage(new DiaryPage(pageNumber + 1)));

                pagination.getChildren().addAll(backButton, nextButton);
            }
        }
        VBox.setMargin(pagination, new Insets(0, 0, 20, 0));

        List<Note> notes = DataBaseAccess.getNotes(pageNumber, 9);

        VBox notesContainer = new VBox(10);
        notesContainer.setStyle("-fx-background-color: #130329;");

        for (Note note : notes) {
            VBox box = new VBox();
            box.setSpacing(5);
            box.setStyle("-fx-background-color: #130329;");

            note.setDiaryPage(pageNumber);

            SuperLabel date = new SuperLabel(note.getStringDate());
            date.makeGrey();
            date.setTextFill(Color.web("#A76286"));
            SuperLabel title = new SuperLabel(note.getShortTitle());
            title.setTextFill(Color.web("#FCA7A5"));
            Hyperlink watch = new Hyperlink(localeRes.getString("view"));
            watch.setTextFill(Color.web("#130329"));
            watch.setStyle("-fx-background-color: #FCA7A5;");
            watch.setOnAction(event -> PageManager.loadPage(new NotePage(note)));
            box.getChildren().addAll(date, title, watch);

            notesContainer.getChildren().add(box);
        }

        SuperLabel title = new SuperLabel(localeRes.getString("diary"));
        title.makeTitle();
        title.setTextFill(Color.web("#FCA7A5"));

        SuperLabel pageNumber = new SuperLabel(localeRes.getString("page") + " №" + this.pageNumber);
        pageNumber.makeGrey();
        pageNumber.setTextFill(Color.web("#A76286"));

        VBox container = new VBox(title, pageNumber, pagination, notesContainer);
        container.setStyle("-fx-background-color: #130329;");
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #130329; -fx-background-color: #130329;");

        Root root = new Root();
        root.setToTopCenter(scrollPane);
        root.setMenuBar();
        root.setStyle("-fx-background-color: #130329;");

        Scene scene = new Scene(root);
        scene.setFill(Color.web("#130329"));
        return scene;
    }
}