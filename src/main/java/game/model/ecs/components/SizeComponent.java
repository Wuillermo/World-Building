package game.model.ecs.components;

import game.model.ecs.Component;

public class SizeComponent extends Component {
    public int width;
    public int height;

    public SizeComponent(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
