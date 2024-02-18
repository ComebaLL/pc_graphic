package com.example.lab1;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class BresenhamLineDrawer {

    // Метод для рисования линии по методу Брезенхема
    public static void draw(Canvas canvas, int x1, int y1, int x2, int y2) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Вычисляем разницу между координатами
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        // Переходим от одной точки к другой, рисуя точки на холсте
        while (true) {
            gc.fillRect(x1, y1, 1, 1);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int err2 = 2 * err;

            if (err2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (err2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }
}

