package ifsc.edu.br.roteador;


import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Network {
    private Graph<Router, DefaultWeightedEdge> graph;
    private Map<String, Router> routers;

    public Network() {
        this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        this.routers = new HashMap<>();
    }

    public void addRouter(String name) {
        Router router = new Router(name);
        routers.put(name, router);
        graph.addVertex(router);
    }

    public void removeRouter(String name) {
        Router router = routers.remove(name);
        if (router != null) {
            graph.removeVertex(router);
        }
    }

    public void addLink(String from, String to, double weight) {
        Router fromRouter = routers.get(from);
        Router toRouter = routers.get(to);
        if (fromRouter != null && toRouter != null) {
            DefaultWeightedEdge edge1 = graph.addEdge(fromRouter, toRouter);
            graph.setEdgeWeight(edge1, weight);
            fromRouter.addNeighbor(toRouter);

            DefaultWeightedEdge edge2 = graph.addEdge(toRouter, fromRouter);
            graph.setEdgeWeight(edge2, weight);
            toRouter.addNeighbor(fromRouter);
        }
    }

    public void removeLink(String from, String to) {
        Router fromRouter = routers.get(from);
        Router toRouter = routers.get(to);
        if (fromRouter != null && toRouter != null) {
            DefaultWeightedEdge edge1 = graph.getEdge(fromRouter, toRouter);
            if (edge1 != null) {
                graph.removeEdge(edge1);
                fromRouter.removeNeighbor(toRouter);
            }

            DefaultWeightedEdge edge2 = graph.getEdge(toRouter, fromRouter);
            if (edge2 != null) {
                graph.removeEdge(edge2);
                toRouter.removeNeighbor(fromRouter);
            }
        }
    }

    public void calculateShortestPaths(String sourceName) {
        Router source = routers.get(sourceName);
        if (source != null) {
            BellmanFordShortestPath<Router, DefaultWeightedEdge> bellmanFord = new BellmanFordShortestPath<>(graph);
            for (Router router : routers.values()) {
                if (!router.equals(source)) {
                    GraphPath<Router, DefaultWeightedEdge> path = bellmanFord.getPath(source, router);
                    if (path != null) {
                        System.out.println("Shortest path from " + source.getName() + " to " + router.getName() + ": " +
                                path.getVertexList() + " with total cost: " + path.getWeight());
                    } else {
                        System.out.println("No path from " + source.getName() + " to " + router.getName());
                    }
                }
            }
        }
    }

    public void drawGraph() {
        mxGraph mxGraph = new mxGraph();
        Object parent = mxGraph.getDefaultParent();

        mxGraph.getModel().beginUpdate();
        try {
            Map<Router, Object> vertexMap = new HashMap<>();
            for (Router router : routers.values()) {
                Object v = mxGraph.insertVertex(parent, null, router.getName(), 0, 0, 80, 80, "shape=ellipse");
                vertexMap.put(router, v);
            }

            for (DefaultWeightedEdge edge : graph.edgeSet()) {
                Router source = graph.getEdgeSource(edge);
                Router target = graph.getEdgeTarget(edge);
                Object edgeObject = mxGraph.insertEdge(parent, null, graph.getEdgeWeight(edge), vertexMap.get(source), vertexMap.get(target));
                mxGraph.getModel().setValue(edgeObject, String.valueOf(graph.getEdgeWeight(edge)));
            }
        } finally {
            mxGraph.getModel().endUpdate();
        }

        mxCircleLayout layout = new mxCircleLayout(mxGraph);
        layout.execute(mxGraph.getDefaultParent());

        JFrame frame = new JFrame();
        frame.getContentPane().add(new mxGraphComponent(mxGraph));
        frame.setTitle("Network Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.setVisible(true);
    }

}
