package ifsc.edu.br.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.Map;

public class BellmanFord<V> extends AbstractAlgorithm<V> {

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
        int vertexCount = graph.vertexSet().size();
        for (int i = 1; i < vertexCount; i++) {
            for (DefaultWeightedEdge edge : graph.edgeSet()) {
                V u = graph.getEdgeSource(edge);
                V v = graph.getEdgeTarget(edge);
                double weight = graph.getEdgeWeight(edge);
                if (distances.get(u) + weight < distances.get(v)) {
                    distances.put(v, distances.get(u) + weight);
                    predecessors.put(v, u);
                }
            }
        }
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            V u = graph.getEdgeSource(edge);
            V v = graph.getEdgeTarget(edge);
            double weight = graph.getEdgeWeight(edge);
            if (distances.get(u) + weight < distances.get(v)) {
                throw new IllegalArgumentException("Graph contains a negative-weight cycle");
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
