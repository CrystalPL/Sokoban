package pl.crystalek.sokoban.io.file;

import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.UserMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public final class FileSaver {
    private final FileManager fileManager;
    private final MainLoader mainLoader;

    FileSaver(final FileManager fileManager, final MainLoader mainLoader) {
        this.fileManager = fileManager;
        this.mainLoader = mainLoader;
    }

    void saveFiles() throws SaveUserFileException {
        final File programDirectory = fileManager.getProgramDirectory();
        if (!programDirectory.exists()) {
            programDirectory.mkdir();
        }

        saveFile(fileManager.getSettingsFile(), mainLoader.getSettings());
        saveFile(fileManager.getRankingFile(), mainLoader.getRankingManager());
        saveUserFiles();
    }

    public void saveFile(final File fileToSave, final Object objectToSave) throws SaveUserFileException {
        final File programDirectory = fileManager.getProgramDirectory();
        if (!programDirectory.exists()) {
            programDirectory.mkdir();
        }

        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(objectToSave);
        } catch (final IOException exception) {
            throw new SaveUserFileException(Lang.ERROR_WHILE_SAVING_FILE.replace("{FILE_NAME}", fileToSave.getName()), exception);
        }
    }

    private void saveUserFiles() throws SaveUserFileException {
        for (final UserMap userMap : fileManager.getMainLoader().getMapManager().getUserMapList()) {
            saveUserFile(userMap, FileSaveType.MAP);
        }

        for (final Progress progress : fileManager.getMainLoader().getProgressManager().getSaveList()) {
            saveUserFile(progress, FileSaveType.PROGRESS);
        }
    }

    public void saveUserFile(final UserMap userMap, final FileSaveType fileSaveType) throws SaveUserFileException {
        final String name = userMap.getName();
        final Map<String, File> userFileList = fileSaveType == FileSaveType.MAP ? fileManager.getUserMapFileList() : fileManager.getUserGameSaveList();
        final File directory = fileSaveType == FileSaveType.MAP ? fileManager.getUserMapDirectory() : fileManager.getProgressDirectory();
        final File programDirectory = fileManager.getProgramDirectory();
        if (!programDirectory.exists()) {
            programDirectory.mkdir();
        }

        if (!directory.exists()) {
            directory.mkdir();
        }

        userFileList.putIfAbsent(name, new File(directory, name + ".sokoban"));

        try (
                final FileOutputStream fileOutputStream = new FileOutputStream(userFileList.get(name));
                final ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(userMap);
        } catch (final IOException exception) {
            throw new SaveUserFileException(Lang.ERROR_WHILE_SAVING_MAP_FILE.replace("{MAP_NAME}", name), exception);
        }
    }
}
