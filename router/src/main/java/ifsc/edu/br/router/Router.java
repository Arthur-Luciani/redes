package ifsc.edu.br.router;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Router {
    private String id;
    private Graph<String, DefaultEdge> graph;
    private Map<String, Router> neighbors;

    public Router(String id) {
        this.id = id;
        this.graph = new SimpleWeightedGraph<>(DefaultEdge.class);
        this.neighbors = new HashMap<>();
        this.graph.addVertex(id);
    }

    public void addNeighbor(Router neighbor, int weight) {
        String neighborId = neighbor.id;
        this.graph.addVertex(neighborId);
        this.graph.setEdgeWeight(this.graph.addEdge(this.id, neighborId), weight);
        this.neighbors.put(neighborId, neighbor);
        neighbor.neighbors.put(this.id, this);
        notifyNeighbors(neighbor);
    }

    private void notifyNeighbors(Router newNeighbor) {
        notifyNeighbors();
        newNeighbor.receiveMessage("Graph update from " + this.id, this.graph);
    }

    private void notifyNeighbors() {
        for (Router neighbor : neighbors.values()) {
            neighbor.receiveMessage("Graph update from " + this.id, this.graph);
        }
    }

    public void sendMessage(String message,  String destinationId) {
        sendMessage(message, this.id, destinationId);
    }

    protected void sendMessage(String message, String sourceId, String destinationId) {
        BellmanFordShortestPath<String, DefaultEdge> bellmanFordAlg = new BellmanFordShortestPath<>(this.graph);
        var path = bellmanFordAlg.getPath(this.id, destinationId);

        if (path != null && !path.getVertexList().isEmpty()) {
            String nextHop = path.getVertexList().get(1); // Get the next hop in the path
            Router nextRouter = this.neighbors.get(nextHop);
            if (nextRouter != null) {
                if (nextHop.equals(destinationId)) {
                    nextRouter.receiveMessage(message, this.graph);
                    return;
                }
                System.out.println("Router " + this.id + " redirecting message: " + message + " to " + nextHop);
                nextRouter.sendMessage(message, sourceId, destinationId);
            } else {
                System.out.println("Next hop router not found: " + nextHop);
            }
        } else {
            System.out.println("No path found to destination: " + destinationId);
        }
    }

    public void receiveMessage(String message, Graph<String, DefaultEdge> receivedGraph) {
        mergeGraphs(receivedGraph);
        System.out.println("Router " + this.id + " received message: " + message);
    }

    private void mergeGraphs(Graph<String, DefaultEdge> receivedGraph) {
        for (String vertex : receivedGraph.vertexSet()) {
            this.graph.addVertex(vertex);
        }
        for (DefaultEdge edge : receivedGraph.edgeSet()) {
            String source = receivedGraph.getEdgeSource(edge);
            String target = receivedGraph.getEdgeTarget(edge);
            double weight = receivedGraph.getEdgeWeight(edge);
            Optional.ofNullable(this.graph.addEdge(source, target))
                    .ifPresent(e -> {
                        this.graph.setEdgeWeight(e, weight);
                        notifyNeighbors();
                    });
        }
    }

    public void printGraph() {
        System.out.println("Graph of Router " + this.id + ": " + this.graph.toString());
    }
}