package game.model.core;

import java.awt.*;
import java.util.LinkedList;

public class SubHandler {

    private final LinkedList<GameObject> gameObjects;

    public SubHandler() {
        this.gameObjects = new LinkedList<>();
    }

    public void update() {
        for(GameObject tempObject : gameObjects) {
            tempObject.update();
        }
    }

    public void render(Graphics g) {
        for(GameObject tempObject : gameObjects) {
            tempObject.render(g);
        }
    }

    public void add(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public boolean remove(GameObject gameObject) {
        return gameObjects.remove(gameObject);
    }
}
