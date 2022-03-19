package pl.crystalek.sokoban.editor.listener;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pl.crystalek.sokoban.editor.model.BoxLocation;

import java.util.Optional;

final class BoxMouseMoveListener implements EventHandler<MouseEvent> {
    private final BoxListenerManager boxListenerManager;

    BoxMouseMoveListener(final BoxListenerManager boxListenerManager) {
        this.boxListenerManager = boxListenerManager;
    }

    @Override
    public void handle(final MouseEvent event) {
        final ImageView movedBoxImageView = boxListenerManager.getMovedImageView();
        if (movedBoxImageView == null) { //sprawdzanie czy jest wybrany jakiś obrazek
            return;
        }

        if (event.getSceneX() < 160 || event.getSceneX() > 1440 || event.getSceneY() > 800 || event.getSceneY() < 20) {
            boxListenerManager.getEventUtil().goOutMapBorder();
            return;
        }

        //przenoszenie bloku
        movedBoxImageView.setTranslateX(event.getSceneX() - boxListenerManager.getOrgSceneX());
        movedBoxImageView.setTranslateY(event.getSceneY() - boxListenerManager.getOrgSceneY());

        //zwraca najbliższy blok
        final Optional<BoxLocation> closestPaneOptional = boxListenerManager.getEventUtil().getSmallestDistance(event.getSceneX(), event.getSceneY());
        if (!closestPaneOptional.isPresent()) {
            boxListenerManager.getEventUtil().removeLastImage();
            return;
        }

        final Pane closestPane = closestPaneOptional.get().getBoxPane(); //zwraca pane
        final ObservableList<Node> closestPaneChildren = closestPane.getChildren(); //zwraca "dzieci" najblizszego kwadratu, czyli ImageView
        final Image draggedImage = boxListenerManager.getCopyDraggedBlockMap().get(movedBoxImageView.getImage()); //bierze taki sam obraz, ale o innej referencji

        //przywracanie i usuwanie obrazka
        final Pane previousPane = boxListenerManager.getPreviousPane();
        if (previousPane != null) { //sprawdzanie czy poprzedni pane (kwadrat) jest ustawiony
            if (!closestPane.equals(previousPane)) { //jesli oba kwadraty sie roznia
                final ObservableList<Node> children = previousPane.getChildren();
                if (!children.isEmpty()) {
                    final ImageView child = (ImageView) children.get(0);
                    if (boxListenerManager.getPreviousImage() != null) { //sprawdzanie czy wczesniej byl tam jakis obrazek
                        child.setImage(boxListenerManager.getPreviousImage()); //ustawianie poprzedniego obrazka
                    } else {
                        if (child.getImage().equals(draggedImage)) {  //usuwanie poprzedniego obrazka, jest jest obrazkiem przenoszonym
                            child.setImage(null); //kasowanie przenoszonego obrazka z poprzedniego kwadratu
                        }
                    }
                    boxListenerManager.setPreviousImage(null); //usuwanie poprzedniego obrazka z pamieci
                    boxListenerManager.setPreviousPane(null); //usuwanie poprzedniego pane'a (kwadratu) z pamieci
                }
            }
        }

        //tworzenie obiektu imageview, jesli go nie ma
        final ImageView imageViewInClosestPane;
        if (!closestPaneChildren.isEmpty()) {
            imageViewInClosestPane = (ImageView) closestPaneChildren.get(0);
        } else {
            imageViewInClosestPane = new ImageView();
            closestPaneChildren.add(imageViewInClosestPane);
        }

        //nie ustawiaj gumki, jeżeli na najbliższym kwadracie nie ma żadnego bloku
        if (imageViewInClosestPane.getImage() == null && movedBoxImageView.getImage().equals(boxListenerManager.getImageList().get("eraser"))) {
            return;
        }

        final Image imageFromClosestPane = imageViewInClosestPane.getImage(); //obrazek ktory znajduje sie na najblizszym kwadracie
        if (!draggedImage.equals(imageFromClosestPane)) { //sprawdzanie czy na kwadracie znajduje sie przenoszony obrazek, jesli nie, to ma ustawić obrazek ktory jest w kwadracie
            boxListenerManager.setPreviousImage(imageFromClosestPane);
        }

        if (boxListenerManager.getPreviousPane() == null) {
            boxListenerManager.setPreviousPane(closestPane); //ustawianie pane'a (kwadratu), jesli nie jest on ustawiony
        }

        imageViewInClosestPane.setImage(draggedImage); //ustawianie przenoszonego zdjecia
    }
}


