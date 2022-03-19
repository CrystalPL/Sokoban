package pl.crystalek.sokoban;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pl.crystalek.sokoban.controller.editor.ExportMap;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.editor.convert.ConvertStringToGridPane;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
class ExportMapTester {
    private MainLoader mainLoader;

    @Start
    private void start(final Stage stage) {
        final ViewLoader viewLoader = new ViewLoader(stage);
        final MainLoader mainLoader = new MainLoader(viewLoader);
        viewLoader.setMainLoader(mainLoader);
        try {
            mainLoader.load();
        } catch (final LoadUserFileException | LoadResourcesException | CreateFileException | IOException | LineUnavailableException | UnsupportedAudioFileException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }

        this.mainLoader = mainLoader;
    }

    @Test
    void exportMapTest() throws URISyntaxException, IOException {
        final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
        final MapEditor mapEditor = new MapEditor(mainLoader);
        mapEditorController.setMapEditor(mapEditor);
        mapEditor.openCreator();
        final ConvertStringToGridPane convertStringToGridPane = new ConvertStringToGridPane(mainLoader, mapEditor);
        final List<String> mapStringList = Files.readAllLines(new File(getClass().getResource("/testLevel.txt").toURI()).toPath());
        final ExportMap exportMap = mapEditorController.getExportMap();

        convertStringToGridPane.stringToGridPane(mapStringList);

        final File file = new File(getClass().getResource("/testExportMap.txt").toURI());
        Platform.runLater(() -> exportMap.checkFile(file));
    }
}
