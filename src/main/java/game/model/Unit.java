package game.model;

import game.model.Factions.Faction;
import game.model.Map.Tile;

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
