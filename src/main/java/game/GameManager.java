package game;

import game.model.Map.Map;
import game.model.Map.MapIO;
import game.model.Map.TerrainType;
import game.view.GamePanel;

public class GameManager extends Game implements Runnable {

    // ATTRIBUTES
    private final int FPS = 60;

    // General game variables
    private final int gameFactions;

    private GamePanel gamePanel;
    private Thread gameThread;
    private boolean isRunning;
    private Handler handler;
    private boolean inGame;
    private TerrainType defaultTerrain;

    public GameManager() {
        this.gameFactions = 5;
        this.gamePanel = new GamePanel(this);
        this.inGame = false;
        this.defaultTerrain = TerrainType.PLAINS;
        startGameThread();
        init();
    }

    // METHODS
    // Initialize the game
    private void init() {
        handler = new Handler();
        gamePanel.setHandler(handler);
    }

    private synchronized void startGameThread() {
        if(isRunning) return;

        gameThread = new Thread(this);
        gameThread.start();
        isRunning = true;
    }

    private synchronized void stop() {
        if(!isRunning) return;

        try {
            gameThread.join();
            isRunning = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000f/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                //UPDATE: Update information
                gamePanel.update();
                delta--;
            }
            //DRAW: Draw with the updated information
            gamePanel.repaint();
        }
        stop();
    }

    // CREATE A NEW GAME
    // TODO Organize steps to create the full game and save at the end
    private void createNewGame(Object... config) {

    }

    public void playButtonPressed() {
        if(inGame) return;
        inGame = true;
        gameStep = gameStep.next();
        System.out.println("Next step: " + gameStep.toString());
        newMap();
        gamePanel.setState(GamePanel.STATE.GAME);
    }

    public void loadButtonPressed() {
        if(inGame) return;
        inGame = true;
        gameStep = gameStep.next();
        System.out.println("Next step: " + gameStep.toString());
        loadMap();
        gamePanel.setState(GamePanel.STATE.GAME);
    }

    public void defaultInitializing() {
        if(inGame) return;
        inGame = true;
        gameStep = gameStep.next();
        System.out.println("Next step: " + gameStep.toString());
        Map map = Map.defaultMapCreator(gamePanel);
        handler.addObject(map);
        map.setVisible(true);
        gamePanel.setState(GamePanel.STATE.GAME);
    }

    public void newMap() {
        System.out.println("Create Map");
        map = new Map(ID.Map, gamePanel.getScreenWidth(), gamePanel.getScreenHeight(), gamePanel.getTileSize(), defaultTerrain);

        handler.addObject(map);
        map.setVisible(true);
    }

    private void loadMap() {
        System.out.println("Load Map");
        map = MapIO.load();

        handler.addObject(map);
        map.setVisible(true);
    }

    public void saveMap() {
        MapIO.save(map);
        System.out.println("Map Saved");
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}