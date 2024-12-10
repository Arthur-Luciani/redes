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
    private List<Router> fromNeighbors;

    public Router(String name) {
        this.name = name;
        this.fromNeighbors = new ArrayList<>();
        this.distanceVector = new ArrayList<>();
    }

    public void addNeighbor(Router neighbor, int distance) {
        addNeighbor(neighbor, BigInteger.valueOf(distance));
    }

    public void addNeighbor(Router neighbor, BigInteger distance) {
        neighbor.addFromNeighbor(this);
        distanceVector.add(new Node(this, neighbor, distance));
        notifyNeighbors();
    }

    private void addFromNeighbor(Router neighbor) {
        fromNeighbors.add(neighbor);
    }

    private boolean calculateNewRoute(Node node, Router origin) {
        var originNode = this.distanceVector.stream()
                .filter(n -> n.getTo().equals(origin))
                .findFirst().orElseThrow();
        var possibleRouteCost = originNode.getDistance().add(node.getDistance());

        var myNode = this.distanceVector.stream()
                .filter(n -> n.getTo().equals(node.getTo()))
                .findFirst();

        if (myNode.isPresent()) {
            var actualToNode = myNode.get();
            if (possibleRouteCost.compareTo(actualToNode.getDistance()) < 0) {
                actualToNode.setDistance(possibleRouteCost);
                actualToNode.setUsing(origin);
                return true;
            }
        } else {
            this.distanceVector.add(new Node(origin, node.getTo(), possibleRouteCost));
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
        fromNeighbors.forEach(neighbor -> neighbor.update(this, distanceVector));
    }

    public void printDistanceVectorGraphically() {
        System.out.println("====================================================");
        System.out.println("Distance Vector for Router " + name + ":");
        distanceVector.stream()
                .sorted(Comparator.comparing(node -> node.getTo().getName()))
                .forEach(node -> {
                    System.out.println("To: " + node.getTo().getName() + " using: " + node.getUsing().getName() + " Cost: " + node.getDistance());
                });
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
