import java.awt.*;

// Base class for all polygon classes
class Polygon_ extends Image {

    private int vertexCount;
    private Polygon polygon;

    // Constructor that initializes color and vertexCount of a polygon

    public Polygon_(Color color, int vertexCount) {
        super(color);
        this.vertexCount = vertexCount;
    }

    // Creates a polygon object given its vertices

    public Polygon_(Color color) {
        super(color);
    }

    public void createPolygon(int[] x_points, int[] y_points) {
        polygon = new Polygon(x_points, y_points, vertexCount);
    }

    // Draws polygon using polygon object

    @Override
    public void draw(Graphics graphics) {
        colorDrawing(graphics);
        graphics.drawPolygon(polygon);
    }
}
