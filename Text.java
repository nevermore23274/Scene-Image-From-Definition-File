import java.awt.*;

public class Text extends Image{
    private String text;
    private Color color;
    private Point location;

    public Text(Color color, Point loc, String text){
        super(color);
        this.color = color;
        this.location = loc;
        this.text = text;
    }
    
    public void draw(Graphics g){
        g.setColor(color);
        g.drawString(text, location.x, location.y);
    }
}
