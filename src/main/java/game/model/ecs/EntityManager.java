package game.model.ecs;

import java.util.*;

public class EntityManager {
    private static final Map<UUID,  Entity> entities = new HashMap<>();

    // Adds an entity
    public void addEntity(Entity entity) {
        entities.put(entity.getId(), entity);
    }

    // Removes an entity
    public void removeEntity(UUID entityId) {
        entities.remove(entityId);
    }

    // Gets an entity by its ID
    public static Entity getEntityById(UUID entityId) {
        return entities.get(entityId);
    }

    // Returns all entities
    public Collection<Entity> getAllEntities() {
        return entities.values();
    }

    // Finds entities with specific components
    public List<Entity> getEntitiesWithComponent(Class<? extends Component> componentClass, ComponentStore componentStore) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : entities.values()) {
            if (componentStore.getComponent(entity, componentClass) != null) {
                result.add(entity);
            }
        }
        return result;
    }
}
