package src.main.java;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageTile extends Canvas {
    private final  GraphicsContext graphicsContext = getGraphicsContext2D();
    private boolean covered = true;
    private Image image;
    private String filename;
    public ImageTile(double x, double y, String filename){
        super(130, 130);
        relocate(x, y);
        paintCovered();
        this.filename = filename;
        image = new Image(filename);
    }

    public void paintCovered(){
        graphicsContext.drawImage(new Image("https://i.ytimg.com/vi/KrmgQr32cQM/maxresdefault.jpg"), 0, 0, getWidth(), getHeight());
    }

    public void paintUncovered(){
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        graphicsContext.drawImage(image, 0, 0, getWidth(), getHeight());
    }

    public void setCovered(boolean on) {
        covered = on;
        if(covered)
            paintCovered();
        else
            paintUncovered();
    }

    public boolean isMatch(ImageTile other){
        return other != null && filename.equals(other.filename);
    }
}
