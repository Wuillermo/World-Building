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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> eventQueue.addEvent(new Event(Event.EventType.OPEN_MENU));
            case KeyEvent.VK_W -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_UP));
            case KeyEvent.VK_S -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_DOWN));
            case KeyEvent.VK_A -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_LEFT));
            case KeyEvent.VK_D -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_RIGHT));
        }
    }

    public void handleKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> eventQueue.addEvent(new Event(Event.EventType.OPEN_MENU));
            case KeyEvent.VK_W -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_UP_RELEASED));
            case KeyEvent.VK_S -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_DOWN_RELEASED));
            case KeyEvent.VK_A -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_LEFT_RELEASED));
            case KeyEvent.VK_D -> eventQueue.addEvent(new Event(Event.EventType.CAMERA_RIGHT_RELEASED));
        }
    }

    @Override
    public void handleMouseClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
    }

    @Override
    public void handleMouseMove(MouseEvent e) {
        // Gameplay-specific mouse movement logic (e.g., highlight tiles)
    }
}
