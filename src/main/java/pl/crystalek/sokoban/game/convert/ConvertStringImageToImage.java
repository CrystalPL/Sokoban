package pl.crystalek.sokoban.game.convert;

import javafx.scene.image.Image;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.Map;

public final class ConvertStringImageToImage {
    private final Map<String, Image> imageList;

    public ConvertStringImageToImage(final MainLoader mainLoader) {
        this.imageList = new HashMap<>(mainLoader.getImageList());
        imageList.remove("info");
        imageList.remove("error");
        imageList.remove("warning");
        imageList.remove("crate");
        imageList.remove("bricks");
        imageList.remove("player");
        imageList.remove("eraser");
    }

    public Image[][] convertImageToStringImage(final String[][] stringImageList) {
        if (stringImageList.length == 0) {
            return new Image[][]{};
        }
        final Image[][] deletedImageList = new Image[stringImageList.length][stringImageList[0].length];

        for (int i = 0; i < stringImageList.length; i++) {
            for (int j = 0; j < stringImageList[i].length; j++) {
                final String stringImage = stringImageList[i][j];
                if (stringImage == null) {
                    continue;
                }

                switch (stringImage) {
                    case "grass":
                        deletedImageList[i][j] = imageList.get("grass");
                        break;
                    case "bomb":
                        deletedImageList[i][j] = imageList.get("bomb");
                        break;
                    case "tree":
                        deletedImageList[i][j] = imageList.get("tree");
                        break;
                }
            }
        }

        return deletedImageList;
    }
}
