package ifsc.edu.br.algorithm;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Map;

public interface Algorithm<V> {
    void recalculate(Graph<V, DefaultWeightedEdge> graph, V source);

    double getDistance(V vertex);

    V getPredecessor(V vertex);

    V getNextHop(V destination);

    Map<V, Double> getDistances();

    Map<V, V> getPredecessors();
}