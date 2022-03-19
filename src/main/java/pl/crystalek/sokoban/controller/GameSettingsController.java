package pl.crystalek.sokoban.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import pl.crystalek.sokoban.exception.SaveUserFileException;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.io.file.FileManager;
import pl.crystalek.sokoban.lang.Lang;
import pl.crystalek.sokoban.settings.Control;
import pl.crystalek.sokoban.settings.Settings;
import pl.crystalek.sokoban.settings.Sound;

import java.net.URL;
import java.util.ResourceBundle;

public final class GameSettingsController implements Controller, Initializable {
    private MainLoader mainLoader;
    @FXML
    private Button soundButton;
    @FXML
    private Button controlButton;
    @FXML
    private Slider brightnessSlider;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final ColorAdjust colorAdjust = new ColorAdjust();
        brightnessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue() / 100;
            value = value < 1 ? (1 - value) * -1 : value - 1;

            colorAdjust.setBrightness(value);
            mainLoader.getSettings().setBrightness(value);

            for (final Pane pane : mainLoader.getViewLoader().getPaneMap().values()) {
                pane.setEffect(colorAdjust);
            }
        });
    }

    @FXML
    private void back(final ActionEvent event) {
        final FileManager fileManager = mainLoader.getFileManager();
        try {
            fileManager.getFileSaver().saveFile(fileManager.getSettingsFile(), mainLoader.getSettings());
        } catch (final SaveUserFileException exception) {
            mainLoader.getController(DialogController.class).showDialogWindow("error", Lang.TITLE_ERROR, exception.getMessage());
            exception.printStackTrace();
        }
        mainLoader.getViewLoader().setWindow(SokobanMainController.class);
    }

    @FXML
    private void control(final ActionEvent event) {
        final Settings settings = mainLoader.getSettings();
        settings.setControlType(settings.getControlType() == Control.WSAD ? Control.ARROWS : Control.WSAD);
        controlButton.setText(settings.getControlType().getName());
    }

    @FXML
    private void soundButton(final ActionEvent event) {
        final Settings settings = mainLoader.getSettings();
        settings.setSound(settings.getSound() == Sound.ENABLE ? Sound.DISABLE : Sound.ENABLE);
        soundButton.setText(settings.getSound().getName());
    }

    void updateSettings() {
        final Settings settings = mainLoader.getSettings();
        controlButton.setText(settings.getControlType().getName());
        soundButton.setText(settings.getSound().getName());
        final double value = settings.getBrightness() * 100;
        brightnessSlider.setValue(value < 0 ? 100 - Math.abs(value) : value + 100);
    }
}
