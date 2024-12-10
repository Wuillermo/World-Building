package game;

import game.model.AIPlayer;
import game.model.Map.Map;
import game.model.Player;

public abstract class Game {
    protected Player player;
    protected AIPlayer[] aiPlayers;
    protected Map map;

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
}
