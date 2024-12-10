package game.view;

import game.model.core.GameManager;
import game.model.core.Handler;
import game.controller.KeyController;
import game.controller.MouseController;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; //16x16 tile
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; //48x48 displayed tile size
    private final int maxScreenCol = 27;
    private final int maxScreenRow = 20;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;


    // GAME ATTRIBUTES
    private GameManager gameManager;
    private Handler handler;
    private final Menu menu;
    private final KeyController keyController;
    private final MouseController mouseController;

    public enum STATE {
        MENU,
        GAME;
    };
    private STATE state = STATE.MENU;

    // CONSTRUCTOR
    public GamePanel(GameManager gameManager) {
        this.gameManager = gameManager;
        keyController = new KeyController(this, gameManager);
        mouseController = new MouseController(this);

        // Make the window
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // If set to true, all drawing from this component will be done in an offscreen painting buffer
        this.addKeyListener(keyController);
        this.addMouseListener(mouseController);
        this.setFocusable(true);

        menu = new Menu(this);
    }

    // METHODS
    public void update() {
        // Updates the game every FPS
        if(state == STATE.GAME){
            handler.update();
        }
    }

    public void paintComponent(Graphics g) {
        // Renders the game
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
        if(state == STATE.GAME) {
            handler.render(g);
        }else if(state == STATE.MENU){
            menu.render(g);
        }
    }

    public void menuButtonPressed(String buttonPressed) {
        switch(buttonPressed) {
            case "PLAY" -> {
                gameManager.playButtonPressed();
            }
            case "LOAD" -> {
                gameManager.loadButtonPressed();
            }
            case "DEFAULT" -> {
                gameManager.defaultInitializing();
            }
        }
    }

    public void setState(STATE state){
        this.state = state;
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Menu getMenu() {
        return menu;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public STATE getState(){
        return state;
    }
    public int getTileSize() {
        return tileSize;
    }
}
