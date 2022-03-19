package pl.crystalek.sokoban.editor.listener;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pl.crystalek.sokoban.editor.model.BoxLocation;
import pl.crystalek.sokoban.map.UserMap;

import java.time.LocalDateTime;
import java.util.Optional;

final class BoxPlaceMousePressedListener implements EventHandler<MouseEvent> {
    private final BoxListenerManager boxListenerManager;

    BoxPlaceMousePressedListener(final BoxListenerManager boxListenerManager) {
        this.boxListenerManager = boxListenerManager;
    }

    @Override
    public void handle(final MouseEvent event) {
        if (boxListenerManager.getMovedImageView() == null) {
            return;
        }

        if (boxListenerManager.getMovedImageView().getImage() == null) {
            return;
        }

        if (event.isSecondaryButtonDown()) {
            boxListenerManager.getEventUtil().goOutMapBorder();
            return;
        }

        final Optional<BoxLocation> closestPaneOptional = boxListenerManager.getEventUtil().getSmallestDistance(event.getSceneX(), event.getSceneY());
        if (!closestPaneOptional.isPresent()) {
            return;
        }

        final ObservableList<Node> closestPaneChildren = closestPaneOptional.get().getBoxPane().getChildren();
        if (closestPaneChildren.size() == 0) {
            return;
        }

        final ImageView imageView = (ImageView) closestPaneChildren.get(0);
        Image movedImage = boxListenerManager.getMovedImageView().getImage();
        if (movedImage.equals(boxListenerManager.getImageList().get("eraser"))) {
            boxListenerManager.setPreviousPane(null);
            movedImage = null;
        }

        imageView.setImage(movedImage);
        final UserMap editedMap = boxListenerManager.getMapEditor().getEditedMap();
        editedMap.setChangesToSave(true);
        editedMap.setModificationDate(LocalDateTime.now());
    }
}
