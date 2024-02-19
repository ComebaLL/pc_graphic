package com.example.lab2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BresenhamCircleApp extends Application {

    private double centerX;
    private double centerY;
    private double radius;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bresenham Circle Drawing");

        // Создаем Canvas для рисования
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Label для отображения параметров окружности
        Label infoLabel = new Label("Центр: (0, 0), Радиус: 0");

        // Slider для выбора радиуса
        Slider radiusSlider = new Slider(1, 100, 50); // Минимальное, максимальное и начальное значение
        radiusSlider.setShowTickMarks(true);
        radiusSlider.setShowTickLabels(true);
        radiusSlider.setMajorTickUnit(25);

        // Обработчик событий для обновления параметров и перерисовки окружности при клике или изменении радиуса
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            centerX = event.getX();
            centerY = event.getY();
            radius = radiusSlider.getValue();

            // Рисуем окружность при клике
            drawBresenhamCircle(gc, centerX, centerY, radius);

            // Обновляем информацию в Label
            infoLabel.setText(String.format("Центр: (%.2f, %.2f), Радиус: %.2f", centerX, centerY, radius));
        });

        radiusSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            radius = newValue.doubleValue();

            // Рисуем окружность при изменении радиуса
            drawBresenhamCircle(gc, centerX, centerY, radius);

            // Обновляем информацию в Label
            infoLabel.setText(String.format("Центр: (%.2f, %.2f), Радиус: %.2f", centerX, centerY, radius));
        });

        // Создаем вертикальный контейнер для размещения Canvas, Slider и Label
        VBox vbox = new VBox(canvas, radiusSlider, infoLabel);

        // Создаем сцену и устанавливаем ее в primaryStage
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);

        // Отображаем primaryStage
        primaryStage.show();
    }

    // Метод для рисования окружности методом Брезенхема
    private void drawBresenhamCircle(GraphicsContext gc, double centerX, double centerY, double radius) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        int x = 0;
        int y = (int) radius;
        int delta = 2 - 2 * (int) radius;

        while (y >= 0) {
            // Отображаем пиксели окружности во всех восьми симметричных точках
            drawPixel(gc, centerX + x, centerY - y);
            drawPixel(gc, centerX + x, centerY + y);
            drawPixel(gc, centerX - x, centerY - y);
            drawPixel(gc, centerX - x, centerY + y);
            drawPixel(gc, centerX + y, centerY - x);
            drawPixel(gc, centerX + y, centerY + x);
            drawPixel(gc, centerX - y, centerY - x);
            drawPixel(gc, centerX - y, centerY + x);

            // Если решение для текущей точки равно 0, то переход к следующей точке
            int decision = 2 * (delta + y) - 1;
            if ((delta < 0) && (decision <= 0)) {
                x++;
                delta += 2 * x + 1;
            } else {
                // Иначе изменяем координаты и обновляем решение
                int decision2 = 2 * (delta - x) - 1;
                if ((delta > 0) && (decision2 > 0)) {
                    y--;
                    delta += 1 - 2 * y;
                } else {
                    x++;
                    delta += 2 * (x - y);
                    y--;
                }
            }
        }
    }

    // Метод для отображения пикселя на Canvas
    private void drawPixel(GraphicsContext gc, double x, double y) {
        gc.fillRect(x, y, 1, 1);
    }
}






