package com.example.demo;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;

public class AffineTransforms {

    // Метод для создания матрицы аффинных преобразований для поворота
    public double[][] rotationMatrix(double angle, double pivotX, double pivotY) {
        double[][] matrix = new double[3][3];

        double radianAngle = Math.toRadians(angle);
        double cos = Math.cos(radianAngle);
        double sin = Math.sin(radianAngle);

        matrix[0][0] = cos;
        matrix[0][1] = -sin;
        matrix[0][2] = pivotX * (1 - cos) + pivotY * sin;
        matrix[1][0] = sin;
        matrix[1][1] = cos;
        matrix[1][2] = pivotY * (1 - cos) - pivotX * sin;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;

        return matrix;
    }

    // Метод для применения аффинного преобразования к GraphicsContext
    public void applyTransform(GraphicsContext gc, double[][] transformMatrix) {
        Affine transform = new Affine();
        transform.setMxx(transformMatrix[0][0]);
        transform.setMxy(transformMatrix[0][1]);
        transform.setTx(transformMatrix[0][2]);
        transform.setMyx(transformMatrix[1][0]);
        transform.setMyy(transformMatrix[1][1]);
        transform.setTy(transformMatrix[1][2]);

        gc.setTransform(transform);
    }

    // Метод для сброса аффинного преобразования в GraphicsContext
    public void resetTransform(GraphicsContext gc) {
        gc.setTransform(new Affine());
    }
}

