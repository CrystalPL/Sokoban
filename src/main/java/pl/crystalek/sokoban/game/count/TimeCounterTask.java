package pl.crystalek.sokoban.game.count;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pl.crystalek.sokoban.controller.LevelLostController;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.util.TimeUtil;

import javax.sound.sampled.Clip;
import java.util.TimerTask;

public final class TimeCounterTask extends TimerTask {
    private final MainLoader mainLoader;
    private final Label timeLabel;
    private final Progress progress;
    private int counting;
    private int playTime;
    private boolean pause = false;

    TimeCounterTask(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;

        final GameController gameController = mainLoader.getController(GameController.class);
        this.timeLabel = gameController.getTimeLabel();
        this.progress = gameController.getGame().getOldProgress();
        this.counting = progress.getTimeInSeconds();
    }

    @Override
    public void run() {
        if (pause) {
            return;
        }

        if (counting > 0) {
            Platform.runLater(() -> timeLabel.setText(String.valueOf(counting)));
            counting--;
        } else {
            if (progress.isCloseGameWhenTimeEnd()) {
                cancel();
                Platform.runLater(() -> mainLoader.getController(LevelLostController.class).getPlayTime().setText(TimeUtil.getDateInString((progress.getRanking().getPlayTime() + playTime) * 1_000L, ", ", true)));
                mainLoader.getViewLoader().setWindow(LevelLostController.class);
                final Clip clip = mainLoader.getSoundList().get("defeat");
                clip.setFramePosition(0);
                clip.start();
            }
        }

        playTime++;
    }

    public int getCounting() {
        return counting;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPause(final boolean pause) {
        this.pause = pause;
    }
}
