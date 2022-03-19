package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.controller.game.ChooseByDifficultyController;
import pl.crystalek.sokoban.controller.load.LoadGameController;
import pl.crystalek.sokoban.controller.load.LoadType;
import pl.crystalek.sokoban.controller.load.LoadUtil;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameModeChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModuleChoiceController.class);
    }

    @FXML
    private void ownMap(final ActionEvent event) {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final LoadUtil loadUtil = new LoadUtil(mainLoader);
        loadUtil.showMapList(loadGameController, LoadType.USERMAP);
        loadGameController.setLoadUtil(loadUtil, true);
        mainLoader.getViewLoader().setWindow(LoadGameController.class);
    }

    @FXML
    private void randomLevel(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(ChooseByDifficultyController.class);
    }

    @FXML
    private void risingLevel(final ActionEvent event) {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final LoadUtil loadUtil = new LoadUtil(mainLoader);
        loadUtil.showMapList(loadGameController, LoadType.DEFAULTMAP);
        loadGameController.setLoadUtil(loadUtil, false);
        mainLoader.getViewLoader().setWindow(LoadGameController.class);
    }

}
