package game.model.core;

import game.model.factions.AIPlayer;
import game.model.map.Map;
import game.model.factions.Player;

public abstract class Game {
    protected boolean isRunning;
    protected Player player;
    protected AIPlayer[] aiPlayers;
    protected Map map;

    // Probably not needed
    protected enum GameSteps {
        NOTSTARTED, LOADMAP, INITIALIZEAI, ADDPLAYER, INGAME, QUITGAME;

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
    protected GameSteps gameStep = GameSteps.NOTSTARTED;

    protected abstract void init();
    protected abstract void startGameThread();
    protected abstract void stop();
}
