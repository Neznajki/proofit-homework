package task;

import data.object.PolicySubObject;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class WaterCollectorTest {

    @Test
    public void getCoefficientRate() {
        WaterCollector waterCollector = new WaterCollector();
        PolicySubObject policySubObject = new PolicySubObject();
        policySubObject.sumInsured = 9;

        waterCollector.addSubject(policySubObject);
        Assert.assertEquals(0.1, waterCollector.getCoefficientRate(), 0);
        policySubObject = new PolicySubObject();
        policySubObject.sumInsured = 1;
        waterCollector.addSubject(policySubObject);
        Assert.assertEquals(0.05, waterCollector.getCoefficientRate(), 0);
        //        if (this.getTotalSum() >= 10) {
        //            return 0.05;
        //        }
        //        return 0.1;
    }
}