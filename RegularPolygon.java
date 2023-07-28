import java.awt.*;

public class RegularPolygon extends Polygon_{
    private int radius;
    private int sides;
    private Point center;
    private int[] xPoints;
    private int[] yPoints;
    private Color color;

    public RegularPolygon(Color color, int sides, Point center, int rad){
        super(color);
        this.color = color;
        this.radius = rad;
        this.sides = sides;
        this.center = center;
        calculatePoints();
    }

    private void calculatePoints(){
        xPoints = new int[sides];
        yPoints = new int[sides];
        
        double angle = 2 * Math.PI / sides;
        for(int i = 0; i < sides; i++){
            xPoints[i] = (int)(center.x + radius * Math.cos(i * angle));
            yPoints[i] = (int)(center.y + radius * Math.sin(i * angle));
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(color);
        g.fillPolygon(xPoints, yPoints, sides);
    }
}
