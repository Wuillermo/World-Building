package game.model.factions;

import game.model.core.GameObject;
import game.model.core.ID;
import game.model.city.City;
import game.model.unit.Unit;

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
