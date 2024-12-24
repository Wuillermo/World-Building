package game.model.ecs;

import java.util.*;

public class ComponentStore {
    // Main map to organize components by type
    private final Map<Class<? extends Component>, Map<UUID, Component>> components = new HashMap<>();

    // Adds a component to an entity
    public <T extends Component> void addComponent(Entity entity, T component){
        // Gets the map of the component type, or creates one if it doesn't exist
        components
                .computeIfAbsent(component.getClass(), k -> new HashMap<>())
                .put(entity.getId(),component); // Associates the component to the entity
    }

    // Gets the component of an entity based on type
    public <T extends Component> T getComponent(Entity entity, Class<T> componentClass) {
        // Looks for the map of the component type and returns the entity's component
        return componentClass.cast(
                components.getOrDefault(componentClass, new HashMap<>()).get(entity.getId())
        );
    }

    // Remove a component from an entity
    public <T extends Component> void removeComponent(Entity entity, Class<T> componentClass) {
        components.getOrDefault(componentClass, new HashMap<>()).remove(entity.getId());
    }

    public <T extends Component> List<Entity> getEntitiesWithComponents(Class<T> componentClass) {
        List<Entity> entitiesWithComponent = new ArrayList<>();

        // Get the map of components for the specific type
        Map<UUID, Component> componentMap = components.get(componentClass);

        // If the component type exists, add the entities to the list
        if (componentMap != null) {
            for (UUID entityId : componentMap.keySet()) {
                // You can create an entity instance based on entityId if needed
                // Assuming you have a method to get an Entity by its ID, for example:
                Entity entity = EntityManager.getEntityById(entityId); // You need to implement this method
                entitiesWithComponent.add(entity);
            }
        }

        return entitiesWithComponent;

    }


}
