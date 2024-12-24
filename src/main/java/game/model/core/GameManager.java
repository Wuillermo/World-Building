package game.model.core;

import game.controller.Events.EventProcessor;
import game.controller.Events.EventQueue;
import game.controller.GameplayContext;
import game.controller.InputManager;
import game.controller.MenuContext;
import game.model.ecs.ComponentStore;
import game.model.ecs.Entity;
import game.model.ecs.EntityManager;
import game.model.ecs.SystemManager;
import game.model.ecs.components.MovementComponent;
import game.model.ecs.components.PositionComponent;
import game.model.ecs.components.SizeComponent;
import game.model.ecs.systems.CameraMovementSystem;
import game.model.ecs.systems.CameraSystem;
import game.model.ecs.systems.RenderSystem;
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

    // ECS Components
    private final ComponentStore componentStore;
    private final SystemManager systemManager;
    private final EntityManager entityManager;
    private Camera camera;
    private CameraSystem cameraSystem;
    private CameraMovementSystem cameraMovementSystem;
    private RenderSystem renderSystem;

    // Handler
    //private final Handler handler;

    // Input
    private final InputManager inputManager;
    private final GameplayContext gameplayContext;
    private final MenuContext menuContext;
    private final EventProcessor eventProcessor;
    private Thread eventThread;

    // In-game
    //private final Camera camera;
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
        this.isRunning = false;
        this.threadsRunning = false;
        this.inGame = false;

        // Initialize ECS
        this.componentStore = new ComponentStore();
        this.systemManager = new SystemManager(componentStore);
        this.entityManager = new EntityManager();

        // Create Camera entity
        Entity cameraEntity = new Entity();
        MovementComponent cameraMovementComponent = new MovementComponent();
        entityManager.addEntity(cameraEntity);
        componentStore.addComponent(cameraEntity, new PositionComponent(0, 0));
        componentStore.addComponent(cameraEntity, cameraMovementComponent);
        componentStore.addComponent(cameraEntity, new SizeComponent(gamePanel.getScreenWidth(), gamePanel.getScreenHeight()));

        // Create main Systems
        this.cameraSystem = new CameraSystem(componentStore, camera);
        this.cameraMovementSystem = new CameraMovementSystem(componentStore);
        this.renderSystem = new RenderSystem(componentStore, cameraEntity, gameMap);
        cameraMovementSystem.setCameraEntity(cameraEntity);
        cameraMovementSystem.setGameMap(gameMap);

        systemManager.addSystem(cameraSystem);
        systemManager.addSystem(cameraMovementSystem);
        systemManager.addRender(renderSystem);

        this.eventProcessor = new EventProcessor(this, eventQueue, gamePanel, cameraMovementComponent);

        this.window.add(gamePanel);
        this.window.showOnScreen();

        this.inputManager.setContext(menuContext);
    }

    // METHODS
    // Initialize the game
    public void startGame() {
        init();
        startGameThreads();
        renderSystem.setGraphics(gamePanel.getGraphics());
    }

    protected void init() {
        this.gameFactions = 1;
        this.turn = 0;
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
                update((float) delta);
                // Draw: Draw with the updated information
                //gamePanel.repaint();
                systemManager.render((float) delta);
                delta--;
                fps++;
            }


            if(second >= 1000000000) {
                //System.out.println("Ticks per second: " + fps);
                second = 0;
                fps = 0;
            }
        }
        stop();
    }

    private void update(float deltaTime) {
        if(gamePanel.getSCREENState() == GamePanel.SCREEN_STATE.GAME) {
            //handler.update();
            systemManager.update(deltaTime);
        }
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
        //loadMap();
        gamePanel.setState(GamePanel.SCREEN_STATE.GAME);
    }

    public void defaultInitializing() {
        if(inGame) return;
    }

    public synchronized void newMap() {
        System.out.println("Create Map");
        gameMap = GameMap.randomMapCreator(camera, gamePanel.getTileSize());
        cameraMovementSystem.setGameMap(gameMap);
        renderSystem.setGameMap(gameMap);
        cameraMovementSystem.setGameMap(gameMap);

        //handler.addMapLevelObject(gameMap);
        gameMap.setVisible(true);
    }

    private synchronized void loadMap() {
        System.out.println("Load Map");
        gameMap = MapIO.load();

       //handler.addMapLevelObject(gameMap);
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