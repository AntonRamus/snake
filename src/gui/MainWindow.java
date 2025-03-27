package gui;

import application.engine.GameLogic;
import application.models.Snake;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainWindow extends Application {
    private GameLogic gameLogic;
    @Override
    public void start(Stage stage) {
        stage.setTitle("Snake");
        VBox root = initPane();
        Scene scene = new Scene(root);
        initControls(scene);
        stage.setScene(scene);
        stage.setOnCloseRequest(_ -> gameLogic.end());
        stage.show();
        gameLogic.start();
    }

    private VBox initPane() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Canvas canvas = new Canvas(400, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        gameLogic = new GameLogic(gc);

        root.getChildren().addAll(canvas);

        return root;
    }

    private void initControls(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case UP -> gameLogic.getSnake().setDirection(Snake.Direction.UP);
                case DOWN -> gameLogic.getSnake().setDirection(Snake.Direction.DOWN);
                case LEFT -> gameLogic.getSnake().setDirection(Snake.Direction.LEFT);
                case RIGHT -> gameLogic.getSnake().setDirection(Snake.Direction.RIGHT);
            }
        });
    }
}
