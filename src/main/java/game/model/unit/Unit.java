package game.model.unit;

import game.model.factions.Faction;
import game.model.map.Tile;

// Represents a military or civilian unit
public class Unit {
    String type;
    int health;
    int movementDistance;
    int attack, defense;
    Tile currentLocation;
    Faction owner;

    public Unit(){

    }

    public void moveTo(Tile destination){
    }

    public void attack(Unit target){
    }
}
