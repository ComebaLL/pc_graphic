package com.example.lab1;

import com.example.lab1.BresenhamLineDrawer;
import com.example.lab1.DDALineDrawer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Canvas canvas1;
    private Canvas canvas2;

    private double startX, startY, endX, endY;
    private GraphicsContext gc1, gc2;

    private boolean firstPointSet = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Line Drawing App");

        // Создаем два холста для рисования линий
        canvas1 = createCanvas();
        canvas2 = createCanvas();

        // Создаем кнопку для вызова установки точек и рисования линии
        Button drawButton = new Button("Set Points and Draw Line");

        // При нажатии кнопки вызываем метод setPointsAndDrawLine
        drawButton.setOnAction(e -> setPointsAndDrawLine());

        // Создаем разделительную черную линию между холстами
        StackPane divider = new StackPane();
        divider.setStyle("-fx-background-color: black;");
        divider.setMinWidth(2);

        // Создаем горизонтальный контейнер для размещения холстов, разделителя и кнопки
        HBox layout = new HBox(10);
        layout.getChildren().addAll(canvas1, divider, canvas2, drawButton);

        // Создаем сцену и устанавливаем ее в primaryStage
        Scene scene = new Scene(layout, 824, 400);

        // Получаем GraphicsContext для обоих холстов
        gc1 = canvas1.getGraphicsContext2D();
        gc2 = canvas2.getGraphicsContext2D();

        // Добавляем обработчики мыши для рисования на холстах
        setupMouseEvents();

        primaryStage.setScene(scene);

        // Отображаем primaryStage
        primaryStage.show();
    }

    // Метод для создания холста
    private Canvas createCanvas() {
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Устанавливаем начало координат в левый верхний угол холста
        gc.translate(0.5, 0.5);

        // Рисуем рамку вокруг холста
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);

        return canvas;
    }

    // Метод для установки точек и рисования линии на обоих холстах
    private void setPointsAndDrawLine() {
        // Используем BresenhamLineDrawer для рисования линии по методу Брезенхема
        BresenhamLineDrawer.draw(canvas1, (int) startX, (int) startY, (int) endX, (int) endY);

        // Используем DDALineDrawer для рисования линии по методу ЦДА
        DDALineDrawer.draw(canvas2, (int) startX, (int) startY, (int) endX, (int) endY);

        // Сбрасываем флаг первой точки
        firstPointSet = false;
    }

    // Метод для настройки обработчиков мыши на холстах
    private void setupMouseEvents() {
        canvas1.setOnMouseClicked(this::handleMouseClicked);
        canvas2.setOnMouseClicked(this::handleMouseClicked);
    }

    private void handleMouseClicked(MouseEvent e) {
        if (!firstPointSet) {
            // Установка координат первой точки
            startX = e.getX();
            startY = e.getY();
            firstPointSet = true;
        } else {
            // Установка координат второй точки и рисование линии
            endX = e.getX();
            endY = e.getY();
            setPointsAndDrawLine();
        }
    }
}





