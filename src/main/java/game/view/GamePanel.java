package game.view;

import game.controller.InputManager;
import game.model.core.GameManager;
import game.model.core.Handler;

import java.awt.*;
import java.awt.event.*;
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
    private final GameManager gameManager;
    private Handler handler;
    private final MainMenu mainMenu;

    public enum SCREEN_STATE {
        MAIN_MENU,
        GAME_MENU,
        GAME
    };
    private SCREEN_STATE SCREENState = SCREEN_STATE.MAIN_MENU;

    // CONSTRUCTOR
    public GamePanel(GameManager gameManager, InputManager inputManager) {
        this.gameManager = gameManager;

        // Configure the window
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // If set to true, all drawing from this component will be done in an offscreen painting buffer

        // Add the Keyboard and Mouse Listeners and bind them to the input manager
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                inputManager.handleKeyPress(e);
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputManager.handleMouseClick(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                inputManager.handleMouseMove(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {}
        });

        this.mainMenu = new MainMenu(this);
        this.setFocusable(true);
    }

    // METHODS
    public void update() {
        // Updates the game every FPS
        if(handler != null) {
            if (SCREENState == SCREEN_STATE.GAME) {
                handler.update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        // Renders the game
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);
        if(SCREENState == SCREEN_STATE.GAME && handler != null) {
            handler.render(g);
        }else if(SCREENState == SCREEN_STATE.MAIN_MENU){
            mainMenu.render(g);
        }
    }

    public void setState(SCREEN_STATE SCREENState){
        this.SCREENState = SCREENState;
    }
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public MainMenu getMenu() {
        return mainMenu;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public SCREEN_STATE getState(){
        return SCREENState;
    }
    public int getTileSize() {
        return tileSize;
    }
}
