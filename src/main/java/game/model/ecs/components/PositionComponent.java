package game.model.ecs.components;

import game.model.ecs.Component;

public class PositionComponent extends Component {
    public int x;
    public int y;

    public PositionComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
