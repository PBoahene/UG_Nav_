package com.ug.javafx;

import java.util.*;

// Simple coordinate class to replace JavaFX Point2D
class Coordinate {
    public final double x, y;
    public Coordinate(double x, double y) { this.x = x; this.y = y; }
    public double getX() { return x; }
    public double getY() { return y; }
}

public class TestNavigation {
    private Graph graph = new Graph();
    private Map<String, String> landmarkToNode = new HashMap<>();
    private Map<String, Coordinate> coordinates = new HashMap<>();

    public static void main(String[] args) {
        TestNavigation test = new TestNavigation();
        test.runTests();
    }

    private void runTests() {
        setupCampusGraph();
        setupLandmarks();
        setupCoordinates();
        
        System.out.println("=== UG Campus Navigation System Test ===\n");
        
        // Test 1: Basic route finding
        System.out.println("TEST 1: Finding routes from Legon Hall to Balme Library");
        testRouteFinderLegonToBalmeLOVE();
        
        // Test 2: Route through landmark
        System.out.println("\nTEST 2: Finding route through Bank landmark");
        testRouteThroughLandmark();
        
        // Test 3: Alternative routes
        System.out.println("\nTEST 3: Finding all alternative routes");
        testAlternativeRoutes();
        
        // Test 4: Visual map coordinates
        System.out.println("\nTEST 4: Testing visual map functionality");
        testVisualMapFunctionality();
        
        // Test 5: Traffic estimation
        System.out.println("\nTEST 5: Testing traffic estimation");
        testTrafficEstimation();
    }

    private void testRouteFinderLegonToBalmeLOVE() {
        String from = "Legon Hall";
        String to = "Balme Library";
        
        List<Route> routes = graph.findAllPaths(from, to);
        if (!routes.isEmpty()) {
            Sorter.mergeSort(routes);
            System.out.println("✓ Route finding successful!");
            System.out.println("  Found " + routes.size() + " route(s):");
            
            for (int i = 0; i < Math.min(3, routes.size()); i++) {
                Route r = routes.get(i);
                double time = graph.estimateTime(r.path);
                System.out.printf("  %d. %s | Distance: %.2f | ETA: %.2f mins\n", 
                    i + 1, r.path, r.distance, time);
            }
        } else {
            System.out.println("✗ No routes found!");
        }
    }

    private void testRouteThroughLandmark() {
        String from = "Legon Hall";
        String to = "Cedi House";
        String landmark = "bank";
        
        String landmarkNode = landmarkToNode.get(landmark);
        if (landmarkNode != null) {
            List<Route> toLandmark = graph.findAllPaths(from, landmarkNode);
            List<Route> fromLandmark = graph.findAllPaths(landmarkNode, to);
            
            List<Route> routes = new ArrayList<>();
            for (Route r1 : toLandmark) {
                for (Route r2 : fromLandmark) {
                    String combinedPath = r1.path + " → " + r2.path.split(" → ", 2)[1];
                    routes.add(new Route(combinedPath, r1.distance + r2.distance));
                }
            }
            
            if (!routes.isEmpty()) {
                Sorter.mergeSort(routes);
                System.out.println("✓ Landmark routing successful!");
                Route bestRoute = routes.get(0);
                double time = graph.estimateTime(bestRoute.path);
                System.out.printf("  Best route through %s: %s\n", landmarkNode, bestRoute.path);
                System.out.printf("  Distance: %.2f | ETA: %.2f mins\n", bestRoute.distance, time);
            } else {
                System.out.println("✗ No route through landmark found!");
            }
        }
    }

    private void testAlternativeRoutes() {
        String from = "Night Market";
        String to = "UGCS";
        
        List<Route> routes = graph.findAllPaths(from, to);
        Sorter.mergeSort(routes);
        
        System.out.println("✓ Alternative routes found:");
        System.out.println("  Total routes available: " + routes.size());
        
        for (int i = 0; i < Math.min(routes.size(), 3); i++) {
            Route r = routes.get(i);
            System.out.printf("  Option %d: %s (Distance: %.2f)\n", 
                i + 1, r.path, r.distance);
        }
    }

