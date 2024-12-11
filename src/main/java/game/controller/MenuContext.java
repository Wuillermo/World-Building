package game.controller;

import game.controller.Events.Event;
import game.controller.Events.EventQueue;
import game.view.GamePanel;
import game.view.MainMenu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuContext implements InputContextInterface {
    private final EventQueue eventQueue;
    private final GamePanel gamePanel;
    private final MainMenu mainMenu;
    private final Rectangle playButton;
    private final Rectangle loadButton;
    private final Rectangle initialMapButton;
    private final Rectangle quitButton;

    public MenuContext(EventQueue eventQueue, GamePanel gamePanel) {
        this.eventQueue = eventQueue;
        this.gamePanel = gamePanel;
        this.mainMenu = gamePanel.getMenu();
        this.playButton = mainMenu.playButton;
        this.loadButton = mainMenu.loadButton;
        this.initialMapButton = mainMenu.initialMapButton;
        this.quitButton = mainMenu.quitButton;
    }
    @Override
    public void handleKeyPress(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyChar());
    }

    @Override
    public void handleMouseClick(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        // PLAY button
        if((mx >= playButton.x) && (mx <= (playButton.x + playButton.width))
                &&(my >= playButton.y) && (my <= (playButton.y + playButton.height))) {
            eventQueue.addEvent(new Event(Event.EventType.NEW_GAME));
        }
        // LOAD button
        else if((mx >= loadButton.x) && (mx <= (loadButton.x + loadButton.width))
                &&(my >= loadButton.y) && (my <= (loadButton.y + loadButton.height))) {
            eventQueue.addEvent(new Event(Event.EventType.LOAD_GAME));
        }
        // DEFAULT button
        else if((mx >= initialMapButton.x) && (mx <= (initialMapButton.x + initialMapButton.width))
                &&(my >= initialMapButton.y) && (my <= (initialMapButton.y + initialMapButton.height))) {

        }
        // QUIT button
        else if((mx >= quitButton.x) && (mx <= (quitButton.x + quitButton.width))
                &&(my >= quitButton.y) && (my <= (quitButton.y + quitButton.height))){
            eventQueue.addEvent(new Event(Event.EventType.QUIT_GAME));
        }
        System.out.println("Handle menu button click at: " + mx + ", " + my);
    }

    @Override
    public void handleMouseMove(MouseEvent e) {
        // Menu-specific mouse movement logic (e.g., highlight buttons)
    }
}
