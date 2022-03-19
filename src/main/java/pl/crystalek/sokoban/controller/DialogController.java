package pl.crystalek.sokoban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import pl.crystalek.sokoban.io.MainLoader;

public final class DialogController implements Controller {
    private MainLoader mainLoader;
    @FXML
    private Label headerTextLabel;
    @FXML
    private Label contentTextLabel;
    @FXML
    private ImageView infoImageView;

    @Override
    public void setManagers(final MainLoader mainLoader) {
        this.mainLoader = mainLoader;
    }

    @FXML
    private void ok(final ActionEvent event) {
        mainLoader.getViewLoader().getStage(getClass()).close();
        headerTextLabel.setText("");
        contentTextLabel.setText("");
        infoImageView.setImage(null);
    }

    public void showDialogWindow(final String imageName, final String title, final String subtitle) {
        infoImageView.setImage(mainLoader.getImageList().get(imageName));
        headerTextLabel.setText(title);
        contentTextLabel.setText(subtitle);
        mainLoader.getViewLoader().getStage(getClass()).show();
    }
}
