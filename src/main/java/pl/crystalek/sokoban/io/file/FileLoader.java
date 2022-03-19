package pl.crystalek.sokoban.io.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.ranking.RankingManager;
import pl.crystalek.sokoban.settings.Settings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class FileLoader {
    private final FileManager fileManager;
    private final MainLoader mainLoader;

    FileLoader(final FileManager fileManager, final MainLoader mainLoader) {
        this.fileManager = fileManager;
        this.mainLoader = mainLoader;
    }

    public void loadFiles() throws LoadResourcesException, LoadUserFileException {
        loadResources();
        mainLoader.setSettings(loadUserFile(fileManager.getSettingsFile()).map(object -> (Settings) object).orElseGet(Settings::new));
        mainLoader.setRankingManager(loadUserFile(fileManager.getRankingFile()).map(object -> (RankingManager) object).orElseGet(RankingManager::new));
        fileManager.setUserMapFileList(loadUserFiles(fileManager.getUserMapDirectory()));
        fileManager.setUserGameSaveList(loadUserFiles(fileManager.getProgressDirectory()));
    }

    private void loadResources() throws LoadResourcesException {
        final Map<String, InputStream> mapFileList = fileManager.getMapFileList();
        final Map<String, InputStream> imageFileList = fileManager.getImageFileList();
        final List<InputStream> fxmlFileList = fileManager.getFXMLFileList();
        final Map<String, InputStream> soundFileList = fileManager.getSoundFileList();

        try (
                final InputStream fileNameStream = getClass().getResourceAsStream("/FileNameList.txt")
        ) {
            final String[] fileNameList = IOUtils.toString(fileNameStream, StandardCharsets.UTF_8).split("\r\n");
            for (final String fileName : fileNameList) {
                final String[] fileNameSplit = FilenameUtils.removeExtension(fileName).split("/");
                final String fileNameWithoutExtension = fileNameSplit[fileNameSplit.length - 1];

                switch (FilenameUtils.getExtension(fileName).toLowerCase()) {
                    case "txt":
                        mapFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/map/" + fileName));
                        break;
                    case "png":
                        imageFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/img/" + fileName));
                        break;
                    case "fxml":
                        fxmlFileList.add(getClass().getResourceAsStream("/fxml/" + fileName));
                        break;
                    case "wav":
                        soundFileList.put(fileNameWithoutExtension, getClass().getResourceAsStream("/sound/" + fileName));
                        break;
                }
            }

        } catch (final IOException exception) {
            throw new LoadResourcesException(Lang.ERROR_WHILE_LOADING_GAMES_FILE, exception);
        }
    }

    private Optional<Object> loadUserFile(final File fileToLoad) throws LoadUserFileException {
        try (
                final FileInputStream fileInputStream = new FileInputStream(fileToLoad);
                final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            final Object readObject = objectInputStream.readObject();
            return readObject instanceof Settings || readObject instanceof RankingManager ? Optional.of(readObject) : Optional.empty();
        } catch (final EOFException exception) {
            return Optional.empty();
        } catch (final IOException | ClassNotFoundException exception) {
            throw new LoadUserFileException(Lang.ERROR_WHILE_LOADING_FILE.replace("{FILE_NAME}", fileToLoad.getName()), exception);
        }
    }

    private Map<String, File> loadUserFiles(final File directory) {
        final Map<String, File> resultMap = new HashMap<>();

        for (final File file : directory.listFiles()) {
            resultMap.put(FilenameUtils.removeExtension(file.getName()), file);
        }

        return resultMap;
    }
}
