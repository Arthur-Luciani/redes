package ifsc.edu.br.router;

public class Main {

    public static void main(String[] args) {
        var routerA = new Router("A");
        var routerB = new Router("B");
        var routerC = new Router("C");
        var routerD = new Router("D");

        routerA.addNeighbor(routerB, 9);
        routerA.addNeighbor(routerC, 7);

        routerC.addNeighbor(routerD, 2);

        routerB.addNeighbor(routerD, -3);

        PrintUtils.printNeighbors(routerA);
        PrintUtils.printNeighbors(routerB);
        PrintUtils.printNeighbors(routerC);
        PrintUtils.printNeighbors(routerD);

        PrintUtils.printDistanceVector(routerA);
        PrintUtils.printDistanceVector(routerB);
        PrintUtils.printDistanceVector(routerC);
        PrintUtils.printDistanceVector(routerD);
    }
}
