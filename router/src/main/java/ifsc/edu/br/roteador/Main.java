package ifsc.edu.br.roteador;

public class Main {
    public static void main(String[] args) {
        Network network = new Network();

        network.addRouter("A");
        network.addRouter("B");
        network.addRouter("C");
        network.addRouter("D");
        network.addRouter("E");
        network.addRouter("F");
        network.addRouter("G");
        network.addRouter("H");
        network.addRouter("I");
        network.addRouter("J");
        network.addRouter("K");
        network.addRouter("L");

        network.addLink("A", "B", 11);
        network.addLink("A", "C", 7);
        network.addLink("C", "D", 2);
        network.addLink("D", "B", 1);
        network.addLink("B", "E", 3);
        network.addLink("E", "F", 5);
        network.addLink("F", "G", 4);
        network.addLink("G", "H", 6);
        network.addLink("H", "I", 9);
        network.addLink("I", "J", 10);
        network.addLink("J", "A", 8);
        network.addLink("A", "D", 4);
        network.addLink("B", "F", 2);
        network.addLink("C", "E", 6);
        network.addLink("D", "G", 3);
        network.addLink("E", "H", 7);
        network.addLink("F", "I", 1);
        network.addLink("G", "J", 5);
        network.addLink("H", "A", 8);
        network.addLink("I", "B", 9);
        network.addLink("J", "C", 10);
        network.addLink("A", "E", 12);
        network.addLink("B", "G", 13);
        network.addLink("C", "H", 14);
        network.addLink("D", "I", 15);
        network.addLink("E", "J", 16);
        network.addLink("K", "L", 16);

        printLinks(network);
    }

    private static void printLinks(Network network) {
        System.out.println("===========================================");
        network.calculateShortestPaths("A");
        System.out.println("===========================================");
        network.calculateShortestPaths("B");
        System.out.println("===========================================");
        network.calculateShortestPaths("C");
        System.out.println("===========================================");
        network.calculateShortestPaths("D");

        network.drawGraph();
    }
}
