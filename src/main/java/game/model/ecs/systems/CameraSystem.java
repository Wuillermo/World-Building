package game.model.ecs.systems;

import game.model.ecs.ComponentStore;
import game.model.ecs.Entity;
import game.model.ecs.ECSSystem;
import game.model.ecs.components.PositionComponent;
import game.view.Camera;

public class CameraSystem extends ECSSystem {
    private final Camera camera;

    public CameraSystem(ComponentStore componentStore, Camera camera){
        super(componentStore);
        this.camera = camera;
    }

    public void updateCamera(Entity cameraEntity) {
        PositionComponent position = componentStore.getComponent(cameraEntity, PositionComponent.class);
        camera.setPosition(position.x, position.y);
    }

    @Override
    public void update(float deltaTime) {

    }
}
