package ifsc.edu.br.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Map;

public abstract class AbstractAlgorithm <V> implements Algorithm<V>{

    protected V source;
    protected Graph<V, DefaultWeightedEdge> graph;
    protected Map<V, Double> distances;
    protected Map<V, V> predecessors;

    @Override
    public V getNextHop(V destination) {
        V current = destination;
        V predecessor = predecessors.get(current);
        while (predecessor != null && !predecessor.equals(source)) {
            current = predecessor;
            predecessor = predecessors.get(current);
        }
        return current.equals(source) ? destination : current;
    }


}
