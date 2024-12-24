package game.model.map;

import game.model.core.GameObject;
import game.model.core.ID;
import game.view.Camera;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

// use transient to declare that you don't want a variable to be saved (serialized) ex:
// private transient Image img;

// Represents the game map
public class GameMap extends GameObject {
    @Serial
    private static final long serialVersionUID = 1L; // Choose a fixed value.

    // ATTRIBUTES
    private final Tile[][] tiles;
    private final Camera camera;
    public final BufferedImage bufferedImage;
    private final int maxColumns;
    private final int maxRows;
    private final int tileSize;
    private final int mapXSize;
    private final int mapYSize;

    public GameMap(ID id, Camera camera, int maxColumns, int maxRows, int tileSize) {
        super(id);
        if (maxColumns <= 0 || maxRows <= 0 || tileSize <= 0) {
            throw new IllegalArgumentException("Map dimensions and tile size must be positive integers.");
        }
        this.camera = camera;
        this.maxColumns = maxColumns;
        this.maxRows = maxRows;
        this.tileSize = tileSize;
        this.mapXSize = maxColumns * tileSize;
        this.mapYSize = maxRows * tileSize;
        this.bufferedImage = new BufferedImage(mapXSize, mapYSize, BufferedImage.TYPE_INT_ARGB);
        this.tiles = new Tile[maxRows][maxColumns];
        generateDefaultMap();
    }

    // Initialize the map with random terrain
    private void generateDefaultMap() {
        for (int x = 0; x < maxColumns; x++){
            for (int y = 0; y < maxRows; y++){
                tiles[y][x] = null;
            }
        }
    }

