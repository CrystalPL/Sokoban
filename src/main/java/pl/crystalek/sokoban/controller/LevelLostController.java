package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.io.MainLoader;

public final class LevelLostController implements Controller {
    private MainLoader mainLoader;

    @FXML
    private Label playTime;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        playTime.setText("");
        mainLoader.getController(GameController.class).leaveGame();
    }

    public Label getPlayTime() {
        return playTime;
    }
}
