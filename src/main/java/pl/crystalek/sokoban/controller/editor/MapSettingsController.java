package pl.crystalek.sokoban.controller.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import pl.crystalek.sokoban.controller.Controller;
import pl.crystalek.sokoban.controller.DialogController;
import pl.crystalek.sokoban.editor.MapEditor;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.map.UserMap;

import java.time.LocalDateTime;

public final class MapSettingsController implements Controller {
    private MainLoader mainLoader;
    private boolean openFromSaveButton;

    @FXML
    private TextField playTimeTextField;

    @FXML
    private TextField bonusForTimeTextField;

    @FXML
    private TextField mapNameTextField;

    @FXML
    private CheckBox closeGameCheckBox;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void back(final ActionEvent event) {
        playTimeTextField.setText("");
        bonusForTimeTextField.setText("");
        mapNameTextField.setText("");
        closeGameCheckBox.setSelected(false);
        mainLoader.getViewLoader().setWindow(MapEditorController.class);
    }

    @FXML
    private void saveSettings(final ActionEvent event) {
        final String playTimeText = playTimeTextField.getText().trim();
        final String bonusForTimeText = bonusForTimeTextField.getText().trim();
        final String mapNameText = mapNameTextField.getText().trim();
        final MapEditorController mapEditorController = mainLoader.getController(MapEditorController.class);
        final MapEditor mapEditor = mapEditorController.getMapEditor();
        final UserMap editedMap = mapEditor.getEditedMap();

        if (playTimeText.isEmpty() || bonusForTimeText.isEmpty() || mapNameText.isEmpty()) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, Lang.NOT_EMPTY_FIELD);
            return;
        }

        if (!mapNameText.equals(editedMap.getName())) {
            if (mainLoader.getMapManager().getUserMapList().stream().anyMatch(x -> x.getName().equalsIgnoreCase(mapNameText))) {
                mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, Lang.MAP_NAME_EXIST);
                return;
            }
        }

        final int playTimeNumber;
        final int bonusForTimeNumber;
        try {
            playTimeNumber = Integer.parseInt(playTimeText);
            bonusForTimeNumber = Integer.parseInt(bonusForTimeText);
            if (playTimeNumber < 0 || bonusForTimeNumber < 0) {
                throw new NumberFormatException();
            }
        } catch (final NumberFormatException exception) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, Lang.RANGE_FIELD_ERROR);
            return;
        }

        editedMap.setTimeInSeconds(playTimeNumber);
        editedMap.setBonus(bonusForTimeNumber);
        if (editedMap.getOldName() == null) {
            editedMap.setOldName(editedMap.getName());
        }
        editedMap.setName(mapNameText);
        editedMap.setCloseGameWhenTimeEnd(closeGameCheckBox.isSelected());
        editedMap.setModificationDate(LocalDateTime.now());
        editedMap.setChangesToSave(true);

        String dialogSubtitle = Lang.SAVE_MAP_SETTINGS;
        if (openFromSaveButton) {
            mapEditorController.getSaveMap().saveMap(mapEditor);
            openFromSaveButton = false;
            dialogSubtitle = Lang.SAVE_MAP_AND_SETTINGS;
        }
        mainLoader.getController(DialogController.class).showDialogWindow("info", Lang.TITLE_INFO, dialogSubtitle);
    }

    public TextField getPlayTimeTextField() {
        return playTimeTextField;
    }

    public TextField getBonusForTimeTextField() {
        return bonusForTimeTextField;
    }

    public TextField getMapNameTextField() {
        return mapNameTextField;
    }

    public CheckBox getCloseGameCheckBox() {
        return closeGameCheckBox;
    }

    public void setOpenFromSaveButton(final boolean openFromSaveButton) {
        this.openFromSaveButton = openFromSaveButton;
    }
}
