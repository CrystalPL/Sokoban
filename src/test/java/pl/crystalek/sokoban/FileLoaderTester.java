package pl.crystalek.sokoban;

import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.io.view.ViewLoader;

@ExtendWith(ApplicationExtension.class)
final class FileLoaderTester {
    private Stage stage;

    @Start
    private void start(final Stage stage) {
        this.stage = stage;
    }

    @Test
    void fileLoaderTest() {
        final ViewLoader viewLoader = new ViewLoader(stage);
        final MainLoader mainLoader = new MainLoader(viewLoader);
        final FileManager fileManager = mainLoader.getFileManager();
        fileManager.checkFilesExist();

        Assertions.assertDoesNotThrow(() -> fileManager.getFileLoader().loadFiles());
    }

}
