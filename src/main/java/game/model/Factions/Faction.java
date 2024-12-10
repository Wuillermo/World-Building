package game.model.Factions;

import game.GameObject;
import game.ID;
import game.model.Map.City;
import game.model.Unit;

import java.awt.*;
import java.util.List;

// Represents a player or AI-controlled civilization
public class Faction extends GameObject {
    String name;
    List<City> cities;
    List<Unit> units;
    int gold;

    public Faction(ID id, String name, List<City> cities, List<Unit> units, int gold) {
        super(id);
        this.name = name;
        this.cities = cities;
        this.units = units;
        this.gold = gold;
    }


    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {

    }
}
