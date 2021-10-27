import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class Bricka extends Canvas {
    private boolean covered = true;

    public Bricka(double x, double y) {
        super(130, 130);
        relocate(x,y);
        paintCovered();
    }
    public void paintCovered() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFont(new Font(null, 20));
        gc.setStroke(Color.BLACK);
        gc.strokeText("mahahahahha", 0, 0);
        gc.setFill(Color.AQUA);
        gc.fillRect(0, 0, getWidth(), getHeight());
    }
    public void setCovered(boolean on) {
        covered = on;
        if(covered)
            paintCovered();
        else
            paintUncovered();
    }
    public abstract void paintUncovered();
    public abstract boolean liknar(Bricka other);
}

