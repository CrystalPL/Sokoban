package pl.crystalek.sokoban.editor;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.editor.convert.ConvertGridPaneToString;
import pl.crystalek.sokoban.editor.convert.ConvertStringToGridPane;
import pl.crystalek.sokoban.editor.listener.BoxListenerManager;
import pl.crystalek.sokoban.editor.model.BoxLocation;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.map.UserMap;

import java.util.LinkedList;
import java.util.List;

public final class MapEditor {
    private static final int OFFSET_X = 160;
    private static final int OFFSET_Y = 20;
    private final List<BoxLocation> boxLocationList = new LinkedList<>();
    private final MainLoader mainLoader;
    private final ConvertGridPaneToString convertGridPaneToString;
    private final ConvertStringToGridPane convertStringToGridPane;
    private final BoxListenerManager boxListenerManager;
    private UserMap editedMap = new UserMap();

    public MapEditor(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
        this.convertGridPaneToString = new ConvertGridPaneToString(mainLoader);
        this.convertStringToGridPane = new ConvertStringToGridPane(mainLoader, this);
        this.boxListenerManager = new BoxListenerManager(mainLoader, this);
    }

    public void openCreator() {
        final GridPane mapBox = mainLoader.getController(MapEditorController.class).getMapBox();
        mapBox.setGridLinesVisible(true);

        for (int rowNumber = 0; rowNumber < 20; rowNumber++) {
            for (int columnNumber = 0; columnNumber < 30; columnNumber++) {
                final Pane boxPane = new Pane();
                boxPane.setPrefSize(39, 39);

                boxLocationList.add(new BoxLocation(columnNumber * 39 + 19 + OFFSET_X, rowNumber * 39 + 19 + OFFSET_Y, boxPane));
                mapBox.add(boxPane, columnNumber, rowNumber);
            }
        }

        boxListenerManager.setListeners();
    }

    public List<BoxLocation> getBoxLocationList() {
        return boxLocationList;
    }

    public UserMap getEditedMap() {
        return editedMap;
    }

    public void setEditedMap(final UserMap editedMap) {
        this.editedMap = editedMap;
    }

    public ConvertGridPaneToString getGridPaneToString() {
        return convertGridPaneToString;
    }

    public ConvertStringToGridPane getConvertStringToGridPane() {
        return convertStringToGridPane;
    }

    public BoxListenerManager getBoxListenerManager() {
        return boxListenerManager;
    }
}
