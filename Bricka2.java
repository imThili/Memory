import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bricka2 extends Bricka {
    public Bricka2(double x, double y) {
        super(x, y);
    }
    public void paintUncovered() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.GREEN);
        gc.fillPolygon(new double[]{0, 25, 50}, new double[]{50, 25, 20}, 3);
    }
    public boolean liknar(Bricka other) {
        return other instanceof Bricka2;
    }
}

