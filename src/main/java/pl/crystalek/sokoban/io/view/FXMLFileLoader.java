package pl.crystalek.sokoban.io.view;

import javafx.fxml.FXMLLoader;
import pl.crystalek.sokoban.io.file.FileManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public final class FXMLFileLoader {

    public List<FXMLLoader> getFXMLList(final FileManager fileManager) throws IOException {
        final List<FXMLLoader> resultMap = new ArrayList<>();

        for (final InputStream inputStream : fileManager.getFXMLFileList()) {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.load(inputStream);
            resultMap.add(fxmlLoader);
            inputStream.close();
        }
        return resultMap;
    }
}
