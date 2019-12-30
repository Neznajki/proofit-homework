package task;

import contract.SumCollector;
import data.object.PolicySubObject;

import java.util.List;

public abstract class AbstractCollector implements SumCollector {
    protected List<PolicySubObject> policySubObjects;

    @Override
    public void addSubject(PolicySubObject subject) {
        this.policySubObjects.add(subject);
    }

    @Override
    public double getTotalCoefficient() {
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
