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

public class Memory extends Application {
    private Bricka b1 = null, b2 = null;
    private Label triesLabel = new Label("0");
    private Label hitsLabel = new Label("0");
    private Pane center;
    private int tries = 0, hits = 0;
    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        center = new Pane();
        root.setCenter(center);

        Image image = new Image("https://preview.redd.it/du1p7x7id1x61.jpg?width=960&crop=smart&auto=webp&s=2c0e8e7b5d14dd25720b58a282f197538fede1e3", 800, 700, false, true);
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);

        ClickHandler c1 = new ClickHandler();
        Bricka[] brickor = {

        new BildBricka(12, 400, "https://preview.redd.it/y67pg0npyuq61.jpg?width=640&crop=smart&auto=webp&s=6c23a382270e7ab1708be0032fce480e495f04d8"),
        new BildBricka(300, 17, "https://preview.redd.it/y67pg0npyuq61.jpg?width=640&crop=smart&auto=webp&s=6c23a382270e7ab1708be0032fce480e495f04d8"),
        new BildBricka(40, 207, "https://preview.redd.it/vagfg610bok61.jpg?width=640&crop=smart&auto=webp&s=452883f03258ad551f1591011a4f510e3a7d6b90"),
        new BildBricka(589, 40,"https://preview.redd.it/vagfg610bok61.jpg?width=640&crop=smart&auto=webp&s=452883f03258ad551f1591011a4f510e3a7d6b90"),
        new BildBricka(112, 600,"https://preview.redd.it/l7btbwlgmdl61.jpg?width=640&crop=smart&auto=webp&s=cdd8263eb0c7b1e4e67b10fa31e3191894c81719"),
        new BildBricka(318, 145,"https://preview.redd.it/l7btbwlgmdl61.jpg?width=640&crop=smart&auto=webp&s=cdd8263eb0c7b1e4e67b10fa31e3191894c81719"),
        new BildBricka(500, 305, "https://preview.redd.it/worxz7hz48i61.jpg?width=960&crop=smart&auto=webp&s=9ac9d14f81ef42975f32fb092f1f16990baf1f86"),
        new BildBricka(219, 290,"https://preview.redd.it/worxz7hz48i61.jpg?width=960&crop=smart&auto=webp&s=9ac9d14f81ef42975f32fb092f1f16990baf1f86"),
        };
        for(Bricka b : brickor){
            center.getChildren().add(b);
            b.setOnMouseClicked(c1);
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
        stage.setTitle("Memory");
        stage.setScene(scene);
        stage.show();

        imageView.fitWidthProperty().bind(center.widthProperty());
        imageView.fitHeightProperty().bind(center.heightProperty());
    }
    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Bricka b = (Bricka)event.getSource();
            if(b1 == null) {
                b1 = b;
                b1.setCovered(false);
            }
            else if(b2 == null && b != b1) {
                b2 = b;
                b2.setCovered(false);
            }
        }
    }
    class TestaHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (b1 != null && b2 != null) {
                if (b1.liknar(b2)) {
                    center.getChildren().remove(b1);
                    center.getChildren().remove(b2);
                    hits++;
                    hitsLabel.setText(""+hits);
                }
                else {
                    b1.setCovered(true);
                    b2.setCovered(true);
                }
                tries++;
                triesLabel.setText(""+tries);
                b1 = b2 = null;
            }
        }
    }
}
