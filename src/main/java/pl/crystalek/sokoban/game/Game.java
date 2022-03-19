package pl.crystalek.sokoban.game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.crystalek.sokoban.editor.convert.ConvertGridPaneToString;
import pl.crystalek.sokoban.game.convert.ConvertImageToStringImage;
import pl.crystalek.sokoban.game.convert.ConvertStringImageToImage;
import pl.crystalek.sokoban.game.count.TimeCounter;
import pl.crystalek.sokoban.game.listener.PlayerMoveListener;
import pl.crystalek.sokoban.game.listener.ResetMapListener;
import pl.crystalek.sokoban.game.progress.Progress;
import pl.crystalek.sokoban.io.MainLoader;
import pl.crystalek.sokoban.lang.Lang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Game {
    private final MainLoader mainLoader;
    private final ImageView[][] boxLocationList = new ImageView[30][20];
    private final ConvertStringImageToImage convertStringImageToImage;
    private final ConvertImageToStringImage convertImageToStringImage;
    private final ConvertGridPaneToString convertGridPaneToString;
    private final PlayerMoveListener playerMoveListener;
    private final ResetMapListener resetMapListener = new ResetMapListener(this);
    private final Progress oldProgress;
    private final GridPane mapBox;
    private Map<String, Image> imageList;
    private Player player;
    private Progress progress;
    private Image[][] deleteImageList;
    private TimeCounter timeCounter;
    private int crateCount;

    public Game(final MainLoader mainLoader, final Progress oldProgress, final GridPane mapBox) {
        this.mainLoader = mainLoader;
        this.convertStringImageToImage = new ConvertStringImageToImage(mainLoader);
        this.convertImageToStringImage = new ConvertImageToStringImage(mainLoader);
        this.convertGridPaneToString = new ConvertGridPaneToString(mainLoader);
        this.oldProgress = oldProgress;
        this.mapBox = mapBox;
        this.playerMoveListener = new PlayerMoveListener(this, mainLoader);
        final Stage mainStage = mainLoader.getViewLoader().getMainStage();
        mainStage.addEventFilter(KeyEvent.KEY_PRESSED, playerMoveListener);
        mainStage.addEventFilter(KeyEvent.KEY_RELEASED, resetMapListener);
    }

    public String loadGame(final List<String> mapLines, final Progress progress) {
        final Map<String, Image> imageList = new HashMap<>(mainLoader.getImageList());
        imageList.remove("info");
        imageList.remove("error");
        imageList.remove("warning");
        this.imageList = imageList;
        int playerCount = 0;
        int bombCount = 0;
        int crateCount = 0;
        mapBox.getChildren().clear();

        for (int rowNumber = 0; rowNumber < mapLines.size(); rowNumber++) {
            final String mapLine = mapLines.get(rowNumber);
            for (int columnNumber = 0; columnNumber < mapLine.length(); columnNumber++) {
                final ImageView imageView = new ImageView();
                imageView.setFitHeight(39);
                imageView.setFitWidth(39);

                switch (mapLine.charAt(columnNumber)) {
                    case '#':
                        imageView.setImage(imageList.get("bricks"));
                        break;
                    case '.':
                        imageView.setImage(imageList.get("bomb"));
                        bombCount++;
                        break;
                    case '@':
                        this.player = new Player(columnNumber, rowNumber);
                        imageView.setImage(imageList.get("player"));
                        playerCount++;
                        break;
                    case '$':
                        imageView.setImage(imageList.get("crate"));
                        crateCount++;
                        break;
                    case '&':
                        imageView.setImage(imageList.get("grass"));
                        break;
                    case '*':
                        imageView.setImage(imageList.get("tree"));
                        break;
                }
                boxLocationList[columnNumber][rowNumber] = imageView;
                mapBox.add(imageView, columnNumber, rowNumber);
            }
        }

        if (playerCount != 1) {
            return Lang.MAX_PLAYER_ON_MAP;
        }

        if (progress.getProgressName() == null) {
            if (bombCount != crateCount || bombCount == 0) {
                return Lang.ITEM_ERROR_COUNT;
            }
        }

        this.progress = progress;
        this.deleteImageList = convertStringImageToImage.convertImageToStringImage(progress.getStringEditedBlocks());
        final TimeCounter timeCounter = new TimeCounter(mainLoader);
        this.timeCounter = timeCounter;
        timeCounter.start();
        this.crateCount = crateCount;
        return "";
    }

    public ImageView[][] getBoxLocationList() {
        return boxLocationList;
    }

    public Player getPlayer() {
        return player;
    }

    public Progress getProgress() {
        return progress;
    }

    public ConvertImageToStringImage getConvertImageToStringImage() {
        return convertImageToStringImage;
    }

    public Map<String, Image> getImageList() {
        return imageList;
    }

    public Image[][] getDeleteImageList() {
        return deleteImageList;
    }

    public ConvertGridPaneToString getConvertGridPaneToString() {
        return convertGridPaneToString;
    }

    public PlayerMoveListener getPlayerMoveListener() {
        return playerMoveListener;
    }

    public TimeCounter getTimeCounter() {
        return timeCounter;
    }

    public int getCrateCount() {
        return crateCount;
    }

    public Progress getOldProgress() {
        return oldProgress;
    }

    public ResetMapListener getResetMapListener() {
        return resetMapListener;
    }

    public GridPane getMapBox() {
        return mapBox;
    }
}