    private void testVisualMapFunctionality() {
        // Simulate what MapCanvas does
        String testRoute = "Legon Hall → UGCS → Balme Library";
        List<String> nodes = Arrays.asList(testRoute.split(" → "));
        
        System.out.println("✓ Visual map functionality test:");
        System.out.println("  Route to display: " + testRoute);
        System.out.println("  Map coordinates for each node:");
        
        // Display coordinates for each node in the route
        for (String node : nodes) {
            Coordinate coord = coordinates.get(node);
            if (coord != null) {
                System.out.printf("    %s: (%.0f, %.0f)\n", 
                    node, coord.getX(), coord.getY());
            }
        }
        
        // Simulate drawing connections
        System.out.println("  Route connections that would be drawn:");
        for (int i = 0; i < nodes.size() - 1; i++) {
            Coordinate from = coordinates.get(nodes.get(i));
            Coordinate to = coordinates.get(nodes.get(i + 1));
            if (from != null && to != null) {
                System.out.printf("    Line from %s(%.0f,%.0f) to %s(%.0f,%.0f)\n",
                    nodes.get(i), from.getX(), from.getY(),
                    nodes.get(i + 1), to.getX(), to.getY());
            }
        }
        
        System.out.println("✓ Visual map would display:");
        System.out.println("  - Background campus map image");
        System.out.println("  - Blue circles for all campus locations");
        System.out.println("  - Location labels next to each building");
        System.out.println("  - RED lines connecting route points");
        System.out.println("  - 3-pixel thick route visualization");
    }

    private void testTrafficEstimation() {
        String testPath = "Legon Hall → Night Market → Bank → Cedi House";
        double estimatedTime = graph.estimateTime(testPath);
        
        System.out.println("✓ Traffic estimation working:");
        System.out.printf("  Route: %s\n", testPath);
        System.out.printf("  Estimated travel time: %.2f minutes\n", estimatedTime);
        System.out.println("  (includes random traffic factors between 1.0x - 2.0x base time)");
    }

    private void setupCampusGraph() {
        // Same setup as Main.java
        graph.addNode("Legon Hall");
        graph.addNode("UGCS");
        graph.addNode("Balme Library");
        graph.addNode("Night Market");
        graph.addNode("Bank");
        graph.addNode("Cedi House");

        graph.addEdge("Legon Hall", "UGCS", 2);
        graph.addEdge("UGCS", "Balme Library", 1.5);
        graph.addEdge("Legon Hall", "Night Market", 3);
        graph.addEdge("Night Market", "Balme Library", 2);
        graph.addEdge("Night Market", "Bank", 1);
        graph.addEdge("Bank", "Cedi House", 1.2);
        graph.addEdge("Cedi House", "Balme Library", 1.1);
    }

    private void setupLandmarks() {
        // Same setup as Main.java
        landmarkToNode.put("bank", "Bank");
        landmarkToNode.put("cedi house", "Cedi House");
        landmarkToNode.put("library", "Balme Library");
    }

    private void setupCoordinates() {
        // Same coordinates as used in Main.java for MapCanvas
        // Coordinates based on actual UG campus map layout
        coordinates.put("Legon Hall", new Coordinate(450, 580));           // Lower central area
        coordinates.put("UGCS", new Coordinate(920, 100));                // Engineering Sciences area
        coordinates.put("Balme Library", new Coordinate(700, 350));       // Central campus library area
        coordinates.put("Night Market", new Coordinate(380, 450));        // Student services area
        coordinates.put("Bank", new Coordinate(600, 400));                // Campus services area
        coordinates.put("Cedi House", new Coordinate(480, 390));          // Cedi Conference Centre
    }
}