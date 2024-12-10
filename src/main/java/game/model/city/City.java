package game.model.city;

import game.model.factions.Faction;
import game.model.map.Tile;

import java.util.List;

// Represents a city controlled by a faction
public class City {
    String name;
    Tile location; // May be changed to be more than one tile
    int population;
    List<Building> buildings;
    Faction owner;


}
