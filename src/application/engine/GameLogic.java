package application.engine;

import application.models.Snake;
import application.models.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameLogic extends Thread {
    public static final int TILE_SIZE = 20;

    private final Snake snake;
    private final Map map;
    private final GraphicsContext gc;

    private boolean running = true;

    public GameLogic(GraphicsContext gc) {
        this.gc = gc;
        map = new Map(gc);
        snake = new Snake(2, 2, Snake.Direction.RIGHT, map);
    }

    @Override
    public void run() {
        int FPS = 8;
        double interval = 1000000000.0 / FPS;
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / interval;
            lastTime = currentTime;

            if (delta >= 1) {
                updateValues();
                drawComponents();
                delta--;
            }
        }
    }

    private void updateValues() {
        Tile nextTile = snake.getNextTile();
        //check bounds
        if (nextTile.getX() < 0 ||
                nextTile.getY() < 0 ||
                nextTile.getX() >= map.getNumberOfTileWide() ||
                nextTile.getY() >= map.getNumberOfTileHigh()) {
            gameOver();
        } else if (map.getTile(nextTile).hasSnake()) {
            gameOver();
        } else if (map.getTile(nextTile).hasFood()) {
            snake.eatFood(nextTile);
        } else {
            snake.moveSnake();
        }
        if (map.getFoodTile() == null) map.generateFoodTile();
    }

    private void drawComponents() {
        clearCanvas();
        map.drawFood();
        snake.drawSnake(gc);

        if (!running) {
            gc.setFont(new Font(40));
            gc.setFill(Color.BLACK);
            gc.fillText("Game over!", 90, 170);
            gc.setFont(new Font(30));
            gc.fillText("Score: " + (snake.getSize() - 2), 140, 220);
        }
    }

    private void clearCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public Snake getSnake() {
        return this.snake;
    }

    public void end() {
        this.running = false;
    }

    public void gameOver() {
        running = false;
    }
}
