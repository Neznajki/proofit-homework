package task;

import data.object.IncomingRequest;
import data.object.PolicyObject;
import data.object.PolicySubObject;
import data.object.ResponseObject;

import java.io.InvalidClassException;

public class PremiumCalculator {
    private SumCollector sumCollector = new SumCollector();

    public ResponseObject calculate(IncomingRequest incomingRequest) throws InvalidClassException {
//        List<SumCollector> sumCollectors = new ArrayList<>();// in case we need group by policy objects
        for (PolicyObject policyObject: incomingRequest.policyObjects) {
            if (policyObject.policySubObjects == null) {
                continue;
            }

            addPolicySubObjects(policyObject);
        }

        return new ResponseObject(incomingRequest, this.getSumCollector().calculatePremium());
    }

    protected SumCollector getSumCollector() {
        return sumCollector;
    }

    private void addPolicySubObjects(PolicyObject policyObject) throws InvalidClassException {
        for (PolicySubObject policySubObject: policyObject.policySubObjects) {
            this.getSumCollector().addSubObject(policySubObject);
        }
    }
}
