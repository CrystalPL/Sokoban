package pl.crystalek.sokoban.game.listener;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.count.TimeCounter;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.ranking.Ranking;

public final class ResetMapListener implements EventHandler<KeyEvent> {
    private final Game game;

    public ResetMapListener(final Game game) {
        this.game = game;
    }

    @Override
    public void handle(final KeyEvent event) {
        if (event.getCode() != KeyCode.SPACE) {
            return;
        }

        final Progress oldProgress = game.getOldProgress();
        final Progress progress = game.getProgress();
        final TimeCounter timeCounter = game.getTimeCounter();
        final Ranking ranking = progress.getRanking();

        ranking.setPlayTime(ranking.getPlayTime() + timeCounter.getTimeCounterTask().getPlayTime());
        timeCounter.getTimer().cancel();
        progress.setSetCrates(oldProgress.getSetCrates());

        game.loadGame(oldProgress.getMapLines(), progress);
    }
}
