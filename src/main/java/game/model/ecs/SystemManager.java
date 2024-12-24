package game.model.ecs;

import game.model.ecs.systems.RenderSystem;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private final ComponentStore componentStore; // Referencia al almacén de componentes
    private final List<ECSSystem> systems;         // Lista de sistemas registrados
    private RenderSystem renderSystem;

    public SystemManager(ComponentStore componentStore){
        this.componentStore = componentStore;
        this.systems = new ArrayList<>();
    }

    /**
     * Añade un sistema al administrador.
     * @param system the system to add.
     */
    public void addSystem(ECSSystem system) {
        systems.add(system);
    }

    public void addRender(RenderSystem system){
        this.renderSystem = system;
    }

    /**
     * Update all systems in the manager.
     * @param deltaTime the time elapsed since the last update.
     */
    public void update(float deltaTime) {
        for (ECSSystem system : systems) {
            system.update(deltaTime);
        }
    }

    public void render(float deltaTime) {
        if(renderSystem == null) return;
        renderSystem.update(deltaTime);
    }

    /**
     * Retrieve a specific system by its type.
     * @param <T> The type of the system.
     * @param systemClass The class of the system.
     * @return The instance of the requested system, or null if not found.
     */
    public <T extends ECSSystem> T getSystem(Class<T> systemClass) {
        for (ECSSystem system : systems) {
            if (systemClass.isInstance(system)) {
                return systemClass.cast(system);
            }
        }
        return null;
    }
}
