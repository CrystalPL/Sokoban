package pl.crystalek.sokoban.io;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.game.progress.ProgressManager;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.io.view.FXMLFileLoader;
import pl.crystalek.sokoban.io.view.ImageLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;
import pl.crystalek.sokoban.map.MapManager;
import pl.crystalek.sokoban.ranking.RankingManager;
import pl.crystalek.sokoban.settings.Settings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MainLoader {
    private final Map<Class<?>, Controller> controllerMap = new HashMap<>();
    private Map<String, Clip> soundList;
    private final FileManager fileManager = new FileManager(this);
    private final ViewLoader viewLoader;
    private MapManager mapManager;
    private ProgressManager progressManager;
    private Map<String, Image> imageList;
    private Settings settings;
    private RankingManager rankingManager;

    public MainLoader(final ViewLoader viewLoader) {
        this.viewLoader = viewLoader;
    }

    public void load() throws LoadUserFileException, LoadResourcesException, CreateFileException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        final MapLoader mapLoader = new MapLoader(fileManager);
        final ProgressLoader progressLoader = new ProgressLoader();
        fileManager.load();
        this.progressManager = progressLoader.getGameProgress(fileManager.getUserGameSaveList());
        this.mapManager = mapLoader.getMaps();
        this.imageList = new ImageLoader().getImageList(fileManager);
        this.soundList = new SoundLoader().getSoundList(fileManager);
        final List<FXMLLoader> fxmlList = new FXMLFileLoader().getFXMLList(fileManager);
        setMapController(fxmlList);
        viewLoader.load(fxmlList);
    }

    private void setMapController(final List<FXMLLoader> fxmlList) {
        for (final FXMLLoader fxmlLoader : fxmlList) {
            final Controller controller = fxmlLoader.getController();
            controller.setManagers(this);
            controllerMap.put(controller.getClass(), controller);
        }
    }

    public <T extends Controller> T getController(final Class<T> controllerClass) {
        return (T) controllerMap.get(controllerClass);
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public Map<String, Image> getImageList() {
        return imageList;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ViewLoader getViewLoader() {
        return viewLoader;
    }

    public ProgressManager getProgressManager() {
        return progressManager;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    public RankingManager getRankingManager() {
        return rankingManager;
    }

    public void setRankingManager(final RankingManager rankingManager) {
        this.rankingManager = rankingManager;
    }

    public Map<String, Clip> getSoundList() {
        return soundList;
    }
}
