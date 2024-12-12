package ifsc.edu.br.roteador;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Router {
    private final String name;
    private List<Router> neighbors;

    public Router(String name) {
        this.name = name;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(Router neighbor) {
        this.neighbors.add(neighbor);
    }

    public void removeNeighbor(Router neighbor) {
        this.neighbors.remove(neighbor);
    }

    @Override
    public String toString() {
        return name;
    }
}