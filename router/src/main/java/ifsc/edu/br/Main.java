package ifsc.edu.br;

import ifsc.edu.br.algorithm.Algorithm;
import ifsc.edu.br.algorithm.BellmanFord;
import ifsc.edu.br.algorithm.Dijkstra;
import ifsc.edu.br.algorithm.PathVector;
import ifsc.edu.br.router.Router;
import ifsc.edu.br.utils.Color;
import ifsc.edu.br.utils.GraphDrawer;

import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        execute(BellmanFord::new);
        execute(Dijkstra::new);
        var router = execute(PathVector::new);
        GraphDrawer.drawGraph(router.getGraph());
    }

    private static Router execute(Supplier<Algorithm<String>> supplier) {
        System.out.println(Color.CYAN + "Running simulation with " + supplier.get().getClass().getSimpleName() + " algorithm" + Color.RESET);

        Router routerA = new Router("A", supplier.get());
        Router routerB = new Router("B", supplier.get());
        Router routerC = new Router("C", supplier.get());
        Router routerD = new Router("D", supplier.get());
        Router routerE = new Router("E", supplier.get());
        Router routerF = new Router("F", supplier.get());
        Router routerG = new Router("G", supplier.get());
        Router routerH = new Router("H", supplier.get());

        routerA.addNeighbor(routerB, 15);
        routerA.addNeighbor(routerC, 7);
        routerB.addNeighbor(routerD, 10);
        routerC.addNeighbor(routerD, 2);
        routerD.addNeighbor(routerE, 5);
        routerE.addNeighbor(routerF, 3);
        routerF.addNeighbor(routerG, 8);
        routerG.addNeighbor(routerH, 6);
        routerH.addNeighbor(routerA, 12);
        routerB.addNeighbor(routerF, 12);
        routerC.addNeighbor(routerE, 4);

        // Send multiple messages with redirects
        routerA.sendMessage("Message from A to G", "G");
        routerB.sendMessage("Message from B to H", "H");
        routerC.sendMessage("Message from C to F", "F");

        // Print graphs after sending messages
        routerA.printGraph();
        routerB.printGraph();
        routerC.printGraph();
        routerD.printGraph();
        routerE.printGraph();
        routerF.printGraph();
        routerG.printGraph();
        routerH.printGraph();

        System.out.println(Color.CYAN + "Simulation finished" + Color.RESET);

        return routerA;
    }
}