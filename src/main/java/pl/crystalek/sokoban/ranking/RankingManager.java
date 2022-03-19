package pl.crystalek.sokoban.ranking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class RankingManager implements Serializable {
    private static final long serialVersionUID = 8652152629648140477L;
    private final List<Ranking> rankingList = new ArrayList<>();

    public void add(final Ranking ranking) {
        rankingList.add(ranking);
    }

    public void remove(final Ranking ranking) {
        rankingList.remove(ranking);
    }

    public List<Ranking> getRankingList() {
        return rankingList;
    }
}
