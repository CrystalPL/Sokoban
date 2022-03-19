package pl.crystalek.sokoban.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.crystalek.sokoban.controller.SokobanMainController;
import pl.crystalek.sokoban.exception.CreateFileException;
import pl.crystalek.sokoban.exception.LoadResourcesException;
import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.view.ViewLoader;
import pl.crystalek.sokoban.lang.Lang;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public final class Sokoban extends Application {
    private MainLoader mainLoader;

    @Override
    public void start(final Stage stage) {
        final ViewLoader viewLoader = new ViewLoader(stage);
        final MainLoader mainLoader = new MainLoader(viewLoader);
        viewLoader.setMainLoader(mainLoader);
        try {
            mainLoader.load();
        } catch (final LoadUserFileException | LoadResourcesException | CreateFileException | IOException | LineUnavailableException | UnsupportedAudioFileException exception) {
            exception.printStackTrace();
            System.exit(-1);
            return;
        }

        final Scene scene = new Scene(viewLoader.getPane(SokobanMainController.class));
        scene.setOnKeyPressed(new CloseGameListener(mainLoader));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        this.mainLoader = mainLoader;
    }

    @Override
    public void stop() {
        try {
            mainLoader.getFileManager().save();
            System.out.println(Lang.SAVING_COMPLETE);
        } catch (final SaveUserFileException exception) {
            exception.printStackTrace();
        }
    }
}
