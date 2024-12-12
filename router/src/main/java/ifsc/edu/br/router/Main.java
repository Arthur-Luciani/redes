package ifsc.edu.br.router;

public class Main {
    public static void main(String[] args) {
        Router routerA = new Router("A");
        Router routerB = new Router("B");
        Router routerC = new Router("C");

        routerA.addNeighbor(routerB, 10);
        routerB.addNeighbor(routerC, 5);

        routerA.sendMessage("Hello from A to C", "C");
        routerB.sendMessage("Hello from B to C", "C");

        routerA.printGraph();
        routerB.printGraph();
        routerC.printGraph();
    }
}
