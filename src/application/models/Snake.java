package application.models;

import application.engine.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static application.engine.GameLogic.TILE_SIZE;

public class Snake {
    private final Map map;
    private final Node tail;
    private Node head;
    private Direction direction;
    private int size = 0;

    public Snake(int x, int y, Direction direction, Map map) {
        this.map = map;
        tail = new Node(x, y);
        head = new Node(tail, null, x + 1, y);
        tail.setPrev(head);
        this.direction = direction;
    }

    public void drawSnake(GraphicsContext gc) {
        head.drawNode(gc);
    }

    public void moveSnake() {
        map.getTile(tail.x, tail.y).setSnake(false);
        tail.moveNode();
    }

    public void eatFood(Tile tile) {
        Node newNode = new Node(head, null, tile.getX(), tile.getY());
        head.setPrev(newNode);
        head = newNode;

        map.clearFoodTile();
    }

    public void setDirection(Direction direction) {
        Tile nextTile = getNextTile(direction);
        if (nextTile.getX() == head.next.x && nextTile.getY() == head.next.y) return;
        this.direction = direction;
    }

    public Tile getNextTile() {
        switch (direction) {
            case UP -> {
                return new Tile(head.x, head.y - 1);
            }
            case DOWN -> {
                return new Tile(head.x, head.y + 1);
            }
            case LEFT -> {
                return new Tile(head.x - 1, head.y);
            }
            case RIGHT -> {
                return new Tile(head.x + 1, head.y);
            }
        }
        return null;
    }

    public Tile getNextTile(Direction direction) {
        switch (direction) {
            case UP -> {
                return new Tile(head.x, head.y - 1);
            }
            case DOWN -> {
                return new Tile(head.x, head.y + 1);
            }
            case LEFT -> {
                return new Tile(head.x - 1, head.y);
            }
            case RIGHT -> {
                return new Tile(head.x + 1, head.y);
            }
        }
        return null;
    }


    public int getSize() {
        return size;
    }

    private class Node {
        Node next, prev;
        int x, y;

        Node(Node next, Node prev, int x, int y) {
            this.next = next;
            this.prev = prev;
            this.x = x;
            this.y = y;
            map.getTile(x, y).setSnake(true);
            size++;
        }

        Node(int x, int y) {
            this(null, null, x, y);
        }

        private void setPrev(Node prev) {
            this.prev = prev;
        }


        private void drawNode(GraphicsContext gc) {
            gc.setFill(Color.DARKGREEN);
            gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            if (next != null) {
                next.drawNode(gc);
            }
        }

        private void moveNode() {
            if (prev == null) {
                moveHead();
                return;
            }
            x = prev.x;
            y = prev.y;

            prev.moveNode();
        }

        private void moveHead() {
            switch (direction) {
                case UP -> y--;
                case DOWN -> y++;
                case LEFT -> x--;
                case RIGHT -> x++;
            }
            map.getTile(x, y).setSnake(true);
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
