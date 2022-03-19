package pl.crystalek.sokoban.io.view;

import javafx.scene.image.Image;
import pl.crystalek.sokoban.io.file.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class ImageLoader {

    public Map<String, Image> getImageList(final FileManager fileManager) throws IOException {
        final Map<String, Image> resultMap = new HashMap<>();

        for (final Map.Entry<String, InputStream> entry : fileManager.getImageFileList().entrySet()) {
            final InputStream inputStream = entry.getValue();
            final Image picture = new Image(inputStream);

            resultMap.put(entry.getKey(), picture);

            inputStream.close();
        }

        return resultMap;
    }
}
