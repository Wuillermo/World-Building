package game.model.ecs.systems;

import game.model.ecs.ComponentStore;
import game.model.ecs.Entity;
import game.model.ecs.ECSSystem;
import game.model.ecs.components.PositionComponent;
import game.model.map.GameMap;

import java.awt.*;
import java.util.List;

public class RenderSystem extends ECSSystem {
    private final Entity cameraEntity;
    private PositionComponent cameraPositionComponent;
    private Graphics graphics;

    private GameMap gameMap;

    public RenderSystem(ComponentStore componentStore, Entity cameraEntity, GameMap gameMap) {
        super(componentStore);
        this.gameMap = gameMap;
        this.cameraEntity = cameraEntity;
        cameraPositionComponent = componentStore.getComponent(cameraEntity, PositionComponent.class);
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("a");
        if(graphics == null) return; // Ensure graphics is set before rendering
        // Get all entities with PositionComponent and SpriteComponent
        List<Entity> entities = componentStore.getEntitiesWithComponents(PositionComponent.class);

        for (Entity entity : entities) {
            PositionComponent position = componentStore.getComponent(entity, PositionComponent.class);

            /*// Optional: Check visibility
            VisibilityComponent visibility = componentStore.getComponent(entity, VisibilityComponent.class);
            if (visibility != null && !visibility.isVisible) {
                continue; // Skip rendering if not visible
            }*/

            // Calculate the on-screen position using the camera's position
            int renderX = - cameraPositionComponent.x;
            int renderY = - cameraPositionComponent.y;

            if(gameMap != null) {
                graphics.drawImage(gameMap.bufferedImage, renderX, renderY, null);
            }
            // Render the sprite
            //graphics.drawImage(sprite.sprite, renderX, renderY, null);
        }
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
}
