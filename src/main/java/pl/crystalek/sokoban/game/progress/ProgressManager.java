package pl.crystalek.sokoban.game.progress;

import java.util.ArrayList;
import java.util.List;

public final class ProgressManager {
    private final List<Progress> saveList = new ArrayList<>();

    public void addSave(final Progress progress) {
        saveList.add(progress);
    }

    public void deleteSave(final Progress progress) {
        saveList.remove(progress);
    }

    public List<Progress> getSaveList() {
        return saveList;
    }
}
