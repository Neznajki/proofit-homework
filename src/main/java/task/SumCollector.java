package task;

import data.object.PolicySubObject;

import java.io.InvalidClassException;
import java.util.HashMap;

public class SumCollector {
    /** just to check that there is required sum collectors **/
    protected HashMap<String, AbstractCollector> supportedCollectors = new HashMap<>();

    public SumCollector()
    {
        this.supportedCollectors.put("WATER", new WaterCollector());
        this.supportedCollectors.put("FIRE", new FireCollector());
    }

    public void addSubObject(PolicySubObject policySubObject) throws InvalidClassException {

        String policyName = policySubObject.riskType.toUpperCase().intern();
        for (String supportedType: this.supportedCollectors.keySet()) {
            if (supportedType == policyName) {
                this.supportedCollectors.get(supportedType).addSubject(policySubObject);
                return;
            }
        }

        throw new InvalidClassException(String.format(
                "collector class %s not supported || supported (%s)",
                policySubObject.name,
                String.join(",", this.supportedCollectors.keySet())
        ));
    }

    public double calculatePremium()
    {
        double result = 0;

        for (AbstractCollector collector: this.supportedCollectors.values()) {
            result += collector.getTotalPremium();
        }

        return result;
    }
}
