import java.awt.*;

// Abstract class that defines all hollow polygons

abstract class HollowPolygon extends Polygon_ {

    // Constructor that calls super constructor

    public HollowPolygon(Color color, int vertexCount) {
        super(color, vertexCount);
    }
}
