// MovementComponent.java
package game.model.ecs.components;

import game.model.ecs.Component;

import java.util.LinkedList;
import java.util.Queue;

public class MovementComponent extends Component {
    public enum CAMERA_MOVES {
        CAMERA_UP, CAMERA_DOWN, CAMERA_LEFT, CAMERA_RIGHT
    }

    public Queue<CAMERA_MOVES> cameraMoves;

    public MovementComponent() {
        cameraMoves = new LinkedList<>();
    }

    public void addMove(CAMERA_MOVES move) {
        if (!cameraMoves.contains(move)) {
            cameraMoves.add(move);
        }
    }

    public void removeMove(CAMERA_MOVES move) {
        cameraMoves.remove(move);
    }
}