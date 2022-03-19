package pl.crystalek.sokoban.io.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.crystalek.sokoban.io.MainLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PaneLoader {

    public Map<Class<?>, Pane> getPaneList(final List<FXMLLoader> fxmlList, final MainLoader mainLoader) {
        final Map<Class<?>, Pane> resultMap = new HashMap<>();

        final ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(mainLoader.getSettings().getBrightness());
        final ButtonPressedSoundListener buttonPressedSoundListener = new ButtonPressedSoundListener(mainLoader);

        for (final FXMLLoader fxmlLoader : fxmlList) {
            final Pane root = fxmlLoader.getRoot();
            final ObservableList<Node> children = root.getChildren();
            root.setEffect(colorAdjust);

            for (final Node child : children) {
                if (child instanceof Button) {
                    final Button button = (Button) child;
                    button.addEventFilter(MouseEvent.MOUSE_PRESSED, buttonPressedSoundListener);
                }
            }

            resultMap.put(fxmlLoader.getController().getClass(), root);
        }

        return resultMap;
    }
}