    public void renderMapToImage() {
        Graphics2D g2d = bufferedImage.createGraphics();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                Tile tile = tiles[y][x];
                if(tile != null) {
                    g2d.setColor(tile.getTerrain().getColor());
                    g2d.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    g2d.setColor(Color.PINK);
                    g2d.drawRect(x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }
        g2d.dispose();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        /*int screenX = -camera.getTopLeftX();
        int screenY = -camera.getTopLeftY();
        g.drawImage(bufferedImage, screenX, screenY, null);*/
    }

    // Creates a completely Random map following the most simple rules possible
    public static GameMap randomMapCreator(Camera camera, int tileSize) {

        int cols = 80;
        int rows = 75;
        int borderSize = 5;

        GameMap gameMap = new GameMap(ID.Map, camera, cols, rows, tileSize);
        Random random = new Random();
        TerrainType terrainType;

        // List to track unassigned tiles
        ArrayList<Point> emptyTiles = new ArrayList<>();

        // Initialize the list with all valid map coordinates
        for (int x = borderSize; x < cols - borderSize; x++) {
            for (int y = borderSize; y < rows - borderSize; y++) {
                emptyTiles.add(new Point(x, y));
            }
        }

        // Set the surrounding boundaries of the map
        terrainType = TerrainType.ICE;
        // Top
        for(int x = 0; x < cols; x++) {
            for(int y = 0; y < borderSize; y++) {
                gameMap.setTile(x, y, terrainType);
                emptyTiles.remove(new Point(x, y));
            }
        }
        // Bottom
        for(int x = 0; x < cols; x++) {
            for(int y = rows - 1; y > rows - borderSize - 1; y--) {
                gameMap.setTile(x, y, terrainType);
                emptyTiles.remove(new Point(x, y));
            }
        }
        // Left
        for(int x = 0; x < borderSize; x++) {
            for(int y = borderSize; y < rows - borderSize; y++) {
                gameMap.setTile(x, y, terrainType);
                emptyTiles.remove(new Point(x, y));
            }
        }
        // Right
        for(int x = cols - borderSize; x < cols; x++) {
            for(int y = borderSize; y < rows - borderSize; y++) {
                gameMap.setTile(x, y, terrainType);
                emptyTiles.remove(new Point(x, y));
            }
        }

        int maxExpansion = 20;

        // Fill the map by selecting from empty tiles
        while(!emptyTiles.isEmpty()) {
            //System.out.println("EmptyTiles size: " + emptyTiles.size());
            // Pick a random Tile
            int index = random.nextInt(emptyTiles.size());
            Point chosenTile = emptyTiles.remove(index);
            int x = chosenTile.x;
            int y = chosenTile.y;

            // Chose a random Terrain Type
            terrainType = choseTerrain(random);
            gameMap.setTile(x, y, terrainType);

            // Populate surroundings with the same terrain type
            gameMap.populateTileSurroundings(x, y, maxExpansion, terrainType, emptyTiles, random);
        }

        gameMap.renderMapToImage();
        System.out.println("Finished map");
        return gameMap;
    }

    // Expands a Tile's surroundings as much as maxExpansion allows (or a bit less due to randomness)
    private void populateTileSurroundings(int x, int y, int maxExpansion, TerrainType terrainType, ArrayList<Point> emptyTiles, Random random) {
        // Randomizes the max tiles it can expand

        int toExpand = maxExpansion - random.nextInt(10);

        // Tiles currently being expanded from
        LinkedList<Tile> tilesExpanding = new LinkedList<>();
        tilesExpanding.add(tiles[y][x]);

        // Map to track if a tile can be expanded
        Map<Tile, Boolean> tileCanExpand = new HashMap<>();

        // Expansion loop
        while(!tilesExpanding.isEmpty() && toExpand > 0) {
            // Select a random tile to expand
            Tile tileExpanding = tilesExpanding.get(random.nextInt(tilesExpanding.size()));

            // Ensure this tile can still expand
            tileCanExpand.putIfAbsent(tileExpanding, true);
            if (!tileCanExpand.get(tileExpanding)) {
                tilesExpanding.remove(tileExpanding);
                continue;
            }

            // Get surrounding tiles for possible expansion
            ArrayList<int[]> surroundings = getValidSurroundingTiles(tileExpanding);
            if (surroundings.isEmpty()) {
                tileCanExpand.put(tileExpanding, false); // Mark as non-expandable
                tilesExpanding.remove(tileExpanding);
                continue;
            }

            // Choose a random surrounding tile to expand
            int[] chosenTile = surroundings.get(random.nextInt(surroundings.size()));
            if (tiles[chosenTile[1]][chosenTile[0]] == null) {
                Tile newTile = setTile(chosenTile[0], chosenTile[1], terrainType);
                emptyTiles.remove(new Point(chosenTile[0], chosenTile[1]));
                tilesExpanding.add(newTile); // Add newly expanded tile
                toExpand--; // Reduce the remaining expansion count
            }
        }
    }

    // Helper method to get valid surrounding tiles
    private ArrayList<int[]> getValidSurroundingTiles(Tile tile) {
        ArrayList<int[]> surroundingTiles = new ArrayList<>();
        int x = tile.getX();
        int y = tile.getY();

        // Check all four directions for valid tiles
        if (isValidTile(x, y - 1)) surroundingTiles.add(new int[]{x, y - 1}); // Up
        if (isValidTile(x, y + 1)) surroundingTiles.add(new int[]{x, y + 1}); // Down
        if (isValidTile(x - 1, y)) surroundingTiles.add(new int[]{x - 1, y}); // Left
        if (isValidTile(x + 1, y)) surroundingTiles.add(new int[]{x + 1, y}); // Right

        return surroundingTiles;
    }

    private boolean isValidTile(int x, int y) {
        if(x < 0 || y < 0 || x >= maxColumns || y >= maxRows) return false;
        else return tiles[y][x] == null;
    }

    public static TerrainType choseTerrain(Random random) {
        int pick = 6;
        boolean flag = true;
        while(flag){
            pick = random.nextInt(TerrainType.values().length);
            if(pick != 6) flag = false;
        }

        return TerrainType.values()[pick];
    }

    public Tile setTile(int x, int y, TerrainType terrainType) {
        Tile newTile = new Tile(x, y, terrainType);
        tiles[y][x] = newTile;
        return newTile;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getMapXSize() {
        return mapXSize;
    }

    public int getMapYSize() {
        return mapYSize;
    }
}
