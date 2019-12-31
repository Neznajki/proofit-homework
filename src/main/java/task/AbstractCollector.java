package task;

import data.object.PolicySubObject;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCollector {
    protected List<PolicySubObject> policySubObjects = new ArrayList<>();

    public void addSubject(PolicySubObject subject) {
        this.policySubObjects.add(subject);
    }

    public double getTotalPremium() {
        return this.getTotalSum() * this.getCoefficientRate();
    }

    protected double getTotalSum()
    {
        double result = 0;

        for (PolicySubObject subObject: this.policySubObjects) {
            result += subObject.sumInsured;
        }

        return result;
    }

    abstract protected double getCoefficientRate();
}
