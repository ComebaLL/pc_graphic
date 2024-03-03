package com.example.lab5;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DrawingApp extends Application {

    private double startX, startY;
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean fillMode = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing App");

        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        canvas.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                startX = event.getX();
                startY = event.getY();

                if (fillMode) {
                    fillArea((int) startX, (int) startY, (Color) gc.getFill());
                }
            }
        });

        canvas.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !fillMode) {
                double endX = event.getX();
                double endY = event.getY();

                drawLine(startX, startY, endX, endY);
            }
        });

        Button toggleModeButton = new Button("Toggle Fill Mode");
        toggleModeButton.setOnAction(e -> {
            fillMode = !fillMode;
        });

        BorderPane root = new BorderPane();
        root.setTop(toggleModeButton);
        root.setCenter(canvas);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawLine(double startX, double startY, double endX, double endY) {
        gc.setStroke(Color.BLACK); // Можете изменить цвет линии
        gc.setLineWidth(2); // Можете изменить толщину линии
        gc.strokeLine(startX, startY, endX, endY);
    }

    private void fillArea(int x, int y, Color fill) {
        if (x < 0 || x >= canvas.getWidth() || y < 0 || y >= canvas.getHeight()) {
            return;
        }

        Color originalColor = getPixelColor(x, y);
        Color fillColor = Color.PURPLE;

        if (originalColor.equals(fillColor)) {
            return;
        }

        fillRecursive(x, y, originalColor, fillColor);
    }

    private void fillArea(int x, int y) {
        if (x < 0 || x >= canvas.getWidth() || y < 0 || y >= canvas.getHeight()) {
            return;
        }

        Color originalColor = getPixelColor(x, y);
        Color fillColor = Color.PURPLE;

        if (originalColor.equals(fillColor)) {
            return;
        }

        fillRecursive(x, y, originalColor, fillColor);
    }

    private void fillRecursive(int x, int y, Color originalColor, Color fillColor) {
        if (x < 0 || x >= canvas.getWidth() || y < 0 || y >= canvas.getHeight()) {
            return;
        }

        if (!getPixelColor(x, y).equals(originalColor)) {
            return;
        }

        gc.setFill(fillColor);
        gc.fillRect(x, y, 1, 1);

        fillRecursive(x + 1, y, originalColor, fillColor);
        fillRecursive(x - 1, y, originalColor, fillColor);
        fillRecursive(x, y + 1, originalColor, fillColor);
        fillRecursive(x, y - 1, originalColor, fillColor);
    }

    private Color getPixelColor(int x, int y) {
        return canvas.snapshot(null, null).getPixelReader().getColor(x, y);
    }

}























