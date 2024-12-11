package game.model.core;

import game.model.map.Map;

public abstract class Game {
    protected volatile boolean isRunning;
    protected volatile boolean threadsRunning;
    protected Map map;
    protected int turn;
    protected int gameFactions;

    // Probably not needed
    protected enum GameSteps {
        NOT_STARTED, LOAD_MAP, INITIALIZE_AI, ADD_PLAYER, IN_GAME, QUIT_GAME;

        private GameSteps prevStep = null;
        private GameSteps nextStep = null;

        static {
            GameSteps[] values = GameSteps.values();
            for (int i = 1; i <= values.length; i++) {
                GameSteps current = values[i % values.length];
                current.prevStep = values[i - 1];
                current.nextStep = values[(i + 1) % values.length];
            }
        }
        public GameSteps prev() {
            return prevStep;
        }

        public GameSteps next() {
            return nextStep;
        }

    }
    protected GameSteps gameStep = GameSteps.NOT_STARTED;

    protected abstract void init();
    protected abstract void startGameThreads();
    protected abstract void stop();
}
