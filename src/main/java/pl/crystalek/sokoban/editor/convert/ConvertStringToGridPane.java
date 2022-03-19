package pl.crystalek.sokoban.editor.convert;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.editor.model.BoxLocation;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConvertStringToGridPane {
    private final MapEditor mapEditor;
    private final Map<String, Image> imageList;

    public ConvertStringToGridPane(final MainLoader mainLoader, final MapEditor mapEditor) {
        this.mapEditor = mapEditor;
        this.imageList = new HashMap<>(mainLoader.getImageList());
        imageList.remove("error");
        imageList.remove("info");
        imageList.remove("warning");
        imageList.remove("eraser");
    }

    public void stringToGridPane(final List<String> mapLines) {
        final List<BoxLocation> boxLocationList = mapEditor.getBoxLocationList();

        for (int i = 0; i < mapLines.size(); i++) {
            final String line = mapLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                final BoxLocation boxLocation = boxLocationList.get(i * 30 + j);
                final ObservableList<Node> boxPaneChildren = boxLocation.getBoxPane().getChildren();
                final ImageView imageView = new ImageView();

                switch (line.charAt(j)) {
                    case '#':
                        imageView.setImage(imageList.get("bricks"));
                        break;
                    case '.':
                        imageView.setImage(imageList.get("bomb"));
                        break;
                    case '@':
                        imageView.setImage(imageList.get("player"));
                        break;
                    case '$':
                        imageView.setImage(imageList.get("crate"));
                        break;
                    case '&':
                        imageView.setImage(imageList.get("grass"));
                        break;
                    case '*':
                        imageView.setImage(imageList.get("tree"));
                        break;
                    default:
                        continue;
                }
                boxPaneChildren.add(imageView);
            }
        }
    }

}
