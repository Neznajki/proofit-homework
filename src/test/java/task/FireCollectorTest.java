package task;

import data.object.PolicySubObject;
import org.junit.Assert;
import org.junit.Test;

import javax.security.auth.Subject;

import static org.junit.Assert.*;

public class FireCollectorTest {

    @Test
    public void getCoefficientRate() {
        FireCollector fireCollector = new FireCollector();
        PolicySubObject policySubObject = new PolicySubObject();
        policySubObject.sumInsured = 50;

        fireCollector.addSubject(policySubObject);
        Assert.assertEquals(0.013, fireCollector.getCoefficientRate(), 0);
        fireCollector.addSubject(policySubObject);
        Assert.assertEquals(0.013, fireCollector.getCoefficientRate(), 0);
        policySubObject = new PolicySubObject();
        policySubObject.sumInsured = 1;
        fireCollector.addSubject(policySubObject);
        Assert.assertEquals(0.023, fireCollector.getCoefficientRate(), 0);
        //        if (this.getTotalSum() > 100) {
        //            return 0.023;
        //        }
        //        return 0.013;
    }
}