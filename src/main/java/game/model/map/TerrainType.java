package game.model.map;

import java.awt.*;
import java.io.Serializable;

public enum TerrainType implements Serializable {
    // Enum instances representing each terrain type
    PLAINS("Plains", 1, 1, 1, true, false, Color.GREEN),
    HILLS("Hills", 2, 0, 2, true, false, Color.WHITE),
    FOREST("Forest", 3, 2, 1, true, false, Color.DARK_GRAY),
    DESERT("Desert", 1, 0, 0, true, false, Color.yellow),
    MOUNTAIN("Mountain", Integer.MAX_VALUE, 0, 0, false, false, Color.MAGENTA),
    WATER("Water", Integer.MAX_VALUE, 0, 0, false, true, Color.BLUE);

    // Attributes for each terrain
    private final String name;          // Friendly name
    private final int movementCost;     // Movement cost for this terrain
    private final int foodYield;        // Food produced per turn
    private final int productionYield;  // Production points per turn
    private final boolean isBuildable;  // Can structures be built here?
    private final boolean isNavigable;  // Can water units move here?
    private final Color color;          // Color

    // Constructor
    TerrainType(String name, int movementCost, int foodYield, int productionYield, boolean isBuildable, boolean isNavigable, Color color) {
        this.name = name;
        this.movementCost = movementCost;
        this.foodYield = foodYield;
        this.productionYield = productionYield;
        this.isBuildable = isBuildable;
        this.isNavigable = isNavigable;
        this.color = color;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getMovementCost() {
        return movementCost;
    }

    public int getFoodYield() {
        return foodYield;
    }

    public int getProductionYield() {
        return productionYield;
    }

    public Color getColor() {
        return color;
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public boolean isNavigable() {
        return isNavigable;
    }

    // Utility methods
    public String getDescription() {
        return String.format("%s: Move cost=%d, Food=%d, Production=%d, Buildable=%b, Navigable=%b",
                name, movementCost, foodYield, productionYield, isBuildable, isNavigable);
    }

    @Override
    public String toString() {
        return name;
    }
}

