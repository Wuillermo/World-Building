package game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class InputManager {
    private InputContextInterface currentContext;

    public void setContext(InputContextInterface context) {
        this.currentContext = context;
    }

    public void handleKeyPress(KeyEvent e) {
        if (currentContext != null) {
            currentContext.handleKeyPress(e);
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        if (currentContext != null) {
            currentContext.handleKeyReleased(e);
        }
    }

    public void handleMouseClick(MouseEvent e) {
        if (currentContext != null) {
            currentContext.handleMouseClick(e);
        }
    }

    public void handleMouseMove(MouseEvent e) {
        if (currentContext != null) {
            currentContext.handleMouseMove(e);
        }
    }
}

