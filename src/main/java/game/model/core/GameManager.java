package game.model.core;

import game.controller.Events.EventProcessor;
import game.controller.Events.EventQueue;
import game.controller.GameplayContext;
import game.controller.InputManager;
import game.controller.MenuContext;
import game.model.map.Map;
import game.model.map.MapIO;
import game.model.map.TerrainType;
import game.view.GamePanel;
import game.view.GameWindow;

public class GameManager extends Game implements Runnable {

    // ATTRIBUTES
    private final int FPS = 60;

    // General game variables

    private final GameWindow window;
    private final GamePanel gamePanel;
    private Thread gameThread;
    private Handler handler;
    private final InputManager inputManager;
    private final GameplayContext gameplayContext;
    private final MenuContext menuContext;
    private final EventProcessor eventProcessor;
    private Thread eventThread;

    private boolean inGame;
    private final TerrainType defaultTerrain;

    public GameManager() {
        // Create the window
        this.window = new GameWindow(this);

        EventQueue eventQueue = new EventQueue();
        this.inputManager = new InputManager();
        this.gamePanel = new GamePanel(inputManager);
        this.eventProcessor = new EventProcessor(this, eventQueue, gamePanel);
        this.gameplayContext = new GameplayContext(eventQueue);
        this.menuContext = new MenuContext(eventQueue, gamePanel);
        this.isRunning = false;
        this.threadsRunning = false;
        this.inGame = false;
        this.defaultTerrain = TerrainType.PLAINS;

        this.window.add(gamePanel);
        this.window.showOnScreen();

        this.inputManager.setContext(new MenuContext(eventQueue, gamePanel));
    }

    // METHODS
    // Initialize the game
    public void startGame() {
        startGameThreads();
        init();
    }

    protected void init() {
        this.gameFactions = 1;
        this.turn = 0;
        handler = new Handler();
        gamePanel.setHandler(handler);
    }

    // Start the game loop
    protected synchronized void startGameThreads() {
        if(isRunning) return;

        gameThread = new Thread(this);
        gameThread.start();
        eventThread = new Thread(eventProcessor);
        eventThread.start();
        isRunning = true;
        threadsRunning = true;
    }

    // Stop the game loop
    public synchronized void stop() {
        if(isRunning) return;
        System.out.println("Stopping Game");

        try {
            if (eventThread.isAlive()) {
                eventThread.join();
            }
            System.out.println("EventProcessor thread joined");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        cleanupResources();
        closeWindow();
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
                // Update: Update information
                gamePanel.update();
                // Draw: Draw with the updated information
                gamePanel.repaint();
                delta--;
            }
        }
        stop();
    }

    // CALLED BY OTHER THREADS
    public synchronized void switchToMenuContext() {
        inputManager.setContext(menuContext);
    }

    public synchronized void switchToGameplayContext() {
        inputManager.setContext(gameplayContext);
    }

    // CREATE A NEW GAME
    // TODO Organize steps to create the full game and save at the end
    private void createNewGame(Object... config) {

    }

    private synchronized void cleanupResources() {
        System.out.println("Cleaning up");
    }

    private synchronized void closeWindow() {
        window.dispose();
        System.out.println("Window Closed");
    }

    public synchronized void newGameButton() {
        if(inGame) return;
        inGame = true;
        gameStep = gameStep.next();
        System.out.println("Next step: " + gameStep.toString());
        newMap();
        gamePanel.setState(GamePanel.SCREEN_STATE.GAME);
    }

    public synchronized void loadGameButton() {
        if(inGame) return;
        inGame = true;
        gameStep = gameStep.next();
        System.out.println("Next step: " + gameStep.toString());
        loadMap();
        gamePanel.setState(GamePanel.SCREEN_STATE.GAME);
    }

    public void defaultInitializing() {
        if(inGame) return;
        inGame = true;
        gameStep = gameStep.next();
        System.out.println("Next step: " + gameStep.toString());
        Map map = Map.defaultMapCreator(gamePanel);
        handler.addObject(map);
        map.setVisible(true);
        gamePanel.setState(GamePanel.SCREEN_STATE.GAME);
    }

    public synchronized void newMap() {
        System.out.println("Create Map");
        map = new Map(ID.Map, gamePanel.getScreenWidth(), gamePanel.getScreenHeight(), gamePanel.getTileSize(), defaultTerrain);

        handler.addObject(map);
        map.setVisible(true);
    }

    private synchronized void loadMap() {
        System.out.println("Load Map");
        map = MapIO.load();

        handler.addObject(map);
        map.setVisible(true);
    }

    public synchronized void saveMap() {
        MapIO.save(map);
        System.out.println("Map Saved");
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }
    public synchronized void stopRunning() {
        this.isRunning = false;
    }
}