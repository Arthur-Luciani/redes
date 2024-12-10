package ifsc.edu.br.finalrouter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class Node {

    private Router using;
    private Router to;
    private BigInteger distance;
}
