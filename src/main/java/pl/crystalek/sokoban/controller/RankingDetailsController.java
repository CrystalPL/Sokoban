package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.io.MainLoader;

public final class RankingDetailsController implements Controller {
    private MainLoader mainLoader;

    @FXML
    private Label stepsNumberLabel;

    @FXML
    private Label pointsForTimeLabel;

    @FXML
    private Label playTimeLabel;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        final RankingController rankingController = mainLoader.getController(RankingController.class);
        rankingController.getDetailsButton().setDisable(true);
        rankingController.getDeleteButton().setDisable(true);
        mainLoader.getViewLoader().setWindow(RankingController.class);
    }

    Label getStepsNumberLabel() {
        return stepsNumberLabel;
    }

    Label getPointsForTimeLabel() {
        return pointsForTimeLabel;
    }

    Label getPlayTimeLabel() {
        return playTimeLabel;
    }
}
