package task;

import data.object.PolicySubObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.InvalidClassException;

public class SumCollectorTest {

    /**
     * I can't see reason to separate tests
     */
    @Test
    public void fullSumCollectorTestWithDependency() throws InvalidClassException {
        SumCollector sumCollector = new SumCollector();
        PolicySubObject
                waterPolicySubObject = new PolicySubObject(),
                firePolicySubObject = new PolicySubObject(),
                fireUpperPolicySubObject = new PolicySubObject();
        waterPolicySubObject.riskType = "water";
        waterPolicySubObject.sumInsured = 5;
        firePolicySubObject.riskType = "fire";
        firePolicySubObject.sumInsured = 5;
        fireUpperPolicySubObject.riskType = "FIRE";
        fireUpperPolicySubObject.sumInsured = 10;

        sumCollector.addSubObject(waterPolicySubObject);
        sumCollector.addSubObject(firePolicySubObject);
        sumCollector.addSubObject(fireUpperPolicySubObject);

        Assert.assertEquals(5 * 0.1 + 15 * 0.013, sumCollector.calculatePremium(), 0);
    }

    @Test
    public void notSupportedType()
    {
        Assert.assertThrows(InvalidClassException.class, () -> {
            SumCollector sumCollector = new SumCollector();
            PolicySubObject policySubObject = new PolicySubObject();
            policySubObject.riskType = "not supported";
            sumCollector.addSubObject(policySubObject);
        });
    }

    @Test
    public void testSupportedTypes()
    {
        SumCollector sumCollector = new SumCollector();

        Assert.assertEquals(sumCollector.supportedCollectors.size(), 2,0);
        Assert.assertTrue(sumCollector.supportedCollectors.get("WATER") instanceof WaterCollector);
        Assert.assertTrue(sumCollector.supportedCollectors.get("FIRE") instanceof FireCollector);
    }
}