package pl.crystalek.sokoban.game.count;

import pl.crystalek.sokoban.controller.LevelFinishController;
import pl.crystalek.sokoban.controller.game.GameController;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.game.Game;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.ranking.Ranking;
import pl.crystalek.sokoban.util.TimeUtil;

import javax.sound.sampled.Clip;
import java.util.Timer;

public final class TimeCounter {
    private final static int POINT_FOR_TIME = 20;
    private final MainLoader mainLoader;
    private TimeCounterTask timeCounterTask;

    public TimeCounter(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    public void start() {
        final Timer timer = new Timer();
        this.timeCounterTask = new TimeCounterTask(mainLoader);
        timer.schedule(timeCounterTask, 0, 1000);
    }

    public void stop() {
        timeCounterTask.cancel();
        final Game game = mainLoader.getController(GameController.class).getGame();
        final Progress progress = game.getOldProgress();
        final Ranking ranking = progress.getRanking();

        final int playTime = ranking.getPlayTime() + timeCounterTask.getPlayTime();
        final int pointsForTime = Math.min(timeCounterTask.getCounting() * POINT_FOR_TIME, progress.getBonus());

        ranking.setPlayTime(playTime);
        ranking.setPointsForTime(pointsForTime);
        ranking.setStepsNumber(game.getProgress().getRanking().getStepsNumber());
        mainLoader.getRankingManager().add(ranking);
        progress.setRanking(new Ranking());

        final LevelFinishController levelFinishController = mainLoader.getController(LevelFinishController.class);
        levelFinishController.getPlayTime().setText(TimeUtil.getDateInString(playTime * 1_000L, ", ", true));
        levelFinishController.getGainedPoints().setText(String.valueOf(pointsForTime));
        levelFinishController.getStepsNumber().setText(String.valueOf(ranking.getStepsNumber()));
        mainLoader.getViewLoader().setWindow(LevelFinishController.class);

        final FileManager fileManager = mainLoader.getFileManager();
        try {
            fileManager.getFileSaver().saveFile(fileManager.getRankingFile(), mainLoader.getRankingManager());
        } catch (final SaveUserFileException exception) {
            exception.printStackTrace();
        }

        final Clip clip = mainLoader.getSoundList().get("victory");
        clip.setFramePosition(0);
        clip.start();
    }

    public TimeCounterTask getTimeCounterTask() {
        return timeCounterTask;
    }

    public TimeCounterTask getTimer() {
        return timeCounterTask;
    }
}
