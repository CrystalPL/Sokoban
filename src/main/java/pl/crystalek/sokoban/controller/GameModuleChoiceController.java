package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;

public final class GameModuleChoiceController implements Controller {
    private MainLoader mainLoader;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(SokobanMainController.class);
    }

    @FXML
    private void createLevel(final ActionEvent event) {
        final MapEditor mapEditor = new MapEditor(mainLoader);
        mainLoader.getController(MapEditorController.class).setMapEditor(mapEditor);
        mapEditor.openCreator();
        mainLoader.getViewLoader().setWindow(MapEditorController.class);
    }

    @FXML
    private void chooseGameMode(final ActionEvent event) {
        mainLoader.getViewLoader().setWindow(GameModeChoiceController.class);
    }

}
