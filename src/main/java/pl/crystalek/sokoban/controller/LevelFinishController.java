package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.io.MainLoader;

public final class LevelFinishController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private Label playTime;
    @FXML
    private Label gainedPoints;
    @FXML
    private Label stepsNumber;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void backToMenu(final ActionEvent event) {
        mainLoader.getController(GameController.class).leaveGame();
    }

    public Label getPlayTime() {
        return playTime;
    }

    public Label getGainedPoints() {
        return gainedPoints;
    }

    public Label getStepsNumber() {
        return stepsNumber;
    }
}
