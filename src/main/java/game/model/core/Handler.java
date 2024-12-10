package game.model.core;

import game.model.core.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();

    public void update(){
        for(GameObject tempObject : object) {
            tempObject.update();
        }
    }

    public void render(Graphics g) {
        for (GameObject tempObject : object) {
            if(tempObject.isVisible) tempObject.render(g);
        }
    }

    public void addObject(GameObject newObject) {
        object.add(newObject);
    }

    public void removeObject(GameObject tempObject) {
        object.remove(tempObject);
    }
}