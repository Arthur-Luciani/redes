package ifsc.edu.br.router;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class RouterVertex {

    private String from;
    private String to;
    private BigInteger distance;

    public RouterVertex(String from, String to, Integer distance) {
        this.from = from;
        this.to = to;
        this.distance = BigInteger.valueOf(distance);
    }
}
