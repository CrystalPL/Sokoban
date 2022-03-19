package pl.crystalek.sokoban;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pl.crystalek.sokoban.controller.editor.ImportMap;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@ExtendWith(ApplicationExtension.class)
final class ImportMapTester {
    private MainLoader mainLoader;

    @Start
    private void start(final Stage stage) throws LoadResourcesException, UnsupportedAudioFileException, IOException, LineUnavailableException, LoadUserFileException {
        final ViewLoader viewLoader = new ViewLoader(stage);
        final MainLoader mainLoader = new MainLoader(viewLoader);
        viewLoader.setMainLoader(mainLoader);
        mainLoader.load();

        this.mainLoader = mainLoader;
    }

    @Test
    void importMapTest() throws URISyntaxException {
        final ImportMap importMap = mainLoader.getController(MapEditorController.class).getImportMap();
        final File file = new File(getClass().getResource("/testImportLevel.txt").toURI());

        Platform.runLater(() -> Assertions.assertFalse(importMap.checkFile(file)));
    }
}
