package ifsc.edu.br.finalrouter;

import lombok.Getter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class Router {

    private final String name;

    private List<Node> distanceVector;
    private List<Router> neighborsToNotify;

    public Router(String name) {
        this.name = name;
        this.neighborsToNotify = new ArrayList<>();
        this.distanceVector = new ArrayList<>(List.of(new Node(this, this, BigInteger.ZERO)));
    }


    private void addNeighbor(Router neighbor, int distance) {
        System.out.println("\u001B[32mAdding neighbor " + neighbor.getName() + " to " + this.getName() + " with distance " + distance + "\u001B[0m");
        this.addNeighbor(neighbor, BigInteger.valueOf(distance));
    }

    private void addNeighbor(Router neighbor, BigInteger distance) {
        this.internalAddNeighbor(neighbor, distance);
        neighbor.internalAddNeighbor(this, distance);
        this.notifyNeighbors();
        neighbor.notifyNeighbors();
    }

    private void internalAddNeighbor(Router neighbor, BigInteger distance) {
        neighborsToNotify.add(neighbor);
        distanceVector.add(new Node(this, neighbor, distance));
    }

    private boolean calculateNewRoute(Node node, Router origin) {
        var actualOriginNode = this.distanceVector.stream()
                .filter(n -> n.getTo().equals(origin))
                .findFirst().orElseThrow();
        var possibleDistance = actualOriginNode.getDistance().add(node.getDistance());

        var actualNode = this.distanceVector.stream()
                .filter(n -> n.getTo().equals(node.getTo()))
                .findFirst();

        if (actualNode.isPresent()) {
            var actualToNode = actualNode.get();
            if (possibleDistance.compareTo(actualToNode.getDistance()) < 0) {
                actualToNode.setDistance(possibleDistance);
                actualToNode.setUsing(origin);
                return true;
            }
        } else {
            this.distanceVector.add(new Node(origin, node.getTo(), possibleDistance));
            return true;
        }
        return false;
    }

    private void update(Router origin, List<Node> distanceVector) {
        var updated = distanceVector.stream().anyMatch(node -> calculateNewRoute(node, origin));
        if (updated) {
            notifyNeighbors();
        }
    }

    public void notifyNeighbors() {
        neighborsToNotify.forEach(neighbor -> neighbor.update(this, distanceVector));
    }

    public void printDistanceVectorGraphically() {
        System.out.println("====================================================");
        System.out.println("Distance Vector for Router " + name + ":");
        distanceVector.stream()
                .sorted(Comparator.comparing(node -> node.getTo().getName()))
                .forEach(node -> System.out.println("To: " + node.getTo().getName() + " using: " + node.getUsing().getName() + " Cost: " + node.getDistance()));
        System.out.println("====================================================");
    }

    public static void main(String[] args) {
        Router routerA = new Router("A");
        Router routerB = new Router("B");
        Router routerC = new Router("C");
        Router routerD = new Router("D");

        routerA.addNeighbor(routerB, 9);
        routerA.addNeighbor(routerC, 7);

        routerC.addNeighbor(routerD, 2);

        routerD.addNeighbor(routerB, -3);

        routerA.printDistanceVectorGraphically();
        routerB.printDistanceVectorGraphically();
        routerC.printDistanceVectorGraphically();
        routerD.printDistanceVectorGraphically();

    }
}
