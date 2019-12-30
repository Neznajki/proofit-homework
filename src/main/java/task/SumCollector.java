package task;

import data.object.PolicySubObject;

import java.io.InvalidClassException;
import java.util.HashMap;

public class SumCollector {
    private HashMap<String, AbstractCollector> supportedCollectors = new HashMap<>();

    public SumCollector()
    {
        this.supportedCollectors.put("WATER", new WaterCollector());
        this.supportedCollectors.put("FIRE", new FireCollector());
    }

    public void addSubObject(PolicySubObject policySubObject) throws InvalidClassException {

        String policyName = policySubObject.name.toUpperCase().intern();
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
}
