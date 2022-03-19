package pl.crystalek.sokoban.editor.listener;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pl.crystalek.sokoban.editor.model.BoxLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class EventUtil {
    private final BoxListenerManager boxListenerManager;

    EventUtil(final BoxListenerManager boxListenerManager) {
        this.boxListenerManager = boxListenerManager;
    }

    Optional<BoxLocation> getSmallestDistance(final double blockX, final double blockY) {
        final List<BoxLocation> boxList = boxListenerManager.getMapEditor().getBoxLocationList();
        double smallestDistance = Double.MAX_VALUE;
        BoxLocation nearestLocation = null;

        for (final BoxLocation location : boxList) {
            final double distance = Math.sqrt(Math.pow(blockX - location.getX(), 2) + Math.pow(blockY - location.getY(), 2));
            if (distance < smallestDistance && distance <= 45) {
                smallestDistance = distance;
                nearestLocation = location;
            }
        }
        return Optional.ofNullable(nearestLocation);
    }

    Map<Image, Image> getCopyImageList(final Map<String, Image> imageList) {
        final Map<Image, Image> copyImageList = new HashMap<>();
        for (final Image image : imageList.values()) {
            final int height = (int) image.getHeight();
            final int width = (int) image.getWidth();
            final PixelReader pixelReader = image.getPixelReader();
            final WritableImage writableImage = new WritableImage(width, height);
            final PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    final Color color = pixelReader.getColor(x, y);
                    pixelWriter.setColor(x, y, color);
                }
            }

            copyImageList.put(image, writableImage);
        }
        return copyImageList;
    }

    void removeLastImage() {
        final Pane previousPane = boxListenerManager.getPreviousPane();
        if (previousPane == null) {
            return;
        }

        final ObservableList<Node> previousPaneChildren = previousPane.getChildren();
        if (previousPaneChildren.size() == 0) {
            return;
        }

        final ImageView imageView = (ImageView) previousPaneChildren.get(0);
        if (!boxListenerManager.getCopyDraggedBlockMap().containsValue(imageView.getImage())) {
            return;
        }

        final Image previousImage = boxListenerManager.getPreviousImage();
        if (previousImage != null) {
            imageView.setImage(previousImage);
            return;
        }

        previousPaneChildren.clear();
        boxListenerManager.setPreviousPane(null);
    }

    void goOutMapBorder() {
        final ImageView movedBoxImageView = boxListenerManager.getMovedImageView();
        movedBoxImageView.setTranslateX(0);
        movedBoxImageView.setTranslateY(0);
        removeLastImage();
        boxListenerManager.resetVariable();
    }
}
