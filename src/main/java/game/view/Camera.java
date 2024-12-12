package game.view;

import game.model.core.GameObject;
import game.model.core.ID;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

// TODO stop the camera from being able to move infinitely to the right and bottom

public class Camera extends GameObject {
    enum CAMERA_MOVES {
        CAMERA_UP, CAMERA_DOWN, CAMERA_LEFT, CAMERA_RIGHT
    }

    private final Queue<CAMERA_MOVES> camera_moves;

    private int topLeftX;
    private int topLeftY;
    private final int width;
    private final int height;

    public Camera(int width, int height) {
        super(ID.Camera);
        this.width = width;
        this.height = height;
        this.camera_moves = new LinkedList<>();

        topLeftX = 0;
        topLeftY = 0;
    }

    @Override
    public void update() {
        for (CAMERA_MOVES move : camera_moves) {
            switch (move) {
                case CAMERA_UP -> {
                    if (topLeftY >= -7) {
                        topLeftY -= 7;
                    }
                }
                case CAMERA_DOWN -> {
                    if (topLeftY + height <= 200000) {
                        topLeftY += 7;
                    }
                }
                case CAMERA_LEFT -> {
                    if (topLeftX >= -7) {
                        topLeftX -= 7;
                    }
                }
                case CAMERA_RIGHT -> {
                    if (topLeftX + width <= 200000) {
                        topLeftX += 7;
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {

    }

    public void cameraUp() {
        if(!camera_moves.contains(CAMERA_MOVES.CAMERA_UP)) {
            camera_moves.add(CAMERA_MOVES.CAMERA_UP);
        }
    }
    public void cameraDown() {
        if(!camera_moves.contains(CAMERA_MOVES.CAMERA_DOWN)) {
            camera_moves.add(CAMERA_MOVES.CAMERA_DOWN);
        }
    }
    public void cameraLeft() {
        if(!camera_moves.contains(CAMERA_MOVES.CAMERA_LEFT)) {
            camera_moves.add(CAMERA_MOVES.CAMERA_LEFT);
        }
    }
    public void cameraRight() {
        if(!camera_moves.contains(CAMERA_MOVES.CAMERA_RIGHT)) {
            camera_moves.add(CAMERA_MOVES.CAMERA_RIGHT);
        }
    }
    public void stopUp() {
        camera_moves.remove(CAMERA_MOVES.CAMERA_UP);
    }
    public void stopDown() {
        camera_moves.remove(CAMERA_MOVES.CAMERA_DOWN);
    }
    public void stopLeft() {
        camera_moves.remove(CAMERA_MOVES.CAMERA_LEFT);
    }
    public void stopRight() {
        camera_moves.remove(CAMERA_MOVES.CAMERA_RIGHT);
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }
}
