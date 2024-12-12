package game.model.core;

import game.controller.Events.EventProcessor;
import game.controller.Events.EventQueue;
import game.controller.GameplayContext;
import game.controller.InputManager;
import game.controller.MenuContext;
import game.model.map.GameMap;
import game.model.map.MapIO;
import game.view.Camera;
import game.view.GamePanel;
import game.view.GameWindow;

public class GameManager extends Game implements Runnable {

    // GAME VARIABLES
    // Window
    private final GameWindow window;
    private final GamePanel gamePanel;

    // Handler
    private Handler handler;

    // Input
    private final InputManager inputManager;
    private final GameplayContext gameplayContext;
    private final MenuContext menuContext;
    private final EventProcessor eventProcessor;
    private Thread eventThread;

    // In-game
    private final Camera camera;
    private GameMap gameMap;

    // Control
    private boolean inGame;

    public GameManager() {
        // Create the window
        this.window = new GameWindow(this);

        EventQueue eventQueue = new EventQueue();
        this.inputManager = new InputManager();
        this.gamePanel = new GamePanel(inputManager);
        this.gameplayContext = new GameplayContext(eventQueue);
        this.menuContext = new MenuContext(eventQueue, gamePanel);
        this.camera = new Camera(gamePanel.getScreenWidth(), gamePanel.getScreenHeight());
        this.eventProcessor = new EventProcessor(this, eventQueue, gamePanel, camera);
        this.isRunning = false;
        this.threadsRunning = false;
        this.inGame = false;

        this.window.add(gamePanel);
        this.window.showOnScreen();

        this.inputManager.setContext(menuContext);
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
        handler.addObject(camera);
        gamePanel.setHandler(handler);
    }

    // Start the game loop
    protected synchronized void startGameThreads() {
        if(isRunning) return;

        Thread gameThread = new Thread(this);
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
        // ATTRIBUTES
        int FPS = 60;
        int fps = 0;
        double second = 0;
        double drawInterval = 1_000_000_000f/ FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(isRunning) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            second += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                // Update: Update information
                if(gamePanel.getSCREENState() == GamePanel.SCREEN_STATE.GAME) handler.update();
                /*gamePanel.update();*/
                // Draw: Draw with the updated information
                gamePanel.repaint();
                delta--;
                fps++;
            }

            if(second >= 1000000000) {
                System.out.println("Ticks per second: " + fps);
                second = 0;
                fps = 0;
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
        gamePanel.setInGameScreen(gameMap);
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
    }

    public synchronized void newMap() {
        System.out.println("Create Map");
        gameMap = GameMap.randomMapCreator(gamePanel, camera, gamePanel.getTileSize());

        handler.addObject(gameMap);
        gameMap.setVisible(true);
    }

    private synchronized void loadMap() {
        System.out.println("Load Map");
        gameMap = MapIO.load();

        handler.addObject(gameMap);
        gameMap.setVisible(true);
    }

    public synchronized void saveMap() {
        MapIO.save(gameMap);
        System.out.println("Map Saved");
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }
    public synchronized void stopRunning() {
        this.isRunning = false;
    }
}