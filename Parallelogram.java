import java.awt.*;

public class Parallelogram extends Polygon_ {
    private int[] xPoint;
    private int[] yPoint;
    private Color color;

   
    public Parallelogram(Color color, Point upperLeft, Point lowerRight, int xOffset){
        super(color);
        this.color = color;
        int x1 = upperLeft.x;
        int y1 = upperLeft.y;
        int x2 = lowerRight.x;
        int y2 = lowerRight.y;

        xPoint = new int[] {x1, x2, x2 - xOffset, x1 - xOffset};
        yPoint = new int[] {y1, y1, y2, y2};
    }

   
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillPolygon(xPoint, yPoint, 4);
    }
}
