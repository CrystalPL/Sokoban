package pl.crystalek.sokoban.io.view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.settings.Sound;

import javax.sound.sampled.Clip;

final class ButtonPressedSoundListener implements EventHandler<MouseEvent> {
    private final MainLoader mainLoader;

    ButtonPressedSoundListener(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void handle(final MouseEvent event) {
        if (mainLoader.getSettings().getSound() == Sound.DISABLE) {
            return;
        }

        final Clip click = mainLoader.getSoundList().get("buttonClick");
        click.setFramePosition(0);
        click.start();
    }
}
