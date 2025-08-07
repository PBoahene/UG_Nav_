import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

public class MapCanvas extends Canvas {
    private final Image mapImage;
    private final Map<String, Point2D> coords;
    private final List<String> route;

    public MapCanvas(Image mapImage, Map<String, Point2D> coords, List<String> route) {
        super(mapImage.getWidth(), mapImage.getHeight());
        this.mapImage = mapImage;
        this.coords = coords;
        this.route = route;
        draw();
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        // Draw background map
        gc.drawImage(mapImage, 0, 0);

        // Draw all known nodes as blue circles
        gc.setFill(Color.BLUE);
        for (Map.Entry<String, Point2D> entry : coords.entrySet()) {
            Point2D pt = entry.getValue();
            gc.fillOval(pt.getX() - 5, pt.getY() - 5, 10, 10);
            gc.fillText(entry.getKey(), pt.getX() + 8, pt.getY() - 5);
        }

        // Draw the path in RED
        if (route.size() > 1) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(3);

            for (int i = 0; i < route.size() - 1; i++) {
                Point2D from = coords.get(route.get(i));
                Point2D to = coords.get(route.get(i + 1));

                if (from != null && to != null) {
                    gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
                }
            }
        }
    }
}
// This class represents a canvas that displays the campus map and the route.
// It extends the Canvas class and draws the map image, nodes, and route on it.