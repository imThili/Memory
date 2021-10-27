import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BildBricka extends Bricka{
    private Image image;
    private String filename;
    public BildBricka(double x, double y, String filename){
        super(x, y);
        this.filename=filename;
        image = new Image(filename);
    }
    public void paintUncovered(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.drawImage(image, 0, 0, getWidth(), getHeight());
    }
    public boolean liknar(Bricka other){
        return other instanceof BildBricka && filename.equals(((BildBricka) other).filename);
    }
}
