package pl.crystalek.sokoban.main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.crystalek.sokoban.controller.ConfirmationController;
import pl.crystalek.sokoban.controller.ConfirmationType;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;

public final class CloseGameListener implements EventHandler<KeyEvent> {
    private final MainLoader mainLoader;

    public CloseGameListener(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final KeyEvent event) {
        if (event.getCode() != KeyCode.ESCAPE) {
            return;
        }

        final ConfirmationController confirmationController = mainLoader.getController(ConfirmationController.class);
        confirmationController.setConfirmationType(ConfirmationType.EXIT);
        confirmationController.getTextLabel().setText(Lang.DO_YOU_WANT_EXIT_GAME);
        mainLoader.getViewLoader().getStage(ConfirmationController.class).show();
    }
}
