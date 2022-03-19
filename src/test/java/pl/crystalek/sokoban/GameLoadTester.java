package pl.crystalek.sokoban;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;
import pl.crystalek.sokoban.map.DefaultMap;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
final class GameLoadTester {
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
    void loadGameTest() throws URISyntaxException, IOException {
        final Progress progress = new Progress(new String[0][0], "test", new DefaultMap("test"));
        final Game game = new Game(mainLoader, progress, new GridPane());
        final List<String> mapStringList = Files.readAllLines(new File(getClass().getResource("/testLevel.txt").toURI()).toPath());

        Assertions.assertNotEquals(game.loadGame(mapStringList, progress), "");
    }
}
