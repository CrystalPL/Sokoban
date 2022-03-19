package pl.crystalek.sokoban.editor.model;

import javafx.scene.layout.Pane;

public final class BoxLocation {
    private final int x;
    private final int y;
    private final Pane boxPane;

    public BoxLocation(final int x, final int y, final Pane boxPane) {
        this.x = x;
        this.y = y;
        this.boxPane = boxPane;
    }

    public Pane getBoxPane() {
        return boxPane;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
