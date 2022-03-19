package pl.crystalek.sokoban.controller.load;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.ConfirmationType;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.UserMap;

public final class LoadMapToEditController implements Controller, Load {
    private MainLoader mainLoader;
    private LoadUtil loadUtil;
    @FXML
    private Button deleteButton;
    @FXML
    private VBox mapBox;
    @FXML
    private Button loadButton;
    @FXML
    private Label mapInfoLabel;


    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(MapEditorController.class);
        loadButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    @FXML
    private void delete(final ActionEvent event) {
        final ConfirmationController confirmationController = mainLoader.getController(ConfirmationController.class);
        confirmationController.setConfirmationType(ConfirmationType.DELETE);
        confirmationController.setLoad(this);
        confirmationController.getTextLabel().setText(Lang.DO_YOU_WANT_DELETE_MAP);
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    @FXML
    private void load(final ActionEvent event) {
        final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
        final UserMap editedMap = mapEditorController.getMapEditor().getEditedMap();
        if (editedMap.isChangesToSave()) {
            final ConfirmationController confirmationController = mainLoader.getController(ConfirmationController.class);
            confirmationController.setConfirmationType(ConfirmationType.LOADMAP);
            confirmationController.getTextLabel().setText(Lang.DO_YOU_WANT_LOAD_NEW_MAP);
            mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
            return;
        }
        mapEditorController.clearMap();
        final MapEditor mapEditor = mapEditorController.getMapEditor();
        final UserMap choosenMap = (UserMap) loadUtil.getChosenObject();
        mapEditor.setEditedMap(choosenMap);
        mapEditor.getConvertStringToGridPane().stringToGridPane(choosenMap.getMapLines());
        mainLoader.getViewLoader().setWindow(MapEditorController.class);
        loadButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    public VBox getMapBox() {
        return mapBox;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }

    public LoadUtil getLoadUtil() {
        return loadUtil;
    }

    public void setLoadUtil(final LoadUtil loadUtil) {
        loadUtil.showMapList(this, LoadType.USERMAP);
        this.loadUtil = loadUtil;
    }

    @Override
    public Label getMapInfoLabel() {
        return mapInfoLabel;
    }
}
