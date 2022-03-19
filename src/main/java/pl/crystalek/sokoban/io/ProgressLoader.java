package pl.crystalek.sokoban.io;

import pl.crystalek.sokoban.exception.LoadUserFileException;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.game.progress.ProgressManager;
import pl.crystalek.sokoban.lang.Lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public final class ProgressLoader {

    ProgressManager getGameProgress(final Map<String, File> gameProgressFileList) throws LoadUserFileException {
        final ProgressManager progressManager = new ProgressManager();

        for (final Map.Entry<String, File> entry : gameProgressFileList.entrySet()) {
            try (
                    final FileInputStream fileInputStream = new FileInputStream(entry.getValue());
                    final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
            ) {
                final Progress progress = (Progress) objectInputStream.readObject();
                progressManager.addSave(progress);

            } catch (final IOException | ClassNotFoundException exception) {
                throw new LoadUserFileException(Lang.ERROR_WHILE_LOADING_PROGRESS_FILE.replace("{PROGRESS_NAME}", entry.getKey()), exception);
            }
        }
        return progressManager;
    }
}
