package ifsc.edu.br;

import lombok.Getter;

import java.math.BigInteger;
import java.util.*;

public class Router1 implements RouterSubscriber, Comparable<Router1> {

    private static final BigInteger MAX_VALUE = new BigInteger("2").pow(1024).subtract(BigInteger.ONE);

    @Getter
    private final String name;

    @Getter
    private Map<Router1, BigInteger> distanceVector;

    private Set<Router1> neighbors;

    public Router1(String name) {
        this.name = name;
        this.distanceVector = new HashMap<>();
        this.neighbors = new HashSet<>();
        initializeDistanceVector();
    }

    private void initializeDistanceVector() {
        distanceVector.put(this, BigInteger.ZERO);
    }

    public void addNeighbor(Router1 neighbor, int distance) {
        this.addNeighbor(neighbor, BigInteger.valueOf(distance));
    }

    public void addNeighbor(Router1 neighbor, BigInteger distance) {
        neighbor.addFromNeighbor(this);
        neighbors.add(neighbor);
        distanceVector.put(neighbor, distance);
        notifyNeighbors();
    }

    protected void addFromNeighbor(Router1 neighbor) {
        neighbors.add(neighbor);
    }

    @Override
    public void update(Map<Router1, BigInteger> receivedDistanceVector) {
        boolean updated = false;
        for (Map.Entry<Router1, BigInteger> entry : receivedDistanceVector.entrySet()) {
            var destination = entry.getKey();
            BigInteger newDistance = entry.getValue();
            BigInteger currentDistance = distanceVector.getOrDefault(destination, MAX_VALUE);
            BigInteger potentialNewDistance = currentDistance.add(newDistance);
            if (potentialNewDistance.abs().compareTo(currentDistance.abs()) < 0) {
                distanceVector.put(destination, potentialNewDistance);
                updated = true;
            }
        }
        if (updated) {
            notifyNeighbors();
        }
    }

    public void printDistanceVectorGraphically() {
        System.out.println("Distance Vector for Router " + name + ":");
        distanceVector.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    Router1 destination = entry.getKey();
                    BigInteger distance = entry.getValue();
                    StringBuilder stringBuilder = new StringBuilder();

                    String graph = "->";
                    System.out.println("Destination: " + destination.name + ", Distance: " + distance + " " + graph);
                });
    }

    private void notifyNeighbors() {
        neighbors.forEach(neighbor -> neighbor.update(distanceVector));
    }

    public static void main(String[] args) {
        Router1 router1A = new Router1("A");
        Router1 router1B = new Router1("B");
        Router1 router1C = new Router1("C");
        Router1 router1D = new Router1("D");

        router1A.addNeighbor(router1B, 9);
        router1A.addNeighbor(router1C, 7);

        router1C.addNeighbor(router1D, 2);

        router1D.addNeighbor(router1B, -3);

        router1A.printDistanceVectorGraphically();
    }

    @Override
    public int compareTo(Router1 o) {
        return this.name.compareTo(o.getName());
    }
}