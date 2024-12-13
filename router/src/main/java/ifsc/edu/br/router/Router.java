package ifsc.edu.br.router;

import ifsc.edu.br.algorithm.Algorithm;
import ifsc.edu.br.utils.Color;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private final String id;

    @Getter
    private final Graph<String, DefaultWeightedEdge> graph;

    private final Map<String, Router> neighbors;

    private final Algorithm<String> algorithm;

    public Router(String id, Algorithm<String> algorithm) {
        this.id = id;
        this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        this.neighbors = new HashMap<>();
        this.graph.addVertex(id);
        this.algorithm = algorithm;
        this.algorithm.recalculate(this.graph, this.id);
    }

    /**
     * Define um novo vertice ao grafo.
     * </br>
     * Adiciona uma nova aresta entre o roteador e seu vizinho.
     * </br>
     * Recalcula o algoritmo de roteamento.
     * </br>
     * Notifica os vizinhos sobre a atualização do grafo.
     * </br>
     * @param neighbor Roteador vizinho
     * @param weight Distancia entre os roteadores
     */
    public void addNeighbor(Router neighbor, int weight) {
        String neighborId = neighbor.id;
        this.graph.addVertex(neighborId);
        this.graph.setEdgeWeight(this.graph.addEdge(this.id, neighborId), weight);
        this.algorithm.recalculate(this.graph, this.id);
        this.neighbors.put(neighborId, neighbor);
        neighbor.neighbors.put(this.id, this);
        notifyNeighbors(neighbor);
    }

    private void notifyNeighbors(Router newNeighbor) {
        notifyNeighbors();
        newNeighbor.receiveMessage("Graph update from " + this.id, this.graph);
    }

    /**
     * Notifica os vizinhos sobre a atualização do grafo.
     */
    private void notifyNeighbors() {
        for (Router neighbor : neighbors.values()) {
            neighbor.receiveMessage("Graph update from " + this.id, this.graph);
        }
    }

    public void sendMessage(String message, String destinationId) {
        sendMessage(message, this.id, destinationId);
    }

    /**
     * Com base no algoritimo de roteamento, envia uma mensagem para o roteador de destino.
     * </br>
     * Caso o próximo roteador da rota seja o próprio roteador de destino, a mensagem é entregue.
     * </br>
     * Caso contrário, a mensagem é redirecionada para o próximo roteador da rota até chegar ao destino.
     * @param message Mensagem a ser enviada
     * @param sourceId Roteador de origem
     * @param destinationId Roteador de destino
     */
    protected void sendMessage(String message, String sourceId, String destinationId) {
        String nextHop = algorithm.getNextHop(destinationId);
        Router nextRouter = this.neighbors.get(nextHop);

        if (nextRouter != null) {
            if (nextHop.equals(destinationId)) {
                nextRouter.receiveMessage(message, this.graph);
                return;
            }
            System.out.println(Color.BLUE + "Router " + this.id + " redirecting to router " + nextHop + " with message: " + message + Color.RESET);
            nextRouter.sendMessage(message, sourceId, destinationId);
        } else {
            System.out.println(Color.RED + "Next hop router not found: " + nextHop + Color.RESET);
        }
    }

    public void receiveMessage(String message, Graph<String, DefaultWeightedEdge> receivedGraph) {
        mergeGraphs(receivedGraph);
        System.out.println(Color.GREEN + "Router " + this.id + " received message: " + message + Color.RESET);
    }


    /**
     * Atualiza o grafo do roteador com base no grafo recebido de um vizinho.
     * </br>
     * Adiciona novos vértices ao grafo.
     * </br>
     * Adiciona novas arestas ao grafo, caso ainda não exista.
     * @param receivedGraph Grafo recebido de um vizinho
     */
    private void mergeGraphs(Graph<String, DefaultWeightedEdge> receivedGraph) {
        for (String vertex : receivedGraph.vertexSet()) {
            this.graph.addVertex(vertex);
        }
        var updated = false;
        for (DefaultWeightedEdge edge : receivedGraph.edgeSet()) {
            String source = receivedGraph.getEdgeSource(edge);
            String target = receivedGraph.getEdgeTarget(edge);
            double weight = receivedGraph.getEdgeWeight(edge);
            var e = this.graph.addEdge(source, target);
            if (e != null) {
                this.graph.setEdgeWeight(e, weight);
                updated = true;
            }
        }
        if (updated) {
            this.algorithm.recalculate(this.graph, this.id);
            notifyNeighbors();
        }
    }

    public void printGraph() {
        System.out.println(Color.YELLOW + "Graph of Router " + this.id + ": " + this.graph.toString() + Color.RESET);
    }
}