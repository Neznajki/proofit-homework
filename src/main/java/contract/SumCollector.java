package contract;

import data.object.PolicySubObject;

public interface SumCollector {

    void addSubject(PolicySubObject subject);
    double getTotalCoefficient();
}
