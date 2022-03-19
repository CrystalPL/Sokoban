package pl.crystalek.sokoban.editor.listener;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pl.crystalek.sokoban.controller.editor.MapEditorController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.Map;

public final class BoxListenerManager {
    private final MainLoader mainLoader;
    private final EventUtil eventUtil = new EventUtil(this);
    private final MapEditor mapEditor;
    private Map<Image, Image> copyDraggedBlockMap;
    private double orgSceneX;
    private double orgSceneY;
    private ImageView movedImageView;
    private Image previousImage;
    private Pane previousPane;
    private Map<String, Image> imageList;

    public BoxListenerManager(final MainLoader mainLoader, final MapEditor mapEditor) {
        this.mainLoader = mainLoader;
        this.mapEditor = mapEditor;
    }

    public void setListeners() {
        final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
        final AnchorPane editedArea = mapEditorController.getEditedAreaPane();
        final ObservableList<Node> draggedBoxChildren = mapEditorController.getBlockBox().getChildren();
        draggedBoxChildren.clear();

        final Map<String, Image> draggedBlockMap = new HashMap<>(mainLoader.getImageList());
        draggedBlockMap.remove("error");
        draggedBlockMap.remove("info");
        draggedBlockMap.remove("warning");
        this.copyDraggedBlockMap = eventUtil.getCopyImageList(draggedBlockMap);
        this.imageList = draggedBlockMap;

        for (final Image boxImage : draggedBlockMap.values()) {
            final ImageView boxImageView = new ImageView(boxImage);
            boxImageView.setOnMousePressed(new BoxMousePressedListener(this));
            draggedBoxChildren.add(boxImageView);
        }

        editedArea.setOnMousePressed(new BoxPlaceMousePressedListener(this));
        editedArea.setOnMouseMoved(new BoxMouseMoveListener(this));
    }

    public void resetVariable() {
        movedImageView = null;
        previousImage = null;
        previousPane = null;
    }

    double getOrgSceneX() {
        return orgSceneX;
    }

    void setOrgSceneX(final double orgSceneX) {
        this.orgSceneX = orgSceneX;
    }

    double getOrgSceneY() {
        return orgSceneY;
    }

    void setOrgSceneY(final double orgSceneY) {
        this.orgSceneY = orgSceneY;
    }

    ImageView getMovedImageView() {
        return movedImageView;
    }

    void setMovedImageView(final ImageView movedImageView) {
        this.movedImageView = movedImageView;
    }

    MapEditor getMapEditor() {
        return mapEditor;
    }

    EventUtil getEventUtil() {
        return eventUtil;
    }

    Map<Image, Image> getCopyDraggedBlockMap() {
        return copyDraggedBlockMap;
    }

    Image getPreviousImage() {
        return previousImage;
    }

    void setPreviousImage(final Image previousImage) {
        this.previousImage = previousImage;
    }

    Pane getPreviousPane() {
        return previousPane;
    }

    void setPreviousPane(final Pane previousPane) {
        this.previousPane = previousPane;
    }

    Map<String, Image> getImageList() {
        return imageList;
    }
}
