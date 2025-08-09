package com.ug.javafx;

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

        // Draw the path first (behind markers) with enhanced visibility
        if (route.size() > 1) {
            // Draw thick outline for better visibility
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(8);
            for (int i = 0; i < route.size() - 1; i++) {
                Point2D from = coords.get(route.get(i));
                Point2D to = coords.get(route.get(i + 1));
                if (from != null && to != null) {
                    gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
                }
            }
            
            // Draw bright route line on top
            gc.setStroke(Color.LIME);  // Bright green for high visibility
            gc.setLineWidth(6);
            for (int i = 0; i < route.size() - 1; i++) {
                Point2D from = coords.get(route.get(i));
                Point2D to = coords.get(route.get(i + 1));
                if (from != null && to != null) {
                    gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
                    
                    // Add direction arrow in middle of each segment
                    double midX = (from.getX() + to.getX()) / 2;
                    double midY = (from.getY() + to.getY()) / 2;
                    drawDirectionArrow(gc, from, to, midX, midY);
                }
            }
        }

        // Draw all known nodes with enhanced visibility
        for (Map.Entry<String, Point2D> entry : coords.entrySet()) {
            Point2D pt = entry.getValue();
            boolean isOnRoute = route.contains(entry.getKey());
            
            if (isOnRoute) {
                // Special markers for route nodes
                if (entry.getKey().equals(route.get(0))) {
                    // Start point - Green circle
                    gc.setFill(Color.GREEN);
                    gc.fillOval(pt.getX() - 10, pt.getY() - 10, 20, 20);
                    gc.setFill(Color.WHITE);
                    gc.fillText("START", pt.getX() - 15, pt.getY() - 15);
                } else if (entry.getKey().equals(route.get(route.size() - 1))) {
                    // End point - Red circle
                    gc.setFill(Color.RED);
                    gc.fillOval(pt.getX() - 10, pt.getY() - 10, 20, 20);
                    gc.setFill(Color.WHITE);
                    gc.fillText("END", pt.getX() - 12, pt.getY() - 15);
                } else {
                    // Intermediate waypoint - Orange circle
                    gc.setFill(Color.ORANGE);
                    gc.fillOval(pt.getX() - 8, pt.getY() - 8, 16, 16);
                }
            } else {
                // Regular campus locations - Blue circles
                gc.setFill(Color.BLUE);
                gc.fillOval(pt.getX() - 6, pt.getY() - 6, 12, 12);
            }
            
            // Draw text labels with background for better readability
            String label = entry.getKey();
            double textWidth = label.length() * 6; // Approximate text width
            
            // Background rectangle for text
            gc.setFill(Color.WHITE);
            gc.fillRect(pt.getX() + 12, pt.getY() - 18, textWidth + 4, 16);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(pt.getX() + 12, pt.getY() - 18, textWidth + 4, 16);
            
            // Text label
            gc.setFill(Color.BLACK);
            gc.fillText(label, pt.getX() + 14, pt.getY() - 6);
        }
    }
    
    private void drawDirectionArrow(GraphicsContext gc, Point2D from, Point2D to, double x, double y) {
        // Calculate arrow direction
        double dx = to.getX() - from.getX();
        double dy = to.getY() - from.getY();
        double length = Math.sqrt(dx * dx + dy * dy);
        
        if (length > 0) {
            // Normalize direction
            dx /= length;
            dy /= length;
            
            // Arrow size
            double arrowLength = 15;
            double arrowWidth = 8;
            
            // Arrow tip
            double tipX = x + dx * arrowLength / 2;
            double tipY = y + dy * arrowLength / 2;
            
            // Arrow base points
            double baseX1 = x - dx * arrowLength / 2 - dy * arrowWidth;
            double baseY1 = y - dy * arrowLength / 2 + dx * arrowWidth;
            double baseX2 = x - dx * arrowLength / 2 + dy * arrowWidth;
            double baseY2 = y - dy * arrowLength / 2 - dx * arrowWidth;
            
            // Draw arrow
            gc.setFill(Color.YELLOW);
            gc.fillPolygon(new double[]{tipX, baseX1, baseX2}, new double[]{tipY, baseY1, baseY2}, 3);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokePolygon(new double[]{tipX, baseX1, baseX2}, new double[]{tipY, baseY1, baseY2}, 3);
        }
    }
}
// Enhanced MapCanvas with improved route visualization:
// - Bright green route lines with black outlines for high visibility
// - Direction arrows showing route flow
// - Color-coded markers: Green (start), Red (end), Orange (waypoints), Blue (other locations)
// - Text labels with white backgrounds for better readability
// - Thicker lines and larger markers for clear route definition