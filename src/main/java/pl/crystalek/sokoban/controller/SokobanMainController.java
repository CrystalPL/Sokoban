package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.io.MainLoader;

public final class SokobanMainController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void exit(final ActionEvent event) {
        mainLoader.getViewLoader().getMainStage().close();
    }

    @FXML
    private void settings(final ActionEvent event) {
        mainLoader.getController(GameSettingsController.class).updateSettings();
        mainLoader.getViewLoader().setWindow(GameSettingsController.class);
    }

    @FXML
    private void showRanking(final ActionEvent event) {
        mainLoader.getController(RankingController.class).showRanking();
        mainLoader.getViewLoader().setWindow(RankingController.class);
    }

    @FXML
    private void start(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModuleChoiceController.class);
    }
}
