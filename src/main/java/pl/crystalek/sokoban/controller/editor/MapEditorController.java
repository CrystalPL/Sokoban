package pl.crystalek.sokoban.controller.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.ConfirmationType;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.GameModuleChoiceController;
import pl.crystalek.sokoban.controller.load.LoadMapToEditController;
import pl.crystalek.sokoban.controller.load.LoadUtil;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.UserMap;

public final class MapEditorController implements Controller {
    private MainLoader mainLoader;
    private MapEditor mapEditor;
    private ImportMap importMap;
    private ExportMap exportMap;
    private SaveMap saveMap;
    @FXML
    private GridPane mapBox;
    @FXML
    private VBox blockBox;
    @FXML
    private AnchorPane editedAreaPane;
    @FXML
    private Button importMapButton;
    @FXML
    private Button exportMapButton;
    @FXML
    private Button saveMapButton;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
        this.importMap = new ImportMap(mainLoader);
        importMapButton.setOnAction(importMap);
        this.exportMap = new ExportMap(mainLoader);
        exportMapButton.setOnAction(exportMap);
        this.saveMap = new SaveMap(mainLoader);
        saveMapButton.setOnAction(saveMap);
    }

    @FXML
    private void mapSettings(final ActionEvent event) {
        final MapSettingsController mapSettingsController = mainLoader.getController(MapSettingsController.class);
        final UserMap editedMap = mapEditor.getEditedMap();
        mapSettingsController.getBonusForTimeTextField().setText(String.valueOf(editedMap.getBonus()));
        mapSettingsController.getMapNameTextField().setText(editedMap.getName());
        mapSettingsController.getCloseGameCheckBox().setSelected(editedMap.isCloseGameWhenTimeEnd());
        mapSettingsController.getPlayTimeTextField().setText(String.valueOf(editedMap.getTimeInSeconds()));
        mainLoader.getViewLoader().setWindow(MapSettingsController.class);
    }

    @FXML
    private void leave(final ActionEvent event) {
        if (!mapEditor.getEditedMap().isChangesToSave()) {
            clearMapWhenLeave();
            return;
        }

        final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
        controller.setConfirmationType(ConfirmationType.LEAVE);
        controller.getTextLabel().setText(Lang.DO_YOU_WANT_LEAVE_EDITOR);
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    @FXML
    private void resetMap(final ActionEvent event) {
        final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
        controller.setConfirmationType(ConfirmationType.RESETMAP);
        controller.getTextLabel().setText(Lang.DO_YOU_WANT_RESET_MAP);
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }

    @FXML
    private void showMapList(final ActionEvent event) {
        final LoadMapToEditController controller = mainLoader.getController(LoadMapToEditController.class);
        controller.setLoadUtil(new LoadUtil(mainLoader));
        mainLoader.getViewLoader().setWindow(LoadMapToEditController.class);
    }

    public void clearMap() {
        for (final Node child : mapBox.getChildren()) {
            if (child instanceof Pane) {
                ((Pane) child).getChildren().clear();
            }
        }

    }

    public void clearMapWhenLeave() {
        final ViewLoader viewLoader = mainLoader.getViewLoader();
        viewLoader.setWindow(GameModuleChoiceController.class);
        mapBox.setGridLinesVisible(false);
        mapBox.getChildren().clear();
        blockBox.getChildren().clear();
    }

    public MapEditor getMapEditor() {
        return mapEditor;
    }

    public void setMapEditor(final MapEditor mapEditor) {
        this.mapEditor = mapEditor;
    }

    public GridPane getMapBox() {
        return mapBox;
    }

    public VBox getBlockBox() {
        return blockBox;
    }

    public AnchorPane getEditedAreaPane() {
        return editedAreaPane;
    }

    public ExportMap getExportMap() {
        return exportMap;
    }

    public ImportMap getImportMap() {
        return importMap;
    }

    public SaveMap getSaveMap() {
        return saveMap;
    }
}
