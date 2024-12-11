package game.model.map;

import game.model.core.GameObject;
import game.model.core.ID;
import game.view.Camera;
import game.view.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    private final Camera camera;
    private final BufferedImage bufferedImage;
    private final int width;
    private final int height;
    private final int tileSize;

    public Map(ID id, Camera camera, int width, int height, int tileSize, TerrainType defaultTerrain){
        super(id);
        this.camera = camera;
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.tiles = new Tile[width][height];
        generateDefaultMap(defaultTerrain);
        renderMapToImage(tileSize);
    }

    private void renderMapToImage(int tileSize) {
        Graphics2D g2d = bufferedImage.createGraphics();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                Tile tile = tiles[y][x];
                g2d.setColor(tile.getTerrain().getColor());
                g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
        g2d.dispose();
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
        int screenX = -camera.getTopLeftX();
        int screenY = -camera.getTopLeftY();
        g.drawImage(bufferedImage, screenX, screenY, null);
    }

    public static Map defaultMapCreator(GamePanel gamePanel, Camera camera) {

        int height = 100;
        int width = 75;
        int tileSize = gamePanel.getTileSize();
        int screenHeight = height * tileSize;
        int screenWidth = width * tileSize;

        Random random = new Random();

        Map map = new Map(ID.Map, camera, screenWidth, screenHeight, tileSize, TerrainType.PLAINS);

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
