package pl.crystalek.sokoban.controller.game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.SerializationUtils;
import pl.crystalek.sokoban.controller.*;
import pl.crystalek.sokoban.controller.load.LoadGameController;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.count.TimeCounter;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileSaveType;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.ranking.Ranking;

import java.util.List;

public final class GameController implements Controller {
    private MainLoader mainLoader;
    private Game game;
    @FXML
    private GridPane mapBox;
    @FXML
    private Label timeLabel;
    @FXML
    private Button saveButton;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final MouseEvent event) {
        if (saveButton.isDisable()) {
            leaveGameRandomLevel();
        }

        if (!game.getProgress().isChangesToSave()) {
            leaveGame();
            return;
        }

        final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
        controller.setConfirmationType(ConfirmationType.LEAVEGAME);
        controller.getTextLabel().setText(Lang.DO_YOU_WANT_EXIT_GAME);
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    private void leaveGameRandomLevel() {
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        mainStage.removeEventFilter(KeyEvent.KEY_PRESSED, game.getPlayerMoveListener());
        mainStage.removeEventFilter(KeyEvent.KEY_RELEASED, game.getResetMapListener());
        final TimeCounter timeCounter = game.getTimeCounter();
        final Ranking ranking = game.getProgress().getRanking();
        ranking.setPlayTime(ranking.getPlayTime() + timeCounter.getTimeCounterTask().getPlayTime());
        timeCounter.getTimer().cancel();

        mainLoader.getViewLoader().setWindow(ChooseByDifficultyController.class);
    }

    public void leaveGame() {
        if (saveButton.isDisable()) {
            leaveGameRandomLevel();
            return;
        }

        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        loadGameController.getLoadUtil().showProgress();
        mainLoader.getViewLoader().setWindow(LoadGameController.class);
        mainStage.removeEventFilter(KeyEvent.KEY_PRESSED, game.getPlayerMoveListener());
        mainStage.removeEventFilter(KeyEvent.KEY_RELEASED, game.getResetMapListener());
        mapBox.getChildren().clear();
        final TimeCounter timeCounter = game.getTimeCounter();
        final Ranking ranking = game.getProgress().getRanking();
        ranking.setPlayTime(ranking.getPlayTime() + timeCounter.getTimeCounterTask().getPlayTime());
        timeCounter.getTimer().cancel();
    }

    @FXML
    private void saveGame(final MouseEvent event) {
        final Progress progress = game.getProgress();
        if (!progress.isChangesToSave()) {
            mainLoader.getController(DialogController.class).showDialogWindow("warning", Lang.TITLE_WARNING, Lang.NO_CHANGE_TO_SAVE);
            return;
        }

        if (progress.getProgressName() == null) {
            changeName();
            return;
        }
        save();
    }

    public void save() {
        final Progress progress = game.getProgress();
        final String oldSaveName = progress.getOldName();
        if (oldSaveName != null) {
            mainLoader.getFileManager().getUserGameSaveList().remove(oldSaveName).delete();
            progress.setOldName(null);
        }

        progress.setMapLines(game.getConvertGridPaneToString().convertGridPaneToString(game.getBoxLocationList()));
        progress.setStringEditedBlocks(game.getConvertImageToStringImage().convertImageToStringImage(game.getDeleteImageList()));
        progress.getRanking().setPlayTime(game.getTimeCounter().getTimeCounterTask().getPlayTime() + progress.getRanking().getPlayTime());
        progress.setChangesToSave(false);
        final List<Progress> progressList = mainLoader.getProgressManager().getSaveList();
        final Progress progressCopy = SerializationUtils.clone(progress);
        final int progressIndex = progressList.indexOf(game.getOldProgress());

        if (progressIndex != -1) {
            progressList.set(progressIndex, progressCopy);
        } else {
            progressList.add(progressCopy);
        }

        final DialogController dialogController = mainLoader.getController(DialogController.class);
        try {
            mainLoader.getFileManager().getFileSaver().saveUserFile(progress, FileSaveType.PROGRESS);
            dialogController.showDialogWindow("info", Lang.TITLE_INFO, Lang.SAVE_MAP_PROGRESS);
        } catch (final SaveUserFileException exception) {
            dialogController.showDialogWindow("error", Lang.TITLE_ERROR, exception.getMessage());
        }
    }

    @FXML
    private void changeSaveName(final MouseEvent event) {
        changeName();
    }

    private void changeName() {
        final ChangeNameController changeNameController = mainLoader.getController(ChangeNameController.class);
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        mainStage.removeEventFilter(KeyEvent.KEY_RELEASED, game.getResetMapListener());
        mainStage.removeEventFilter(KeyEvent.KEY_PRESSED, game.getPlayerMoveListener());
        game.getTimeCounter().getTimeCounterTask().setPause(true);
        changeNameController.getTextLabel().setText(Lang.ENTER_MAP_NAME);
        mainLoader.getViewLoader().setWindow(ChangeNameController.class);
    }

    public GridPane getMapBox() {
        return mapBox;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(final Game game) {
        this.game = game;
    }
}
