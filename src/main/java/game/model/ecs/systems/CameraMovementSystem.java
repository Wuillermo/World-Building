package game.model.ecs.systems;

import game.model.ecs.Entity;
import game.model.ecs.ECSSystem;

import game.model.ecs.ComponentStore;
import game.model.ecs.components.MovementComponent;
import game.model.ecs.components.PositionComponent;
import game.model.ecs.components.SizeComponent;
import game.model.map.GameMap;

public class CameraMovementSystem extends ECSSystem {

    private GameMap gameMap;
    private Entity cameraEntity;

    public CameraMovementSystem(ComponentStore componentStore) {
        super(componentStore);
    }

    @Override
    public void update(float deltaTime) {
        // Ensure the camera entity exists before trying to update it
        if (cameraEntity == null) return;

        PositionComponent position = componentStore.getComponent(cameraEntity, PositionComponent.class);
        MovementComponent movement = componentStore.getComponent(cameraEntity, MovementComponent.class);
        SizeComponent size = componentStore.getComponent(cameraEntity, SizeComponent.class);

        // Only update the camera entity's position based on its movement commands
        for (MovementComponent.CAMERA_MOVES move : movement.cameraMoves) {
            switch (move) {
                case CAMERA_UP:
                    if (position.y > -7) {
                        position.y -= 7;
                    }
                    break;
                case CAMERA_DOWN:
                    if (position.y + size.height <= gameMap.getMapYSize() + 7) {
                        position.y += 7;
                    }
                    break;
                case CAMERA_LEFT:
                    if (position.x > -7) {
                        position.x -= 7;
                    }
                    break;
                case CAMERA_RIGHT:
                    if (position.x + size.width < gameMap.getMapXSize() + 7) {
                        position.x += 7;
                    }
                    break;
            }
        }

    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    // Set the camera entity for this system
    public void setCameraEntity(Entity cameraEntity) {
        this.cameraEntity = cameraEntity;
    }
}
