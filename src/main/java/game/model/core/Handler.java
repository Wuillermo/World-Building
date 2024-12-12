package game.model.core;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Handler {

    private final Queue<SubHandler> priorityList;
    private final SubHandler mapLevel;
    private final SubHandler unitLevel;
    private final SubHandler topLevel;

    public Handler() {
        this.priorityList = new LinkedList<>();
        this.mapLevel = new SubHandler();
        this.unitLevel = new SubHandler();
        this.topLevel = new SubHandler();

        this.priorityList.add(mapLevel);
        this.priorityList.add(unitLevel);
        this.priorityList.add(topLevel);
    }

    public void update(){
        for(SubHandler subHandler : priorityList) {
            subHandler.update();
        }
    }

    public void render(Graphics g) {
        for(SubHandler subHandler : priorityList) {
            subHandler.render(g);
        }
    }

    public void addMapLevelObject(GameObject newObject) {
        mapLevel.add(newObject);
    }

    public void addUnitLevelObject(GameObject newObject) {
        unitLevel.add(newObject);
    }

    public void addTopLevelObject(GameObject newObject) {
        topLevel.add(newObject);
    }

    public void removeObject(GameObject tempObject) {

    }
}