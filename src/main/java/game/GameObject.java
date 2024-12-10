package game;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected final ID id;
    protected boolean isVisible;

    public GameObject(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }
        this.id = id;
        this.isVisible = false;
    }

    public abstract void update();
    public abstract void render(Graphics g);

    public ID getId() {
        return id;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
