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
        graph.addNode("Law School");
        graph.addNode("Business School");
        graph.addNode("Akuafo Hall");
        graph.addNode("Pentagon Hostel");
        graph.addNode("Jones-Quartey Building");
        

        graph.addEdge("Legon Hall", "UGCS", 2);
        graph.addEdge("UGCS", "Balme Library", 1.5);
        graph.addEdge("Legon Hall", "Night Market", 3);
        graph.addEdge("Night Market", "Balme Library", 2);
        graph.addEdge("Night Market", "Bank", 1);
        graph.addEdge("Bank", "Cedi House", 1.2);
        graph.addEdge("Cedi House", "Balme Library", 1.1);
        graph.addEdge("Balme Library", "Law School", 1.4);
        graph.addEdge("Law School", "Business School", 1.0);
        graph.addEdge("Balme Library", "Pentagon Hostel", 3.0);
        graph.addEdge("UGCS", "Bank", 3.4);

    }

    private void setupLandmarks() {
        landmarkToNode.put("bank", "Bank");
        landmarkToNode.put("cedi house", "Cedi House");
        landmarkToNode.put("library", "Balme Library");
        landmarkToNode.put("law school", "Law School");
        landmarkToNode.put("business school", "Business School");
        landmarkToNode.put("night market", "Night Market");

    }

    private void showRouteOnMap(String pathString) {
        List<String> nodes = Arrays.asList(pathString.split(" → "));
        Image campusMap = new Image(getClass().getResourceAsStream("/com/ug/javafx/Map.png"));


        if (campusMap.isError()) {
            System.out.println("❌ Failed to load image: " + campusMap.getException());
        } else {
            System.out.println("✅ Map image loaded successfully!");
        }


    Map<String, Point2D> coords = Map.ofEntries(
         Map.entry("UGCS", new Point2D(200, 250)),
         Map.entry("Balme Library", new Point2D(300, 200)),
         Map.entry("Night Market", new Point2D(180, 350)),
         Map.entry("Bank", new Point2D(250, 320)),
         Map.entry("Cedi House", new Point2D(270, 280)),
         Map.entry("Legon Hall", new Point2D(100, 300)),
         Map.entry("Akuafo Hall", new Point2D(120, 350)),
         Map.entry("Pentagon Hostel", new Point2D(450, 380)),
         Map.entry("Cedi Conference Centre", new Point2D(270, 280)),
         Map.entry("Business School", new Point2D(320, 240)),
         Map.entry("Law School", new Point2D(350, 260)),
         Map.entry("Central Cafeteria", new Point2D(260, 320)),
         Map.entry("Jones-Quartey Building", new Point2D(210, 270))
    );


        MapCanvas canvas = new MapCanvas(campusMap, coords, nodes);
        Stage mapStage = new Stage();
        mapStage.setTitle("Top Route on Map");
        mapStage.setScene(new Scene(new StackPane(canvas)));
        mapStage.show();
    }
public static void main(String[] args) {
        launch(args);
    }
};
// Note: The MapCanvas class is assumed to be implemented elsewhere in the project.
// It should handle the drawing of the campus map and the route based on the provided coordinates.