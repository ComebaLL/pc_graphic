package com.example.demo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BresenhamEllipseApp extends Application {

    private double centerX;
    private double centerY;
    private double width;
    private double height;
    private double rotationAngle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bresenham Ellipse Drawing");

        // Создаем Canvas для рисования
        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Label для отображения параметров эллипса
        Label infoLabel = new Label("Центр: (0, 0), Длина: 0, Высота: 0, Угол поворота: 0");

        // TextFields для ввода ширины, высоты и угла поворота
        TextField widthField = new TextField();
        TextField heightField = new TextField();
        TextField angleField = new TextField();
        widthField.setPromptText("Ширина");
        heightField.setPromptText("Высота");
        angleField.setPromptText("Угол поворота");

        // Обработчик событий для обновления параметров и перерисовки эллипса при клике
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            centerX = event.getX();
            centerY = event.getY();
            // Предполагаем, что пользователь вводит ширину, высоту и угол поворота эллипса
            width = parseDouble(widthField.getText(), 100);
            height = parseDouble(heightField.getText(), 50);
            rotationAngle = parseDouble(angleField.getText(), 0);

            // Рисуем эллипс при клике
            drawBresenhamEllipse(gc, centerX, centerY, width, height, rotationAngle);

            // Обновляем информацию в Label
            infoLabel.setText(String.format("Центр: (%.2f, %.2f), Длина: %.2f, Высота: %.2f, Угол поворота: %.2f",
                    centerX, centerY, width, height, rotationAngle));
        });

        // Создаем горизонтальный контейнер для размещения TextFields
        HBox inputBox = new HBox(widthField, heightField, angleField);

        // Создаем вертикальный контейнер для размещения Canvas, inputBox и Label
        VBox vbox = new VBox(canvas, inputBox, infoLabel);

        // Создаем сцену и устанавливаем ее в primaryStage
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);

        // Отображаем primaryStage
        primaryStage.show();
    }

    private double parseDouble(String text, double defaultValue) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Метод для рисования эллипса методом Брезенхема с учетом поворота
    private void drawBresenhamEllipse(GraphicsContext gc, double centerX, double centerY,
                                      double width, double height, double rotationAngle) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Создаем объект для аффинных преобразований
        AffineTransforms transforms = new AffineTransforms();

        // Применяем преобразование поворота
        double[][] rotationMatrix = transforms.rotationMatrix(rotationAngle, centerX, centerY);
        transforms.applyTransform(gc, rotationMatrix);

        double a = width / 2; // Полуось по горизонтали
        double b = height / 2; // Полуось по вертикали
        double x = 0; // Начальная координата x
        double y = b; // Начальная координата y
        double a2 = a * a; // Квадрат полуоси a
        double b2 = b * b; // Квадрат полуоси b
        double delta = b2 - a2 * b + a2 / 4; // Начальное значение параметра для определения точек на эллипсе
        double delta2; // Вспомогательная переменная для определения точек внутри эллипса

// Внешний цикл - рисуем верхнюю половину эллипса
        while (b2 * x <= a2 * y) {
            drawPixel(gc, centerX + x, centerY + y); // Рисуем точку в верхней правой четверти
            drawPixel(gc, centerX + x, centerY - y); // Рисуем точку в нижней правой четверти
            drawPixel(gc, centerX - x, centerY + y); // Рисуем точку в верхней левой четверти
            drawPixel(gc, centerX - x, centerY - y); // Рисуем точку в нижней левой четверти

            x++;

            delta2 = b2 * x * x + b2 * x + a2 * (y - 0.5) * (y - 0.5) - a2 * b2; // Определение параметра для текущей точки
            if (delta < 0 && delta2 < 0) {
                delta += b2 * (2 * x + 1); // Переход к следующей вертикальной точке
            } else {
                y--;
                delta += b2 * (2 * x + 1) + a2 * (1 - 2 * y); // Переход к следующей диагональной точке
            }
        }

// Внутренний цикл - рисуем нижнюю половину эллипса
        delta = b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2;
        while (y >= 0) {
            drawPixel(gc, centerX + x, centerY + y); // Рисуем точку в верхней правой четверти
            drawPixel(gc, centerX + x, centerY - y); // Рисуем точку в нижней правой четверти
            drawPixel(gc, centerX - x, centerY + y); // Рисуем точку в верхней левой четверти
            drawPixel(gc, centerX - x, centerY - y); // Рисуем точку в нижней левой четверти

            y--;

            delta2 = b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2; // Определение параметра для текущей точки
            if (delta > 0 && delta2 > 0) {
                delta += a2 * (1 - 2 * y); // Переход к следующей горизонтальной точке
            } else {
                x++;
                delta += b2 * (2 * x + 1) + a2 * (1 - 2 * y); // Переход к следующей диагональной точке
            }
        }


        // Сбрасываем преобразование
        transforms.resetTransform(gc);
    }

    // Метод для отображения пикселя на Canvas
    private void drawPixel(GraphicsContext gc, double x, double y) {
        gc.fillRect(x, y, 1, 1);
    }


}




