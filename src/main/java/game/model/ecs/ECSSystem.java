package game.model.ecs;

public abstract class ECSSystem {
    protected ComponentStore componentStore;

    public ECSSystem(ComponentStore componentStore){
        this.componentStore = componentStore;
    }

    public abstract void update(float deltaTime);
}
