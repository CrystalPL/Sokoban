package pl.crystalek.sokoban.io.file;

import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FileManager {
    private final Map<String, InputStream> imageFileList = new HashMap<>();
    private final Map<String, InputStream> mapFileList = new HashMap<>();
    private final Map<String, InputStream> soundFileList = new HashMap<>();
    private final List<InputStream> fxmlFileList = new ArrayList<>();
    private final FileLoader fileLoader;
    private final FileSaver fileSaver;
    private final MainLoader mainLoader;
    private Map<String, File> userMapFileList;
    private Map<String, File> userGameSaveList;
    private File programDirectory;
    private File userMapDirectory;
    private File progressDirectory;
    private File settingsFile;
    private File rankingFile;

    public FileManager(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
        this.fileLoader = new FileLoader(this, mainLoader);
        this.fileSaver = new FileSaver(this, mainLoader);
    }

    public void load() throws LoadResourcesException, LoadUserFileException, CreateFileException {
        checkFilesExist();
        fileLoader.loadFiles();
    }

    public void save() throws SaveUserFileException {
        fileSaver.saveFiles();
    }

    public void checkFilesExist() throws CreateFileException {
        try {
            final File programDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), "Sokoban");
            final File userMapDirectory = new File(programDirectory, "your maps");
            final File progressDirectory = new File(programDirectory, "game save");

            if (!programDirectory.exists()) {
                programDirectory.mkdir();
            }

            if (!userMapDirectory.exists()) {
                userMapDirectory.mkdir();
            }

            if (!progressDirectory.exists()) {
                progressDirectory.mkdir();
            }

            final File settingsFile = new File(programDirectory, "settings.sokoban");
            final File rankingFile = new File(programDirectory, "ranking.sokoban");

            if (!settingsFile.exists()) {
                settingsFile.createNewFile();
                System.out.println(Lang.CREATE_SETTINGS_FILE);
            }

            if (!rankingFile.exists()) {
                rankingFile.createNewFile();
                System.out.println(Lang.CREATE_RANKING_FILE);
            }


            this.programDirectory = programDirectory;
            this.userMapDirectory = userMapDirectory;
            this.progressDirectory = progressDirectory;
            this.settingsFile = settingsFile;
            this.rankingFile = rankingFile;
        } catch (final IOException exception) {
            throw new CreateFileException(Lang.ERROR_WHILE_CREATING, exception);
        }
    }

    public Map<String, File> getUserMapFileList() {
        return userMapFileList;
    }

    public void setUserMapFileList(final Map<String, File> userMapFileList) {
        this.userMapFileList = userMapFileList;
    }

    public File getUserMapDirectory() {
        return userMapDirectory;
    }

    public File getProgramDirectory() {
        return programDirectory;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public Map<String, InputStream> getImageFileList() {
        return imageFileList;
    }

    public Map<String, InputStream> getMapFileList() {
        return mapFileList;
    }

    public List<InputStream> getFXMLFileList() {
        return fxmlFileList;
    }

    public MainLoader getMainLoader() {
        return mainLoader;
    }

    public FileSaver getFileSaver() {
        return fileSaver;
    }

    public File getProgressDirectory() {
        return progressDirectory;
    }

    public Map<String, File> getUserGameSaveList() {
        return userGameSaveList;
    }

    public void setUserGameSaveList(final Map<String, File> userGameSaveList) {
        this.userGameSaveList = userGameSaveList;
    }

    public File getRankingFile() {
        return rankingFile;
    }

    public Map<String, InputStream> getSoundFileList() {
        return soundFileList;
    }

    public FileLoader getFileLoader() {
        return fileLoader;
    }
}
