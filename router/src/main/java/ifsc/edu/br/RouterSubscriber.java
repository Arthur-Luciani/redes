package ifsc.edu.br;

import java.math.BigInteger;
import java.util.Map;

public interface RouterSubscriber {

    void update(Map<Router1, BigInteger> distanceVector);
}
