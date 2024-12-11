package game.controller;

import game.controller.Events.Event;
import game.controller.Events.EventQueue;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameplayContext implements InputContextInterface {
    private final EventQueue eventQueue;

    public GameplayContext(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void handleKeyPress(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> eventQueue.addEvent(new Event(Event.EventType.OPEN_MENU));
            // Add more gameplay-specific actions
        }
    }

    @Override
    public void handleMouseClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        System.out.println("Handle in-game click at: " + mx + ", " + my);
    }

    @Override
    public void handleMouseMove(MouseEvent e) {
        // Gameplay-specific mouse movement logic (e.g., highlight tiles)
    }
}
