package pl.crystalek.sokoban.controller.editor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.ConfirmationType;
import pl.crystalek.sokoban.controller.DialogController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.UserMap;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class ExportMap implements EventHandler<ActionEvent> {
    private final MainLoader mainLoader;

    ExportMap(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final ActionEvent event) {
        final MapEditor mapEditor = mainLoader.getController(MapEditorController.class).getMapEditor();
        final UserMap editedMap = mapEditor.getEditedMap();
        if (editedMap.isChangesToSave()) {
            final ConfirmationController controller = mainLoader.getController(ConfirmationController.class);
            controller.setConfirmationType(ConfirmationType.EXPORTMAP);
            controller.getTextLabel().setText(Lang.DO_YOU_WANT_EXPORT_MAP);
            mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
            return;
        }
        showFileChooser(mapEditor);
    }

    public void showFileChooser(final MapEditor mapEditor) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
        fileChooser.setInitialFileName(mapEditor.getEditedMap().getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setTitle(Lang.CHOOSE_SAVE_LOCATION);

        checkFile(fileChooser.showSaveDialog(mainLoader.getViewLoader().getMainStage()));
    }

    public boolean checkFile(final File fileToSave) {
        final MapEditor mapEditor = mainLoader.getController(MapEditorController.class).getMapEditor();

        if (fileToSave == null) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, Lang.NOT_CHOOSE_LOCATION);
            return false;
        }

        if (!fileToSave.exists()) {
            try {
                fileToSave.createNewFile();
            } catch (final IOException exception) {
                mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, Lang.EXPORT_FILE_ERROR);
                exception.printStackTrace();
            }
        }

        final List<String> mapLines = mapEditor.getGridPaneToString().convertGridPaneToString(mapEditor.getBoxLocationList());
        try (
                final FileWriter fileWriter = new FileWriter(fileToSave);
                final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)

        ) {
            for (final String mapLine : mapLines) {
                bufferedWriter.write(mapLine);
                bufferedWriter.newLine();
            }
        } catch (final IOException exception) {
            exception.printStackTrace();
        }

        return true;
    }
}
