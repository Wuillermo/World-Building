package game.model.Map;

import java.io.*;
import java.net.URL;

public class MapIO {

    public static void save(Map gameMap) {
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

    public static Map load() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(MapIO.class.getResource("/maps/map.mp").getFile()))) {
            Map loadedMap = (Map) objectInputStream.readObject();
            System.out.println("Loaded Map ID: " + loadedMap.getId());
            return loadedMap;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
