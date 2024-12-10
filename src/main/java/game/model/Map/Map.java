package game.model;

// Represents the game map
public class Map {
    // ATTRIBUTES
    private final Tile[][] tiles;
    private final int width;
    private final int height;

    public Map(int width, int height, TerrainType defaultTerrain){
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        generateDefaultMap(defaultTerrain);
    }

    // Initialize the map with default terrain
    private void generateDefaultMap(TerrainType defaultTerrain) {
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tiles[x][y] = new Tile(x, y, defaultTerrain);
            }
        }
    }

    // Get a tile by coordinates
    public Tile getTile(int x, int y) {
        if (isValidTile(x, y)) {
            return tiles[x][y];
        }
        throw new IndexOutOfBoundsException("Invalid tile coordinates");
    }

    // Check if coordinates are valid
    public boolean isValidTile(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
