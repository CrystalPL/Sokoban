package pl.crystalek.sokoban.ranking;

import java.io.Serializable;

public final class Ranking implements Serializable, Comparable<Ranking> {
    private static final long serialVersionUID = 1441667038302892290L;
    private int playTime = 0;
    private int stepsNumber = 0;
    private int pointsForTime = 0;
    private String mapName;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(final String mapName) {
        this.mapName = mapName;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(final int playTime) {
        this.playTime = playTime;
    }

    public int getStepsNumber() {
        return stepsNumber;
    }

    public void setStepsNumber(final int stepsNumber) {
        this.stepsNumber = stepsNumber;
    }

    public int getPointsForTime() {
        return pointsForTime;
    }

    public void setPointsForTime(final int pointsForTime) {
        this.pointsForTime = pointsForTime;
    }

    @Override
    public int compareTo(final Ranking ranking) {
        return -Integer.compare(pointsForTime, ranking.pointsForTime);
    }
}
