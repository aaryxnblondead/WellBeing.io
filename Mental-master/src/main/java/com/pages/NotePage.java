package com.pages;

import com.contracts.Page;
import com.elements.Root;
import com.elements.SuperLabel;
import com.models.Note;
import com.service.DataBaseAccess;
import com.service.PageManager;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class NotePage extends Page {
    private final Note note;
    public NotePage(Note note) {
        this.note = note;
    }
    @Override
    public Scene getInterface() {
        SuperLabel date = new SuperLabel(note.getStringDate());
        date.makeGrey();
        SuperLabel content = new SuperLabel(note.getContent());
        content.setWrapText(true);
        content.setTextFill(Color.web("#130329"));
        content.setStyle("-fx-control-inner-background: #FCA7A5;");
        date.setTextFill(Color.web("#130329"));
        date.setStyle("-fx-control-inner-background: #FCA7A5;");

        HBox buttons = new HBox(10);
        Hyperlink deleteButton = new Hyperlink(Page.localeRes.getString("delete"));
        deleteButton.setTextFill(Color.web("#130329"));
        deleteButton.setOnAction(event -> {
            DataBaseAccess.deleteNote(note);

/*
            We decide which diary page to return the user to. If you came to an entry from a
            diary page that had only one entry (we just deleted it), then you need to redirect
            to the previous page. Also, if this was the last entry in the diary, then we redirect
            to the main one. If you came from the main page, then also to the main page
*/

            if (note.getDiaryPage() != null) {
                int pageNumber = note.getDiaryPage();

                List<Note> notes = DataBaseAccess.getNotes(pageNumber, 9);
                if (notes.size() == 0) {
                    if (pageNumber == 1) {
                        PageManager.loadPage(new UserPage());
                    } else {
                        PageManager.loadPage(new DiaryPage(pageNumber - 1));
                    }
                } else {
                    PageManager.loadPage(new DiaryPage(note.getDiaryPage()));
                }
            } else {
                PageManager.loadPage(new UserPage());
            }
        });


        if (note.getDiaryPage() != null) {
            Hyperlink backButton = new Hyperlink(Page.localeRes.getString("back"));
            backButton.setTextFill(Color.web("#130329"));
            backButton.setOnAction(event -> PageManager.loadPage(new DiaryPage(note.getDiaryPage())));
            buttons.getChildren().addAll(backButton, deleteButton);
        } else {
            buttons.getChildren().addAll(deleteButton);
        }

        VBox container = new VBox(date, content, buttons);
        container.setSpacing(10);
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #5A3E79; -fx-background-color: #5A3E79;");
        container.setStyle("-fx-background-color: #5A3E79;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Root root = new Root();
        root.setToTopCenter(scrollPane);
        root.setMenuBar();
        root.setStyle("-fx-background-color: #5A3E79;");

        Scene scene = new Scene(root);
        scene.setFill(Color.web("#5A3E79"));
        return scene;
    }
}