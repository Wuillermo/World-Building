package game.view;

import game.controller.InputManager;
import game.model.map.GameMap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; //16x16 tile
    private final int scale = 2;
    private final int tileSize = originalTileSize * scale; //48x48 displayed tile size
    private final int maxScreenCol = 27;
    private final int maxScreenRow = 20;
    private final int screenWidth = 1296;
    private final int screenHeight = 960;

    private final MainMenuPanel mainMenu;
    private GameMap gameMap;

    public enum SCREEN_STATE {
        MAIN_MENU,
        GAME
    }
    private SCREEN_STATE SCREENState = SCREEN_STATE.MAIN_MENU;

    // CONSTRUCTOR
    public GamePanel(InputManager inputManager) {

        this.mainMenu = new MainMenuPanel(screenWidth);

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
            public void keyReleased(KeyEvent e) {
                inputManager.handleKeyReleased(e);
            }
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
        this.setFocusable(true);
    }

    // METHODS
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Renders the game
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        if(SCREENState == SCREEN_STATE.GAME) {
            gameMap.render(g);
        }else if(SCREENState == SCREEN_STATE.MAIN_MENU){
            mainMenu.render(g);
        }
    }

    public void setInGameScreen(GameMap gameMap){
        this.gameMap = gameMap;
        this.SCREENState = SCREEN_STATE.GAME;
    }
    public void setState(SCREEN_STATE SCREENState){
        this.SCREENState = SCREENState;
    }

    public MainMenuPanel getMenu() {
        return mainMenu;
    }
    public int getScreenWidth() {
        return screenWidth;
    }
    public int getScreenHeight() {
        return screenHeight;
    }
    public int getTileSize() {
        return tileSize;
    }
    public int getMaxScreenCol() {
        return maxScreenCol;
    }
    public int getMaxScreenRow() {
        return maxScreenRow;
    }
    public SCREEN_STATE getSCREENState() {
        return SCREENState;
    }
}
