package ifsc.edu.br.router;

import lombok.experimental.UtilityClass;

import java.util.Comparator;

@UtilityClass
public class PrintUtils {

    public static void printDistanceVector(Router router) {
        System.out.println("====================================================");
        System.out.println("Distance Vector for Router " + router.getName() + ":");
        router.getDistanceVector().stream()
                .sorted(Comparator.comparing(node -> node.getDestination().getName()))
                .forEach(node -> System.out.println("To: " + node.getDestination().getName() + " using: " + node.getUsing().getName() + " Cost: " + node.getDistance()));
        System.out.println("====================================================");
    }

    public static void printNeighbors(Router router) {
        System.out.println("====================================================");
        System.out.println("Neighbors for Router " + router.getName() + ":");
        router.getNeighbors().forEach(neighbor -> System.out.println(neighbor.getName()));
        System.out.println("====================================================");
    }
}
