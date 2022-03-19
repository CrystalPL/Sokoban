package pl.crystalek.sokoban.editor.convert;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.crystalek.sokoban.editor.model.BoxLocation;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ConvertGridPaneToString {
    private final Map<String, Image> imageList;

    public ConvertGridPaneToString(final MainLoader mainLoader) {
        this.imageList = new HashMap<>(mainLoader.getImageList());
        imageList.remove("error");
        imageList.remove("info");
        imageList.remove("warning");
        imageList.remove("eraser");
    }

    public List<String> convertGridPaneToString(final Object object) {
        final List<String> resultStringList = new LinkedList<>();

        for (int i = 0; i < 20; i++) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < 30; j++) {
                final ImageView imageView;
                if (object instanceof List) {
                    final List<BoxLocation> boxLocationList = (List<BoxLocation>) object;
                    final BoxLocation boxLocation = boxLocationList.get(i * 30 + j);
                    final ObservableList<Node> boxPaneChildren = boxLocation.getBoxPane().getChildren();
                    if (boxPaneChildren.isEmpty()) {
                        stringBuilder.append(" ");
                        continue;
                    }
                    imageView = (ImageView) boxPaneChildren.get(0);
                } else {
                    final ImageView[][] boxLocationList = (ImageView[][]) object;
                    imageView = boxLocationList[j][i];
                }

                final Image image = imageView.getImage();
                if (image == null) {
                    stringBuilder.append(" ");
                    continue;
                }

                if (image.equals(imageList.get("grass"))) {
                    stringBuilder.append("&");
                } else if (image.equals(imageList.get("bricks"))) {
                    stringBuilder.append("#");
                } else if (image.equals(imageList.get("crate"))) {
                    stringBuilder.append("$");
                } else if (image.equals(imageList.get("bomb"))) {
                    stringBuilder.append(".");
                } else if (image.equals(imageList.get("player"))) {
                    stringBuilder.append("@");
                } else if (image.equals(imageList.get("tree"))) {
                    stringBuilder.append("*");
                }
            }
            resultStringList.add(stringBuilder.toString());
        }
        return resultStringList;
    }
}
