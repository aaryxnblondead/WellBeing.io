package com.pages;

import com.contracts.Page;
import com.elements.Root;
import com.models.Mood;
import com.service.DataBaseAccess;
import com.service.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.scene.paint.Color;

import java.util.Collections;
import java.util.List;

public class MoodStatPage extends Page {
    private final VBox moodChart = new VBox();
    private final int pageNumber;

    public MoodStatPage(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public Scene getInterface() {
        List<Mood> moodHistory = DataBaseAccess.getMoodHistory(pageNumber, 30);
        getMoodChart(moodHistory);

        int pagesAmount = DataBaseAccess.getMoodChartPagesAmount(30);
        HBox pagination = new HBox(15);

        if (pagesAmount != 1) {
            if (pageNumber == 1) {
                Hyperlink prevButton = new Hyperlink(localeRes.getString("prev_30_days"));
                prevButton.setTextFill(Color.web("#FCA7A5"));
                prevButton.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");
                prevButton.setOnAction(event -> PageManager.loadPage(new MoodStatPage(pageNumber + 1)));
                pagination.getChildren().add(prevButton);
            } else if (pageNumber == pagesAmount) {
                Hyperlink nextButton = new Hyperlink(localeRes.getString("next_30_days"));
                nextButton.setTextFill(Color.web("#FCA7A5"));
                nextButton.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");
                nextButton.setOnAction(event -> PageManager.loadPage(new MoodStatPage(pageNumber - 1)));
                pagination.getChildren().add(nextButton);
            } else {
                Hyperlink nextButton = new Hyperlink(localeRes.getString("next_30_days"));
                nextButton.setTextFill(Color.web("#FCA7A5"));
                nextButton.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");
                nextButton.setOnAction(event -> PageManager.loadPage(new MoodStatPage(pageNumber - 1)));

                Hyperlink prevButton = new Hyperlink(localeRes.getString("prev_30_days"));
                prevButton.setTextFill(Color.web("#FCA7A5"));
                prevButton.setStyle("-fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");
                prevButton.setOnAction(event -> PageManager.loadPage(new MoodStatPage(pageNumber + 1)));

                pagination.getChildren().addAll(nextButton, prevButton);
            }
        }
        VBox.setMargin(pagination, new Insets(0, 0, 20, 0));

        VBox container = new VBox(pagination, moodChart);
        container.setStyle("-fx-background-color: #130329; -fx-control-inner-background: #5A3E79;");

        Root root = new Root();
        root.setMenuBar();
        root.setToTopCenter(container);
        Scene scene = new Scene(root);
        scene.setFill(Color.web("#130329"));
        return scene;
    }

    public void getMoodChart(List<Mood> moodHistory) {
        moodChart.getChildren().clear();
        Collections.reverse(moodHistory);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickLabelFill(Color.web("#FCA7A5"));
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickLabelFill(Color.web("#FCA7A5"));
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(11);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);

        yAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number object) {
                int value = object.intValue();
                return value <= 10 ? String.valueOf(value) : "";
            }

            @Override
            public Number fromString(String string) {
                return 0;
            }
        });

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(localeRes.getString("mood_chart"));
        lineChart.setStyle("-fx-background-color: #130329; -fx-text-fill: #FCA7A5; -fx-control-inner-background: #5A3E79;");
        lineChart.lookup(".chart-title").setStyle("-fx-text-fill: #FCA7A5;");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(moodHistory.size() + " " + localeRes.getString("days"));

        for (int i = 0; i < moodHistory.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + moodHistory.get(i).getShortStringDate(),
                    moodHistory.get(i).getMood()));
        }

        lineChart.getData().add(series);
        lineChart.setLegendVisible(true);
        lineChart.lookup(".chart-legend").setStyle("-fx-text-fill: #FCA7A5;");

        moodChart.getChildren().add(lineChart);
        moodChart.setStyle("-fx-background-color: #130329; -fx-control-inner-background: #5A3E79;");
    }
}