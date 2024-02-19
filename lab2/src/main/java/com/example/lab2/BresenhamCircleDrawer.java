package com.example.lab2;
import javafx.scene.canvas.GraphicsContext;

public class BresenhamCircleDrawer {

    // Метод для рисования окружности по алгоритму Брезенхема
    public static void draw(GraphicsContext gc, double centerX, double centerY, double radius) {
        int x = 0;
        int y = (int) radius;
        int delta = 2 - 2 * (int) radius;

        drawSymmetricPoints(gc, centerX, centerY, x, y);

        while (y >= x) {
            x++;

            // Проверка решающего параметра и выбор следующего пикселя
            if (delta > 0) {
                y--;
                delta += 4 * (x - y) + 10;
            } else {
                delta += 4 * x + 6;
            }

            drawSymmetricPoints(gc, centerX, centerY, x, y);
        }
    }

    // Вспомогательный метод для отображения симметричных точек окружности
    private static void drawSymmetricPoints(GraphicsContext gc, double centerX, double centerY, int x, int y) {
        drawPixel(gc, centerX + x, centerY + y);
        drawPixel(gc, centerX - x, centerY + y);
        drawPixel(gc, centerX + x, centerY - y);
        drawPixel(gc, centerX - x, centerY - y);
        drawPixel(gc, centerX + y, centerY + x);
        drawPixel(gc, centerX - y, centerY + x);
        drawPixel(gc, centerX + y, centerY - x);
        drawPixel(gc, centerX - y, centerY - x);
    }

    // Вспомогательный метод для отображения точки
    private static void drawPixel(GraphicsContext gc, double x, double y) {
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillRect(x, y, 1, 1);
    }
}


