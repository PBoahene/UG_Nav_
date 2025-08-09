package com.ug.javafx;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application {
    Graph graph = new Graph();
    Map<String, String> landmarkToNode = new HashMap<>();
    List<Route> currentRoutes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        setupCampusGraph();
        setupLandmarks();

        TextField fromInput = new TextField();
        TextField toInput = new TextField();
        TextField landmarkInput = new TextField();
        TextArea output = new TextArea();
        output.setEditable(false);

        Button findRoute = new Button("Find Routes");
        Button showMap = new Button("Show Top Route on Map");

        findRoute.setOnAction(_e -> {
            String from = fromInput.getText().trim();
            String to = toInput.getText().trim();
            String landmark = landmarkInput.getText().trim().toLowerCase();

            if (!graph.nodes.containsKey(from) || !graph.nodes.containsKey(to)) {
                output.setText("Invalid locations.");
                return;
            }

            List<Route> routes = new ArrayList<>();
            if (!landmark.isEmpty()) {
                String landmarkNode = landmarkToNode.get(landmark);
                if (landmarkNode == null) {
                    output.setText("Invalid landmark.");
                    return;
                }

                List<Route> toLandmark = graph.findAllPaths(from, landmarkNode);
                List<Route> fromLandmark = graph.findAllPaths(landmarkNode, to);

                for (Route r1 : toLandmark) {
                    for (Route r2 : fromLandmark) {
                        String combinedPath = r1.path + " → " + r2.path.split(" → ", 2)[1];
                        routes.add(new Route(combinedPath, r1.distance + r2.distance));
                    }
                }
            } else {
                routes = graph.findAllPaths(from, to);
            }

            if (routes.isEmpty()) {
                output.setText("No route found.");
                return;
            }

            Sorter.mergeSort(routes);
            currentRoutes = routes;

            StringBuilder sb = new StringBuilder("Top Routes (with traffic):\n");
            for (int i = 0; i < Math.min(3, routes.size()); i++) {
                Route r = routes.get(i);
                double time = graph.estimateTime(r.path);
                sb.append((i + 1)).append(". ").append(r.path)
                  .append(" | Distance: ").append(r.distance)
                  .append(" | ETA: ").append(String.format("%.2f", time)).append(" mins\n");
            }

            output.setText(sb.toString());
        });

        showMap.setOnAction(_ev -> {
            if (currentRoutes.isEmpty()) {
                output.setText("Find a route first.");
                return;
            }
            String path = currentRoutes.get(0).path;
            showRouteOnMap(path);
        });

        VBox layout = new VBox(10,
                new Label("From:"), fromInput,
                new Label("To:"), toInput,
                new Label("Landmark (optional):"), landmarkInput,
                findRoute, showMap, output);
        layout.setStyle("-fx-padding: 20;");
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setTitle("UG Navigate");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupCampusGraph() {
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
        landmarkToNode.put("bank", "Bank");
        landmarkToNode.put("cedi house", "Cedi House");
        landmarkToNode.put("library", "Balme Library");
    }

    private void showRouteOnMap(String pathString) {
        List<String> nodes = Arrays.asList(pathString.split(" → "));
        Image campusMap = new Image(getClass().getResourceAsStream("/com/ug/javafx/Campus_map.jpg"));

        // Coordinates based on actual UG campus map layout
        Map<String, Point2D> coords = Map.of(
            "Legon Hall", new Point2D(450, 580),           // Lower central area
            "UGCS", new Point2D(920, 100),                // Engineering Sciences area
            "Balme Library", new Point2D(700, 350),       // Central campus library area
            "Night Market", new Point2D(380, 450),        // Student services area
            "Bank", new Point2D(600, 400),                // Campus services area
            "Cedi House", new Point2D(480, 390)           // Cedi Conference Centre (red star)
        );

        MapCanvas canvas = new MapCanvas(campusMap, coords, nodes);
        
        // Create enhanced map window
        Stage mapStage = new Stage();
        StackPane mapPane = new StackPane(canvas);
        Scene mapScene = new Scene(mapPane, campusMap.getWidth(), campusMap.getHeight());
        
        mapStage.setScene(mapScene);
        mapStage.setTitle("UG Campus Navigation - Route: " + pathString);
        mapStage.setResizable(true);
        mapStage.setMaximized(false);
        
        // Center the window
        mapStage.centerOnScreen();
        mapStage.show();
        
        // Bring window to front
        mapStage.toFront();
        mapStage.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
// Note: The MapCanvas class is assumed to be implemented elsewhere in the project.
// It should handle the drawing of the campus map and the route based on the provided coordinates.