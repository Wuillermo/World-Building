package game.model.map;

import java.io.*;
import java.net.URL;

public class MapIO {

    public static void save(GameMap gameMap) {
        final URL urlToResourcesFolder = MapIO.class.getResource("/");
        File mapsFolder = new File(urlToResourcesFolder.getFile() + "/maps/");

        if(!mapsFolder.exists()) {
            mapsFolder.mkdir();
        }

        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(mapsFolder.toString() + "/map.mp"))) {
            objectOutputStream.writeObject(gameMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameMap load() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MapIO.class.getResource("/maps/map.mp").getFile()))) {
            GameMap loadedGameMap = (GameMap) objectInputStream.readObject();
            System.out.println("Loaded Map ID: " + loadedGameMap.getId());
            return loadedGameMap;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
