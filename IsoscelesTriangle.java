import java.awt.*;

public class IsoscelesTriangle extends Polygon_ {
    private int[] xPoints;
    private int[] yPoints;
    private Color color;
    
    public IsoscelesTriangle(Color color, Point top, int height, int width){
        super(color, 3);
        this.color = color;
        xPoints = new int[3];
        yPoints = new int[3];

        int x1 = top.x - width/2;
        int x2 = top.x;
        int x3 = top.x + width/2;
        int y1 = top.y + height;

        xPoints[0] = x1;
        xPoints[1] = x2;
        xPoints[2] = x3;
        yPoints[0] = y1;
        yPoints[1] = top.y;
        yPoints[2] = y1;
    }

    @Override
    public void draw(Graphics g){
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
