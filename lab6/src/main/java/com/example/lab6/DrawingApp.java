package com.example.lab6;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DrawingApp extends Application {
    private double startX, startY, endX, endY;
    private List<Rectangle> rectangles = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    private boolean drawRectangleMode = true; // По умолчанию режим рисования прямоугольника

    // Класс, представляющий прямоугольник
    private class Rectangle {
        double startX, startY, endX, endY;

        Rectangle(double startX, double startY, double endX, double endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }
    }

    // Класс, представляющий линию
    private class Line {
        double startX, startY, endX, endY;

        Line(double startX, double startY, double endX, double endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        // Метод для отсечения и отображения линии только внутри прямоугольника
        void clipAndDraw(GraphicsContext gc, double xMin, double yMin, double xMax, double yMax) {
            boolean insideStart = (startX >= xMin && startX <= xMax && startY >= yMin && startY <= yMax);
            boolean insideEnd = (endX >= xMin && endX <= xMax && endY >= yMin && endY <= yMax);

            if (insideStart && insideEnd) {
                // Если оба конца линии внутри прямоугольника, рисуем линию полностью
                gc.setStroke(Color.BLACK);
                gc.strokeLine(startX, startY, endX, endY);
                return;
            }

            // Удаляем точки линии, которые находятся за пределами прямоугольника
            double clippedStartX = Math.max(Math.min(startX, xMax), xMin);
            double clippedStartY = Math.max(Math.min(startY, yMax), yMin);
            double clippedEndX = Math.max(Math.min(endX, xMax), xMin);
            double clippedEndY = Math.max(Math.min(endY, yMax), yMin);

            gc.setStroke(Color.BLACK);
            gc.strokeLine(clippedStartX, clippedStartY, clippedEndX, clippedEndY);
        }
    }

    // Метод для создания графического интерфейса
    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Обработчики событий для рисования
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });

        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();

            if (drawRectangleMode) {
                rectangles.add(new Rectangle(startX, startY, endX, endY));
            } else {
                lines.add(new Line(startX, startY, endX, endY));
            }

            redraw(gc); // Перерисовываем
        });

        // Кнопки для выбора режима рисования
        Button rectangleButton = new Button("Рисовать прямоугольник");
        rectangleButton.setOnAction(e -> drawRectangleMode = true);

        Button lineButton = new Button("Рисовать линию");
        lineButton.setOnAction(e -> drawRectangleMode = false);

        // Создание интерфейса
        VBox root = new VBox();
        root.getChildren().addAll(rectangleButton, lineButton, canvas);

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Drawing App");
        primaryStage.show();
    }

    // Метод для перерисовки холста с учетом всех фигур
    private void redraw(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Рисуем прямоугольники
        for (Rectangle rectangle : rectangles) {
            gc.strokeRect(rectangle.startX, rectangle.startY, rectangle.endX - rectangle.startX, rectangle.endY - rectangle.startY);
        }

        // Рисуем линии
        for (Line line : lines) {
            for (Rectangle rectangle : rectangles) {
                double xMin = Math.min(rectangle.startX, rectangle.endX);
                double xMax = Math.max(rectangle.startX, rectangle.endX);
                double yMin = Math.min(rectangle.startY, rectangle.endY);
                double yMax = Math.max(rectangle.startY, rectangle.endY);
                line.clipAndDraw(gc, xMin, yMin, xMax, yMax);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


































