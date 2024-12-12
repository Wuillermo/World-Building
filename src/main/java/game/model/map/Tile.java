package game.model.map;

import java.io.Serializable;

// Represents a single tile on the game map
public class Tile implements Serializable {

    // ATTRIBUTES
    private final int x, y;
    private TerrainType terrain;

    // CONSTRUCTOR
    public Tile(int x, int y, TerrainType terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }

    public TerrainType getTerrain() {
        return terrain;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTerrain(TerrainType terrain) {
        this.terrain = terrain;
    }
}
