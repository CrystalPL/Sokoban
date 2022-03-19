package pl.crystalek.sokoban.io;

import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.map.MapManager;
import pl.crystalek.sokoban.map.UserMap;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

final class MapLoader {
    private final FileManager fileManager;

    MapLoader(final FileManager fileManager) {
        this.fileManager = fileManager;
    }

    private MapManager getDefaultMap() throws LoadUserFileException {
        final MapManager mapManager = new MapManager();

        for (final Map.Entry<String, InputStream> entry : fileManager.getMapFileList().entrySet()) {
            try (
                    final InputStream inputStream = entry.getValue();
                    final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    final BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
            ) {
                final List<String> bufferedReaderLines = bufferedReader.lines().collect(Collectors.toList());
                mapManager.addMap(new DefaultMap(
                        entry.getKey(),
                        Integer.parseInt(bufferedReaderLines.get(0)),
                        bufferedReaderLines.subList(4, bufferedReaderLines.size()),
                        Boolean.parseBoolean(bufferedReaderLines.get(1)),
                        Integer.parseInt(bufferedReaderLines.get(3)),
                        Integer.parseInt(bufferedReaderLines.get(2)) * 60));
            } catch (final IOException exception) {
                throw new LoadUserFileException(Lang.ERROR_WHILE_LOADING_MAP_FILE.replace("{MAP_NAME}", entry.getKey()), exception);
            }
        }
        return mapManager;
    }

    MapManager getMaps() throws LoadUserFileException {
        final MapManager mapManager = getDefaultMap();

        for (final Map.Entry<String, File> entry : fileManager.getUserMapFileList().entrySet()) {
            try (
                    final FileInputStream fileInputStream = new FileInputStream(entry.getValue());
                    final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
            ) {
                final UserMap userMap = (UserMap) objectInputStream.readObject();
                mapManager.addMap(userMap);
            } catch (final IOException | ClassNotFoundException exception) {
                throw new LoadUserFileException(Lang.ERROR_WHILE_LOADING_MAP_FILE.replace("{MAP_NAME}", entry.getKey()), exception);
            }
        }
        return mapManager;

    }
}
