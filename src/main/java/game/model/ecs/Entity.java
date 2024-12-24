package game.model.ecs;

import java.util.UUID;

// Entity: a unique identifier
public class Entity {
    private final UUID id;

    public Entity(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
