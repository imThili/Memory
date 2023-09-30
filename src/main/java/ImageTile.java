package src.main.java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageTile extends Canvas {
    private final GraphicsContext graphicsContext = getGraphicsContext2D();
    private final Image image;
    private final String filename;
    private final double x;
    private final double y;

    public ImageTile(double x, double y, String filename) {
        super(130, 130);
        relocate(x, y);
        paintCovered();
        this.filename = filename;
        image = new Image(filename);
        this.x = x;
        this.y = y;
    }

    private void paintCovered() {
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        graphicsContext.drawImage(new Image("https://i.ytimg.com/vi/KrmgQr32cQM/maxresdefault.jpg"), 0, 0, getWidth(), getHeight());
    }

    private void paintUncovered() {
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        graphicsContext.drawImage(image, 0, 0, getWidth(), getHeight());
    }

    public void setCovered(boolean on) {
        if (on)
            paintCovered();
        else
            paintUncovered();
    }

    public boolean isMatch(ImageTile other) {
        return filename.equals(other.filename);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
