package ifsc.edu.br.utils;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class GraphDrawer {
public static void drawGraph(Graph<String, DefaultWeightedEdge> graph) {
    mxGraph mxGraph = new mxGraph();
    Object parent = mxGraph.getDefaultParent();
    mxGraph.getModel().beginUpdate();
    try {
        Map<String, Object> vertexMap = new HashMap<>();
        for (String vertex : graph.vertexSet()) {
            Object v = mxGraph.insertVertex(parent, null, vertex, 0, 0, 80, 80, "shape=ellipse");
            vertexMap.put(vertex, v);
        }
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
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