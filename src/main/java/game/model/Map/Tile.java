package game.model;

// Represents a single tile on the game map
public class Tile {

    // ATTRIBUTES
    private final int x, y;
    private final TerrainType terrain;

    // CONSTRUCTOR
    public Tile(int x, int y, TerrainType terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }
}
