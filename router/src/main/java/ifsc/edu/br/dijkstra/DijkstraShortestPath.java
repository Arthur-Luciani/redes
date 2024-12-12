/*
package ifsc.edu.br.dijkstra;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.util.*;

public class DijkstraShortestPath<V, E> extends BaseShortestPathAlgorithm<V, E> {
    public DijkstraShortestPath(Graph<V, E> graph) {
        super(graph);
    }

    @Override
    public GraphPath<V, E> getPath(V source, V sink) {
        if (!graph.containsVertex(source) || !graph.containsVertex(sink)) {
            throw new IllegalArgumentException("Graph must contain both source and sink vertices");
        }

        Map<V, Double> distances = new HashMap<>();
        Map<V, E> predecessors = new HashMap<>();
        PriorityQueue<V> priorityQueue = new PriorityQueue<>(Comparator.comparing(distances::get));

        for (V vertex : graph.vertexSet()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(source, 0.0);
        priorityQueue.add(source);

        while (!priorityQueue.isEmpty()) {
            V current = priorityQueue.poll();
            if (current.equals(sink)) {
                break;
            }

            for (E edge : graph.outgoingEdgesOf(current)) {
                V neighbor = Graphs.getOppositeVertex(graph, edge, current);
                double newDist = distances.get(current) + graph.getEdgeWeight(edge);
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    predecessors.put(neighbor, edge);
                    priorityQueue.add(neighbor);
                }
            }
        }

        List<E> pathEdges = new ArrayList<>();
        V step = sink;
        while (predecessors.containsKey(step)) {
            E edge = predecessors.get(step);
            pathEdges.add(edge);
            step = Graphs.getOppositeVertex(graph, edge, step);
        }
        Collections.reverse(pathEdges);

        return new GraphWalk<>(graph, source, sink, pathEdges, distances.get(sink));
    }

    public static <V, E> GraphPath<V, E> findPathBetween(Graph<V, E> graph, V source, V sink) {
        return new DijkstraShortestPath<>(graph).getPath(source, sink);
    }
}
*/
