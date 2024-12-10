package game.model.Map;

import java.io.Serializable;

// Represents a single tile on the game map
public class Tile implements Serializable {

    // ATTRIBUTES
    private final int x, y;
    private final TerrainType terrain;

    // CONSTRUCTOR
    public Tile(int x, int y, TerrainType terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }

    public TerrainType getTerrain() {
        return terrain;
    }
}
