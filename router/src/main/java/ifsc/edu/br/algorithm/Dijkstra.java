package ifsc.edu.br.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Dijkstra<V> extends AbstractAlgorithm<V> {

    @Override
    public void recalculate(Graph<V, DefaultWeightedEdge> graph, V source) {
        this.graph = graph;
        this.source = source;
        this.distances = new HashMap<>();
        this.predecessors = new HashMap<>();
        initialize();
        calculateShortestPaths();
    }

    private void initialize() {
        for (V vertex : graph.vertexSet()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
            predecessors.put(vertex, null);
        }
        distances.put(source, 0.0);
    }

    private void calculateShortestPaths() {
        Set<V> visited = new HashSet<>();
        PriorityQueue<V> queue = new PriorityQueue<>((v1, v2) -> Double.compare(distances.get(v1), distances.get(v2)));
        queue.add(source);

        while (!queue.isEmpty()) {
            V current = queue.poll();
            if (!visited.add(current)) {
                continue;
            }

            for (DefaultWeightedEdge edge : graph.edgesOf(current)) {
                V neighbor = graph.getEdgeSource(edge).equals(current) ? graph.getEdgeTarget(edge) : graph.getEdgeSource(edge);
                if (visited.contains(neighbor)) {
                    continue;
                }

                double newDist = distances.get(current) + graph.getEdgeWeight(edge);
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    predecessors.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
    }

    @Override
    public double getDistance(V vertex) {
        return distances.getOrDefault(vertex, Double.POSITIVE_INFINITY);
    }

    @Override
    public V getPredecessor(V vertex) {
        return predecessors.get(vertex);
    }

    @Override
    public Map<V, Double> getDistances() {
        return distances;
    }

    @Override
    public Map<V, V> getPredecessors() {
        return predecessors;
    }

}