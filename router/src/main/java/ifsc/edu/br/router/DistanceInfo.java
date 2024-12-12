package ifsc.edu.br.router;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class DistanceInfo {

    private Router using;

    private Router destination;

    private BigInteger distance;
}
