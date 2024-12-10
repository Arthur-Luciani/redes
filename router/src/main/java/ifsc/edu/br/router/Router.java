package ifsc.edu.br.router;

import ifsc.edu.br.BellmanFordEdgeList;

import java.math.BigInteger;
import java.util.*;

public class Router {
    private static final BigInteger MAX_VALUE = new BigInteger("2").pow(1024).subtract(BigInteger.ONE);

    private Set<String> router = new TreeSet<>();

    Map<String, BigInteger> distanceVector = new HashMap<>();


    public void doSomething(List<RouterVertex> vertices) {
        vertices.forEach(routerVertex -> {
            router.add(routerVertex.getFrom());
            router.add(routerVertex.getTo());
        });
        // do something

        boolean relaxedAnEdge = true;
        for (int i = 0; i < router.size() - 1 && relaxedAnEdge; i++) {
            relaxedAnEdge = false;
            for (RouterVertex vertex : vertices) {

                var fromDist = distanceVector.getOrDefault(vertex.getFrom(), MAX_VALUE).add(vertex.getDistance());
                var toDist = distanceVector.getOrDefault(vertex.getTo(), MAX_VALUE);

                if (fromDist.add(vertex.getDistance()).abs().compareTo(toDist.abs()) < 0) {
                    distanceVector.put(vertex.getTo(), fromDist.add(vertex.getDistance()));
                    relaxedAnEdge = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        List<RouterVertex> vertices = new ArrayList<>();
        vertices.add(new RouterVertex("A", "B", 9));
        vertices.add(new RouterVertex("A", "C", 7));
        vertices.add(new RouterVertex("C", "D", 2));
        vertices.add(new RouterVertex("D", "B", -3));

        Router router = new Router();
        router.doSomething(vertices);

        router.distanceVector.forEach((s, bigInteger) -> {
            System.out.println("The cost to get from node A to" + s + " is " + bigInteger);
        });

    }
}
