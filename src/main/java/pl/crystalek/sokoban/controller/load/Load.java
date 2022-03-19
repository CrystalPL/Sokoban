package pl.crystalek.sokoban.controller.load;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public interface Load {
    Button getLoadButton();

    Button getDeleteButton();

    VBox getMapBox();

    LoadUtil getLoadUtil();

    Label getMapInfoLabel();
}
