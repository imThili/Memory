package src.main.java;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KittenMemory extends Application {
    private final Logger LOG = LoggerFactory.getLogger(KittenMemory.class);

    private ImageTile firstTile = null, secondTile = null;
    private Label triesLabel = new Label("0");
    private Label hitsLabel = new Label("0");

    private Pane center;
    private int tries = 0, hits = 0;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        center = new Pane();
        root.setCenter(center);

        Image image = new Image("https://c4.wallpaperflare.com/wallpaper/581/460/967/nyan-cat-wallpaper-preview.jpg", 800, 700, false, true);
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);

        ClickHandler clickHandler = new ClickHandler();
        ImageTile[] tiles = {

                new ImageTile(12, 400, "https://preview.redd.it/y67pg0npyuq61.jpg?width=640&crop=smart&auto=webp&s=6c23a382270e7ab1708be0032fce480e495f04d8"),
                new ImageTile(300, 17, "https://preview.redd.it/y67pg0npyuq61.jpg?width=640&crop=smart&auto=webp&s=6c23a382270e7ab1708be0032fce480e495f04d8"),
                new ImageTile(40, 207, "https://preview.redd.it/vagfg610bok61.jpg?width=640&crop=smart&auto=webp&s=452883f03258ad551f1591011a4f510e3a7d6b90"),
                new ImageTile(589, 40,"https://preview.redd.it/vagfg610bok61.jpg?width=640&crop=smart&auto=webp&s=452883f03258ad551f1591011a4f510e3a7d6b90"),
                new ImageTile(112, 600,"https://preview.redd.it/l7btbwlgmdl61.jpg?width=640&crop=smart&auto=webp&s=cdd8263eb0c7b1e4e67b10fa31e3191894c81719"),
                new ImageTile(318, 145,"https://preview.redd.it/l7btbwlgmdl61.jpg?width=640&crop=smart&auto=webp&s=cdd8263eb0c7b1e4e67b10fa31e3191894c81719"),
                new ImageTile(500, 305, "https://preview.redd.it/worxz7hz48i61.jpg?width=960&crop=smart&auto=webp&s=9ac9d14f81ef42975f32fb092f1f16990baf1f86"),
                new ImageTile(219, 290,"https://preview.redd.it/worxz7hz48i61.jpg?width=960&crop=smart&auto=webp&s=9ac9d14f81ef42975f32fb092f1f16990baf1f86"),
        };
        for(ImageTile tile : tiles){
            center.getChildren().add(tile);
            tile.setOnMouseClicked(clickHandler);
        }

        FlowPane bottom = new FlowPane();
        bottom.setAlignment(Pos.CENTER);
        bottom.setVgap(5);
        bottom.getChildren().add(new Label("Antal försök="));
        bottom.getChildren().add(triesLabel);
        bottom.getChildren().add(new Label("Antal träffar="));
        bottom.getChildren().add(hitsLabel);
        Button testaButton = new Button("Testa");
        bottom.getChildren().add(testaButton);
        root.setBottom(bottom);
        testaButton.setOnAction(new TestaHandler());

        Scene scene = new Scene(root);
        stage.setTitle("Kitten Memory");
        stage.setScene(scene);
        stage.show();

        imageView.fitWidthProperty().bind(center.widthProperty());
        imageView.fitHeightProperty().bind(center.heightProperty());
    }

    //TODO: kom på ett sätt att randomiza vilka bilder som hamnar på vilken plats.
    private ImageTile [] getTiles(String [] imageSources){
        return null;
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            ImageTile tile = (ImageTile)event.getSource();
            if(firstTile == null) {
                firstTile = tile;
                firstTile.setCovered(false);
            }
            else if(secondTile == null && tile != firstTile) {
                secondTile = tile;
                secondTile.setCovered(false);
            }
        }
    }
    class TestaHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (firstTile != null && secondTile != null) {
                if (firstTile.isMatch(secondTile)) {
                    center.getChildren().remove(firstTile);
                    center.getChildren().remove(secondTile);
                    hits++;
                    hitsLabel.setText(""+hits);
                }
                else {
                    firstTile.setCovered(true);
                    secondTile.setCovered(true);
                }
                tries++;
                triesLabel.setText(""+tries);
                firstTile = secondTile = null;
            }
        }
    }
}