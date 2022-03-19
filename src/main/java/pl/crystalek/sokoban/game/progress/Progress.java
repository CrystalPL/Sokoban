package pl.crystalek.sokoban.game.progress;

import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.map.UserMap;
import pl.crystalek.sokoban.ranking.Ranking;

public final class Progress extends UserMap {
    private static final long serialVersionUID = 8834273690803183686L;
    private boolean userMap;
    private String[][] stringEditedBlocks;
    private String progressName;
    private Ranking ranking = new Ranking();
    private int setCrates;

    public Progress(final String[][] stringEditedBlocks, final String mapName, final DefaultMap defaultMap) {
        super(mapName, defaultMap.isCloseGameWhenTimeEnd(), defaultMap.getBonus(), defaultMap.getTimeInSeconds());
        this.stringEditedBlocks = stringEditedBlocks;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(final String progressName) {
        this.progressName = progressName;
    }

    public boolean isUserMap() {
        return userMap;
    }

    public void setUserMap(final boolean userMap) {
        this.userMap = userMap;
    }

    public String[][] getStringEditedBlocks() {
        return stringEditedBlocks;
    }

    public void setStringEditedBlocks(final String[][] stringEditedBlocks) {
        this.stringEditedBlocks = stringEditedBlocks;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(final Ranking ranking) {
        this.ranking = ranking;
    }

    public int getSetCrates() {
        return setCrates;
    }

    public void setSetCrates(final int setCrates) {
        this.setCrates = setCrates;
    }
}
