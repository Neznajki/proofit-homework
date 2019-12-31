package task;

import data.object.IncomingRequest;
import data.object.PolicyObject;
import data.object.PolicySubObject;
import data.object.ResponseObject;

import java.io.InvalidClassException;
import java.util.HashMap;

public class PremiumCalculator {
    private SumCollector sumCollector = new SumCollector();
//    private HashMap<policyObject, SumCollector> sumCollectors = new HashMap<>();// in case we need group by policy objects
    //private List<SumCollector> sumCollector = new ArrayList<>();


    public ResponseObject calculate(IncomingRequest incomingRequest) throws InvalidClassException {
        for (PolicyObject policyObject: incomingRequest.policyObjects) {
            if (policyObject.policySubObjects == null) {
                continue;
            }

            addPolicySubObjects(policyObject);
        }

        return new ResponseObject(incomingRequest, this.sumCollector.calculatePremium());
    }

    private void addPolicySubObjects(PolicyObject policyObject) throws InvalidClassException {
        for (PolicySubObject policySubObject: policyObject.policySubObjects) {
            this.sumCollector.addSubObject(policySubObject);
        }
    }
}
