package application.engine;

import application.models.Tile;
import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

import static application.engine.GameLogic.TILE_SIZE;

public class Map {
    private final GraphicsContext gc;
    private final Tile[][] tiles;
    private Tile foodTile;
    private final int numberOfTileWide, numberOfTileHigh;
    private final Random random;

    public Map(GraphicsContext gc) {
        this.gc = gc;
        this.random = new Random();
        foodTile = null;

        double canvasHeight = gc.getCanvas().getHeight();
        double canvasWidth = gc.getCanvas().getWidth();
        numberOfTileWide = (int) (canvasWidth / TILE_SIZE);
        numberOfTileHigh = (int) (canvasHeight / TILE_SIZE);

        this.tiles = new Tile[numberOfTileWide][numberOfTileHigh];

        for (int i = 0; i < numberOfTileWide; i++) {
            for (int j = 0; j < numberOfTileHigh; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    public void generateFoodTile() {
        int x, y;
        do {
            x = random.nextInt(numberOfTileWide);
            y = random.nextInt(numberOfTileHigh);

            if (!getTile(x, y).hasSnake()) {
                getTile(x, y).setFood(true);
                foodTile = getTile(x, y);
            }
        } while (foodTile == null);
    }

    public void drawFood() {
        if (foodTile != null) foodTile.drawTile(gc);
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Tile getTile(Tile tile) {
        return tiles[tile.getX()][tile.getY()];
    }

    public Tile getFoodTile() {
        return foodTile;
    }

    public void clearFoodTile() {
        getTile(foodTile).setFood(false);
        foodTile = null;
    }

    public int getNumberOfTileWide() {
        return numberOfTileWide;
    }

    public int getNumberOfTileHigh() {
        return numberOfTileHigh;
    }
}
