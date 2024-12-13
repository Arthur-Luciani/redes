package ifsc.edu.br.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.Map;

public class PathVector<V> extends AbstractAlgorithm<V> {


    @Override
    public void recalculate(Graph<V, DefaultWeightedEdge> graph, V source) {
        this.graph = graph;
        this.source = source;
        this.distances = new HashMap<>();
        this.predecessors = new HashMap<>();
        initialize();
        calculatePaths();
    }

    private void initialize() {
        for (V vertex : graph.vertexSet()) {
            if (vertex.equals(source)) {
                distances.put(vertex, 0.0);
            } else if (graph.containsEdge(source, vertex)) {
                distances.put(vertex, graph.getEdgeWeight(graph.getEdge(source, vertex)));
            } else {
                distances.put(vertex, Double.POSITIVE_INFINITY);
            }
            predecessors.put(vertex, null);
        }
    }

    private void calculatePaths() {
        for (V vertex : graph.vertexSet()) {
            if (!vertex.equals(source)) {
                for (DefaultWeightedEdge edge : graph.edgesOf(vertex)) {
                    V neighbor = graph.getEdgeSource(edge).equals(vertex) ? graph.getEdgeTarget(edge) : graph.getEdgeSource(edge);
                    double newDist = distances.get(neighbor) + graph.getEdgeWeight(edge);
                    if (newDist < distances.get(vertex)) {
                        distances.put(vertex, newDist);
                        predecessors.put(vertex, neighbor);
                    }
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