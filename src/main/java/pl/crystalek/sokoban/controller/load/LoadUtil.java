package pl.crystalek.sokoban.controller.load;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.game.progress.ProgressManager;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.DefaultMap;
import pl.crystalek.sokoban.map.UserMap;

import java.time.format.DateTimeFormatter;
import java.util.List;

public final class LoadUtil {
    private final MainLoader mainLoader;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Button clickedButton;
    private boolean userMapSave;
    private DefaultMap chosenObject;

    public LoadUtil(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    public void showProgress() {
        final LoadGameController loadGameController = mainLoader.getController(LoadGameController.class);
        final ObservableList<Node> children = loadGameController.getSaveBox().getChildren();
        children.clear();
        final ProgressManager progressManager = mainLoader.getProgressManager();
        for (final Progress progress : progressManager.getSaveList()) {
            if (progress.isUserMap() != userMapSave) {
                continue;
            }

            final String infoLabelText = Lang.PROGRESS_INFO_LABEL_TEXT
                    .replace("{CREATION_DATE}", progress.getCreationDate().format(dateTimeFormatter)
                            .replace("{MODIFICAION_DATE}", progress.getModificationDate().format(dateTimeFormatter))
                            .replace("{NAME}", progress.getName()));
            final Button button = getButton(progress.getProgressName(), progress, infoLabelText, loadGameController.getSaveInfoLabel(), loadGameController);
            children.add(button);
        }
    }

    public void showMapList(final Load load, final LoadType loadType) {
        final ObservableList<Node> children = load.getMapBox().getChildren();
        children.clear();

        switch (loadType) {
            case USERMAP:
                for (final UserMap userMap : mainLoader.getMapManager().getUserMapList()) {
                    final String infoLabelText = Lang.MAP_INFO_LABEL_TEXT
                            .replace("{CREATION_DATE}", userMap.getCreationDate().format(dateTimeFormatter)
                                    .replace("{MODIFICAION_DATE}", userMap.getModificationDate().format(dateTimeFormatter)));
                    final Button button = getButton(userMap.getName(), userMap, infoLabelText, load.getMapInfoLabel(), load);
                    children.add(button);
                }
                break;
            case DEFAULTMAP:
                final List<DefaultMap> defaultMapList = mainLoader.getMapManager().getDefaultMapList();
                defaultMapList.sort(DefaultMap::compareTo);

                for (final DefaultMap defaultMap : defaultMapList) {
                    final String infoLabelText = Lang.DIFFICULTY.replace("{DIFFICULTY}", String.valueOf(defaultMap.getPriority()));
                    final Button button = getButton(defaultMap.getName(), defaultMap, infoLabelText, load.getMapInfoLabel(), load);
                    children.add(button);
                }
                break;
        }
    }

    private Button getButton(final String ButtonName, final DefaultMap chosenObject, final String infoLabelText, final Label infoLabel, final Load load) {
        final Button button = new Button();
        button.setOnMouseEntered(mouseEvent -> {
            infoLabel.setText(infoLabelText);
            infoLabel.setStyle("-fx-background-color: #a0a0a0");
        });
        button.setOnMouseExited(mouseEvent -> {
            infoLabel.setText("");
            infoLabel.setStyle(null);
        });
        button.setOnAction(event -> {
            this.clickedButton = button;
            this.chosenObject = chosenObject;
            load.getLoadButton().setDisable(false);
            load.getDeleteButton().setDisable(!(chosenObject instanceof UserMap));
        });
        button.setMaxWidth(149);
        button.setMaxHeight(27);
        button.setAlignment(Pos.CENTER);
        button.setTextFill(Paint.valueOf("#a10000"));
        button.setStyle("-fx-background-color: #a0a0a0; -fx-border-color: #aaaaaa");
        button.setFont(new Font("System Bold Italic", 12));
        button.setText(ButtonName);
        return button;
    }

    public Button getClickedButton() {
        return clickedButton;
    }

    public DefaultMap getChosenObject() {
        return chosenObject;
    }

    public void setUserMapSave(final boolean userMapSave) {
        this.userMapSave = userMapSave;
    }
}
