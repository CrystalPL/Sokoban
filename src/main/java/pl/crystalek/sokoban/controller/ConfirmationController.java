package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.controller.load.Load;
import pl.crystalek.sokoban.controller.load.LoadGameController;
import pl.crystalek.sokoban.controller.load.LoadMapToEditController;
import pl.crystalek.sokoban.controller.load.LoadUtil;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.editor.listener.BoxListenerManager;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.map.UserMap;

import java.time.LocalDateTime;

public final class ConfirmationController implements Controller {
    private MainLoader mainLoader;
    private ConfirmationType confirmationType;
    private Load load;
    @FXML
    private Label textLabel;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void no(final ActionEvent event) {
        mainLoader.getViewLoader().getStage(getClass()).close();
    }

    @FXML
    private void yes(final ActionEvent event) {
        switch (confirmationType) {
            case RESETMAP:
                final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
                final MapEditor mapEditor1 = mapEditorController.getMapEditor();
                mapEditorController.clearMap();
                mapEditor1.getEditedMap().setChangesToSave(true);
                mapEditor1.getEditedMap().setModificationDate(LocalDateTime.now());
                final BoxListenerManager boxListenerManager = mapEditor1.getBoxListenerManager();
                boxListenerManager.resetVariable();
                boxListenerManager.setListeners();
                mainLoader.getController(DialogController.class).showDialogWindow("info", Lang.TITLE_INFO, Lang.MAP_CLEAR);
                break;
            case DELETE:
                final LoadUtil loadUtil = load.getLoadUtil();
                final DefaultMap chosenObject = loadUtil.getChosenObject();
                if (chosenObject instanceof Progress) {
                    final Progress progress = (Progress) chosenObject;
                    mainLoader.getProgressManager().deleteSave(progress);
                    mainLoader.getFileManager().getUserGameSaveList().get(progress.getName()).delete();
                    mainLoader.getController(LoadGameController.class).getSaveBox().getChildren().remove(loadUtil.getClickedButton());
                    load.getLoadButton().setDisable(true);
                    load.getDeleteButton().setDisable(true);
                    break;
                }

                final UserMap chosenMap = (UserMap) chosenObject;
                mainLoader.getMapManager().deleteMap(chosenMap);
                mainLoader.getFileManager().getUserMapFileList().get(chosenMap.getName()).delete();
                load.getMapBox().getChildren().remove(loadUtil.getClickedButton());
                load.getLoadButton().setDisable(true);
                load.getDeleteButton().setDisable(true);
                break;
            case LEAVE:
                mainLoader.getController(MapEditorController.class).clearMapWhenLeave();
                break;
            case LOADMAP:
                final MapEditorController mapEditorController2 = mainLoader.getController(MapEditorController.class);
                final MapEditor mapEditor = mapEditorController2.getMapEditor();
                final LoadMapToEditController loadMapToEditController = mainLoader.getController(LoadMapToEditController.class);
                final UserMap chosenMap2 = (UserMap) loadMapToEditController.getLoadUtil().getChosenObject();
                mapEditorController2.clearMap();
                mapEditor.setEditedMap(chosenMap2);
                mapEditor.getConvertStringToGridPane().stringToGridPane(chosenMap2.getMapLines());
                mainLoader.getViewLoader().setWindow(MapEditorController.class);
                loadMapToEditController.getLoadButton().setDisable(true);
                loadMapToEditController.getDeleteButton().setDisable(true);
                break;
            case EXPORTMAP:
                final MapEditorController mapEditorController3 = mainLoader.getController(MapEditorController.class);
                mapEditorController3.getExportMap().showFileChooser(mapEditorController3.getMapEditor());
                break;
            case IMPORTMAP:
                final MapEditorController mapEditorController4 = mainLoader.getController(MapEditorController.class);
                mapEditorController4.getImportMap().showFileChooser();
                break;
            case LEAVEGAME:
                mainLoader.getController(GameController.class).leaveGame();
                break;
            case EXIT:
                mainLoader.getViewLoader().getMainStage().close();
                break;
        }
        mainLoader.getViewLoader().getStage(getClass()).close();
        textLabel.setText("");
    }

    public Label getTextLabel() {
        return textLabel;
    }

    public void setConfirmationType(final ConfirmationType confirmationType) {
        this.confirmationType = confirmationType;
    }

    public void setLoad(final Load load) {
        this.load = load;
    }
}
