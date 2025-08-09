package com.ug.javafx;

public class Route {
    public String path;     // Full path as string e.g. "A → B → C"
    public double distance; // Total distance

    public Route(String path, double distance) {
        this.path = path;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route: " + path + " | Distance: " + distance;
    }
}
// This class represents a route in the campus navigation system.
// It contains the path as a string and the total distance of the route.