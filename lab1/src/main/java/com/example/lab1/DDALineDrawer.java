package com.example.lab1;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DDALineDrawer {

    // Метод для рисования линии по методу ЦДА
    public static void draw(Canvas canvas, int x1, int y1, int x2, int y2) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Вычисляем разницу между координатами
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        // Вычисляем приращения для каждого шага
        double xIncrement = (double) dx / steps;
        double yIncrement = (double) dy / steps;

        double x = x1;
        double y = y1;

        // Рисуем точки на холсте, переходя от одной точки к другой
        for (int i = 0; i <= steps; i++) {
            gc.fillRect((int) x, (int) y, 1, 1);
            x += xIncrement;
            y += yIncrement;
        }
    }
}

