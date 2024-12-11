package game.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface InputContextInterface {
    void handleKeyPress(KeyEvent e);
    void handleMouseClick(MouseEvent e);
    void handleMouseMove(MouseEvent e);
}
