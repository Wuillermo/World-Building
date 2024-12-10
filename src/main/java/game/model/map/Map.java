package game.model.map;

import game.model.core.GameObject;
import game.model.core.ID;
import game.view.GamePanel;

import java.awt.*;
import java.io.*;
import java.util.Random;

// use transient to declare that you dont want a variable to be saved (serialized) ex:
// private transient Image image;

// Represents the game map
public class Map extends GameObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Choose a fixed value.

    // ATTRIBUTES
    private final Tile[][] tiles;
    private final int width;
    private final int height;
    private final int tileSize;

    public Map(ID id, int width, int height, int tileSize, TerrainType defaultTerrain){
        super(id);
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.tiles = new Tile[width][height];
        generateDefaultMap(defaultTerrain);
    }

    // Initialize the map with default terrain
    private void generateDefaultMap(TerrainType defaultTerrain) {
        Random random = new Random();
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                int pick = random.nextInt(TerrainType.values().length);
                TerrainType terrain = TerrainType.values()[pick];
                tiles[x][y] = new Tile(x, y, terrain);
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


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++) {
                g.setColor(tiles[x][y].getTerrain().getColor());
                g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    public static Map defaultMapCreator(GamePanel gamePanel) {

        int height = 100;
        int width = 75;
        int tileSize = gamePanel.getTileSize();
        int screenHeight = height * tileSize;
        int screenWidth = width * tileSize;

        Random random = new Random();

        Map map = new Map(ID.Map, screenWidth, screenHeight, tileSize, TerrainType.PLAINS);

        TerrainType terrainType = TerrainType.PLAINS;
        for (int x = 0; x < screenWidth; x++){
            if(x >= 3 && (x % 3) - 2 == 0){
                int pick = random.nextInt(TerrainType.values().length);
                terrainType = TerrainType.values()[pick];
            }
            for(int y = 0; y < screenHeight; y++) {
                if(y < 2 || y > screenHeight - 2 || x < 2 || x > screenWidth - 2){
                    // Set the top, left, rigth and bottom Desserts
                    map.setTile(x, y, TerrainType.DESERT);
                }else {
                    map.setTile(x, y, terrainType);
                }

            }
        }

        return map;
    }

    public void setTile(int x, int y, TerrainType terrainType) {
        tiles[x][y] = new Tile(x, y, terrainType);
    }
}
