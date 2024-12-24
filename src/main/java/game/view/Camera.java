package game.view;

import game.model.core.GameObject;
import game.model.core.ID;
import game.model.ecs.ComponentStore;
import game.model.ecs.Entity;
import game.model.ecs.components.MovementComponent;
import game.model.map.GameMap;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

// TODO stop the camera from being able to move infinitely to the right and bottom

public class Camera extends Component {
    private final int width;
    private final int height;

    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // Método para establecer la posición de la cámara (usado en el render system)
    public void setPosition(int x, int y) {
        // Puedes actualizar la visualización de la cámara aquí
    }

    public void addMovementToEntity(Entity entity, MovementComponent.CAMERA_MOVES move, ComponentStore componentStore) {
        // Obtén el componente MovementComponent de la entidad y añádele el movimiento
        MovementComponent movement = componentStore.getComponent(entity, MovementComponent.class);
        movement.addMove(move);
    }

    public void stopMovementForEntity(Entity entity, MovementComponent.CAMERA_MOVES move, ComponentStore componentStore) {
        // Detén el movimiento en la entidad
        MovementComponent movement = componentStore.getComponent(entity, MovementComponent.class);
        movement.removeMove(move);
    }
}
