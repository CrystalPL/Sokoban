package pl.crystalek.sokoban.controller.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import pl.crystalek.sokoban.controller.DialogController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileSaveType;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.UserMap;

import java.io.File;

public final class SaveMap implements EventHandler<ActionEvent> {
    private final MainLoader mainLoader;

    SaveMap(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final ActionEvent event) {
        final MapEditor mapEditor = mainLoader.getController(MapEditorController.class).getMapEditor();
        final UserMap editedMap = mapEditor.getEditedMap();
        if (!editedMap.isChangesToSave()) {
            mainLoader.getController(DialogController.class).showDialogWindow("warning", Lang.TITLE_WARNING, Lang.NO_CHANGE_TO_SAVE);
            return;
        }

        if (editedMap.getName() == null) {
            mainLoader.getViewLoader().setWindow(MapSettingsController.class);
            mainLoader.getController(MapSettingsController.class).setOpenFromSaveButton(true);
            return;
        }
        saveMap(mapEditor);
    }

    public void saveMap(final MapEditor mapEditor) {
        final UserMap editedMap = mapEditor.getEditedMap();
        final String oldMapName = editedMap.getOldName();
        final File fileMapToDelete = mainLoader.getFileManager().getUserMapFileList().remove(oldMapName);
        if (fileMapToDelete != null) {
            fileMapToDelete.delete();
            editedMap.setOldName(null);
        }

        editedMap.setMapLines(mapEditor.getGridPaneToString().convertGridPaneToString(mapEditor.getBoxLocationList()));

        try {
            editedMap.setChangesToSave(false);
            if (mainLoader.getMapManager().getUserMapList().stream().noneMatch(x -> x.getName().equalsIgnoreCase(editedMap.getName()))) {
                mainLoader.getMapManager().addMap(editedMap);
            }

            mainLoader.getFileManager().getFileSaver().saveUserFile(editedMap, FileSaveType.MAP);
            mainLoader.getController(DialogController.class).showDialogWindow("info", Lang.TITLE_INFO, Lang.SAVE_MAP);
        } catch (final SaveUserFileException exception) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, exception.getMessage());
            exception.printStackTrace();
        }
    }
}
