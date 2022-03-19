package pl.crystalek.sokoban.game.convert;

import javafx.scene.image.Image;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.Map;

public final class ConvertImageToStringImage {
    private final Map<String, Image> imageList;

    public ConvertImageToStringImage(final MainLoader mainLoader) {
        this.imageList = new HashMap<>(mainLoader.getImageList());
        imageList.remove("info");
        imageList.remove("error");
        imageList.remove("warning");
        imageList.remove("crate");
        imageList.remove("bricks");
        imageList.remove("player");
        imageList.remove("eraser");
    }

    public String[][] convertImageToStringImage(final Image[][] deletedImageList) {
        final String[][] stringImageList = new String[deletedImageList.length][deletedImageList[0].length];

        for (int i = 0; i < deletedImageList.length; i++) {
            for (int j = 0; j < deletedImageList[i].length; j++) {
                final Image image = deletedImageList[i][j];
                if (image == null) {
                    continue;
                }

                if (image.equals(imageList.get("grass"))) {
                    stringImageList[i][j] = "grass";
                } else if (image.equals(imageList.get("bomb"))) {
                    stringImageList[i][j] = "bomb";
                } else if (image.equals(imageList.get("tree"))) {
                    stringImageList[i][j] = "tree";
                }
            }
        }

        return stringImageList;
    }
}
