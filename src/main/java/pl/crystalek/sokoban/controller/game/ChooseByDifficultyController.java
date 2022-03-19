package pl.crystalek.sokoban.controller.game;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.commons.lang3.SerializationUtils;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.GameModeChoiceController;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.DefaultMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ChooseByDifficultyController implements Controller {
    private MainLoader mainLoader;
    private DifficultyLevel difficultyLevel;
    @FXML
    private Button startButton;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        final GameController gameController = mainLoader.getController(GameController.class);
        gameController.getSaveButton().setDisable(false);
        startButton.setDisable(true);
        mainLoader.getViewLoader().setWindow(GameModeChoiceController.class);
    }

    @FXML
    private void start(final ActionEvent event) {
        final GameController gameController = mainLoader.getController(GameController.class);
        final List<DefaultMap> mapListWithGivenDifficultyLevel = mainLoader.getMapManager().getDefaultMapList().stream().filter(x -> x.getPriority() == difficultyLevel.getPriority()).collect(Collectors.toList());
        Collections.shuffle(mapListWithGivenDifficultyLevel);
        final DefaultMap defaultMap = mapListWithGivenDifficultyLevel.get(0);
        final List<String> mapLines = defaultMap.getMapLines();
        final String name = defaultMap.getName();
        final Progress progress = new Progress(new String[mapLines.stream().max(Comparator.comparingInt(String::length)).get().length()][mapLines.size()], name, defaultMap);
        progress.getRanking().setMapName(name);
        final Game game = new Game(mainLoader, progress, gameController.getMapBox());

        gameController.setGame(game);
        game.loadGame(mapLines, SerializationUtils.clone(progress));
        progress.setMapLines(mapLines);

        gameController.getSaveButton().setDisable(true);
        startButton.setDisable(true);
        mainLoader.getViewLoader().setWindow(GameController.class);
    }

    @FXML
    private void easyLevel(final ActionEvent event) {
        difficultyLevel = DifficultyLevel.EASY;
        startButton.setDisable(false);
    }

    @FXML
    private void hardLevel(final ActionEvent event) {
        difficultyLevel = DifficultyLevel.HARD;
        startButton.setDisable(false);
    }

    @FXML
    private void mediumLevel(final ActionEvent event) {
        difficultyLevel = DifficultyLevel.MEDIUM;
        startButton.setDisable(false);
    }
}
