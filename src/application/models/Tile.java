package application.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static application.engine.GameLogic.TILE_SIZE;

public class Tile {
    private final int x, y;
    private boolean hasFood, hasSnake;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean hasFood() {
        return hasFood;
    }

    public boolean hasSnake() {
        return hasSnake;
    }

    public void setFood(boolean hasFood) {
        this.hasFood = hasFood;
    }

    public void setSnake(boolean hasSnake) {
        this.hasSnake = hasSnake;
    }

    public void drawTile(GraphicsContext gc) {
        if (hasFood) {
            gc.setFill(Color.GOLD);
            gc.fillRect(x * TILE_SIZE + 4, y * TILE_SIZE + 4, 12, 12);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
