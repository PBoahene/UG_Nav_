import java.util.*;

public class Graph {
    public Map<String, Node> nodes = new HashMap<>();

    public void addNode(String name) {
        nodes.putIfAbsent(name, new Node(name));
    }

    public void addEdge(String source, String target, double distance) {
        Node src = nodes.get(source);
        Node tgt = nodes.get(target);
        Edge edge = new Edge(tgt, distance);
        src.edges.add(edge);
    }

    // Dijkstra's Algorithm – for shortest distances
    public Map<String, Double> dijkstra(String startName) {
        Map<String, Double> distances = new HashMap<>();
        PriorityQueue<NodeDist> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.dist));
        Set<String> visited = new HashSet<>();

        for (String name : nodes.keySet()) distances.put(name, Double.MAX_VALUE);
        distances.put(startName, 0.0);
        pq.add(new NodeDist(startName, 0.0));

        while (!pq.isEmpty()) {
            NodeDist current = pq.poll();
            if (visited.contains(current.name)) continue;
            visited.add(current.name);

            Node node = nodes.get(current.name);
            for (Edge e : node.edges) {
                double newDist = distances.get(node.name) + e.distance;
                if (newDist < distances.get(e.target.name)) {
                    distances.put(e.target.name, newDist);
                    pq.add(new NodeDist(e.target.name, newDist));
                }
            }
        }

        return distances;
    }

    // DFS to find all paths from start to end
    public List<Route> findAllPaths(String start, String end) {
        List<Route> routes = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();
        findAllPathsHelper(start, end, visited, currentPath, 0.0, routes);
        return routes;
    }

    private void findAllPathsHelper(String current, String end, Set<String> visited,
                                    List<String> path, double dist, List<Route> routes) {
        visited.add(current);
        path.add(current);

        if (current.equals(end)) {
            routes.add(new Route(String.join(" → ", path), dist));
        } else {
            Node node = nodes.get(current);
            for (Edge edge : node.edges) {
                if (!visited.contains(edge.target.name)) {
                    findAllPathsHelper(edge.target.name, end, visited, path,
                            dist + edge.distance, routes);
                }
            }
        }

        visited.remove(current);
        path.remove(path.size() - 1);
    }

    // Estimate travel time for a route using traffic factor
    public double estimateTime(String path) {
        String[] steps = path.split(" → ");
        double totalTime = 0;

        for (int i = 0; i < steps.length - 1; i++) {
            Node from = nodes.get(steps[i]);
            for (Edge e : from.edges) {
                if (e.target.name.equals(steps[i + 1])) {
                    totalTime += e.distance * e.trafficFactor;
                    break;
                }
            }
        }

        return totalTime;
    }

    // ====== Inner Classes ======

    static class Node {
        String name;
        List<Edge> edges = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }
    }

    static class Edge {
        Node target;
        double distance;
        double trafficFactor;

        Edge(Node target, double distance) {
            this.target = target;
            this.distance = distance;
            this.trafficFactor = 1.0 + Math.random(); // Simulates traffic: 1x – 2x
        }
    }

    static class NodeDist {
        String name;
        double dist;

        NodeDist(String name, double dist) {
            this.name = name;
            this.dist = dist;
        }
    }
}
// This class represents the graph structure of the campus, including nodes and edges.
// It provides methods for adding nodes and edges, finding shortest paths using Dijkstra's algorithm,