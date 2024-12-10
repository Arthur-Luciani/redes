package ifsc.edu.br;

import lombok.Getter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Router2 {

    @Getter
    private String name;

    @Getter
    private Map<Router2, BigInteger> distanceVector;

    private Map<Router2, RouterEnum> neighbors;

    public Router2(String name) {
        this.name = name;
        this.distanceVector = new HashMap<>(Map.of(this, BigInteger.ZERO));
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Router2 neighbor, BigInteger distance) {
        neighbor.addReceiveNeighbor(this);
        neighbors.put(neighbor, RouterEnum.SEND);
        distanceVector.put(neighbor, distance);
    }

    private void addReceiveNeighbor(Router2 neighbor) {
        neighbors.put(neighbor, RouterEnum.RECEIVE);
    }

    //update method based on bellman ford algorithm using distance vector
    public void update(Map<Router2, Integer> receivedDistanceVector) {
        boolean updated = false;



        if (updated) {
            //notifyNeighbors();
        }
    }





}
