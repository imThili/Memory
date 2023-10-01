package src.main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KittenMemory extends Application {

    private final Logger LOG = Logger.getLogger(KittenMemory.class.getName());
    private final ClickHandler clickHandler = new ClickHandler();

    private final Label triesLabel = new Label();
    private final Label foundLabel = new Label();
    private final Label resultLabel = new Label();
    private final Button okButton = new Button("ok");

    private ImageTile firstTile = null, secondTile = null;
    private Pane center;
    private int tries, found, pairs;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        center = new Pane();
        root.setCenter(center);

        Image image = new Image("https://c4.wallpaperflare.com/wallpaper/581/460/967/nyan-cat-wallpaper-preview.jpg", 0, 0, false, true);
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);

        FlowPane top = new FlowPane();
        top.setAlignment(Pos.CENTER_LEFT);
        top.setHgap(10);

        top.getChildren().add(triesLabel);
        top.getChildren().add(foundLabel);
        root.setTop(top);

        FlowPane bottom = new FlowPane();
        bottom.setPrefSize(20, 20);
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setHgap(10);
        bottom.getChildren().add(resultLabel);
        bottom.getChildren().add(okButton);
        okButton.setVisible(false);
        okButton.setOnAction(new FlipHandler());
        root.setBottom(bottom);

        setUpMemory();

        Scene scene = new Scene(root);
        stage.setTitle("Kitten Memory");
        stage.setScene(scene);
        stage.show();

        imageView.fitWidthProperty().bind(center.widthProperty());
        imageView.fitHeightProperty().bind(center.heightProperty());
    }

    public void setUpMemory() {

        String[] imageNames = new String[]{
                "https://preview.redd.it/y67pg0npyuq61.jpg?width=640&crop=smart&auto=webp&s=6c23a382270e7ab1708be0032fce480e495f04d8",
                "https://preview.redd.it/vagfg610bok61.jpg?width=640&crop=smart&auto=webp&s=452883f03258ad551f1591011a4f510e3a7d6b90",
                "https://preview.redd.it/worxz7hz48i61.jpg?width=960&crop=smart&auto=webp&s=9ac9d14f81ef42975f32fb092f1f16990baf1f86",
                "https://i.redd.it/dt44elu7cfnb1.jpg",
                "https://i.redd.it/y4kgdt08ubka1.jpg",

                "https://preview.redd.it/y67pg0npyuq61.jpg?width=640&crop=smart&auto=webp&s=6c23a382270e7ab1708be0032fce480e495f04d8",
                "https://preview.redd.it/vagfg610bok61.jpg?width=640&crop=smart&auto=webp&s=452883f03258ad551f1591011a4f510e3a7d6b90",
                "https://preview.redd.it/worxz7hz48i61.jpg?width=960&crop=smart&auto=webp&s=9ac9d14f81ef42975f32fb092f1f16990baf1f86",
                "https://i.redd.it/dt44elu7cfnb1.jpg",
                "https://i.redd.it/y4kgdt08ubka1.jpg",
        };

        pairs = imageNames.length / 2;

        Integer[][] coordinates = new Integer[][]{
                {5, 5},     //0
                {15, 450},  //1
                {30, 200},  //2
                {200, 50},  //3
                {325, 250}, //4
                {375, 400}, //5
                {550, 400}, //6
                {450, 5},   //7
                {475, 150}, //8
                {175, 300}  //9
        };

        ArrayList<ImageTile> imageTiles = new ArrayList<>();
        ArrayList<String> shuffledImages = new ArrayList<>(Arrays.asList(imageNames));
        Collections.shuffle(shuffledImages);
        imageNames = shuffledImages.toArray(new String[0]);

        for (int i = 0; i < imageNames.length; i++) {
            imageTiles.add(new ImageTile(coordinates[i][0], coordinates[i][1], imageNames[i]));
        }

        for (ImageTile tile : imageTiles) {
            center.getChildren().add(tile);
            tile.setOnMouseClicked(clickHandler);
        }
        tries = 0;
        found = 0;
        setTriesLabelText();
        setFoundLabelText();
    }

    private void setFoundLabelText() {
        foundLabel.setText("Found: " + found + "/" + pairs + " pairs");
    }

    private void setTriesLabelText() {
        triesLabel.setText("Tries: " + tries);
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            ImageTile tile = (ImageTile) event.getSource();

            if (tile == firstTile) {
                return;
            }

            LOG.log(Level.INFO, "coordinates: " + tile.getX() + ", " + tile.getY());

            if (firstTile == null) {

                firstTile = tile;
                firstTile.setCovered(false);

            } else if (secondTile == null) {
                secondTile = tile;
                secondTile.setCovered(false);
                okButton.setVisible(true);

                if (firstTile.isMatch(secondTile)) {
                    String match = "You found a pair!";
                    resultLabel.setText(match);
                    found++;
                } else {
                    String notMatch = "Try again!";
                    resultLabel.setText(notMatch);
                }
            }
        }
    }

    class FlipHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (firstTile != null && secondTile != null) {
                if (firstTile.isMatch(secondTile)) {
                    center.getChildren().remove(firstTile);
                    center.getChildren().remove(secondTile);
                } else {
                    firstTile.setCovered(true);
                    secondTile.setCovered(true);
                }
                tries++;
                triesLabel.setText("Tries: " + tries);
                foundLabel.setText("Found: " + found + "/" + pairs + " pairs");
                firstTile = secondTile = null;
                okButton.setVisible(false);
                resultLabel.setText("");
            }

            if (found == pairs) {
                showPlayAgainDialog();
            }
        }

        private void showPlayAgainDialog() {
            final Dialog<String> playAgainDialog = new Dialog<>();
            playAgainDialog.setTitle("Play Again");
            playAgainDialog.setContentText("You won!");
            playAgainDialog.getDialogPane().getButtonTypes().add(new ButtonType("Play again", ButtonBar.ButtonData.OK_DONE));
            playAgainDialog.getDialogPane().getButtonTypes().add(new ButtonType("Exit game", ButtonBar.ButtonData.CANCEL_CLOSE));
            playAgainDialog.setResultConverter(ButtonType::getText);
            String result = playAgainDialog.showAndWait().orElse(null);
            if (Objects.equals(result, "Play again")) {
                setUpMemory();
            } else {
                Platform.exit();
            }
        }
    }
}