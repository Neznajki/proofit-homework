package task;

import data.object.IncomingRequest;
import data.object.PolicyObject;
import data.object.PolicySubObject;
import data.object.ResponseObject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InvalidClassException;

@RunWith(MockitoJUnitRunner.class)
public class PremiumCalculatorTest {
    @Mock
    private SumCollector sumCollectorMock;
    @Mock
    private PremiumCalculator premiumCalculatorMock;

    @Test
    public void constructorTest()
    {
        PremiumCalculator premiumCalculator = new PremiumCalculator();


        MatcherAssert.assertThat(premiumCalculator.getSumCollector(), new IsInstanceOf(SumCollector.class));
    }

    @Test
    public void calculate() throws InvalidClassException {
        IncomingRequest incomingRequest = new IncomingRequest();
        PolicySubObject[] policySubObjects = {new PolicySubObject(),new PolicySubObject(),new PolicySubObject(),new PolicySubObject(),new PolicySubObject()};
        incomingRequest.policyObjects = new PolicyObject[3];
        incomingRequest.policyObjects[0] = new PolicyObject();
        incomingRequest.policyObjects[1] = new PolicyObject();
        incomingRequest.policyObjects[2] = new PolicyObject();

        incomingRequest.policyObjects[0].policySubObjects = new PolicySubObject[2];
        incomingRequest.policyObjects[0].policySubObjects[0] = policySubObjects[0];
        incomingRequest.policyObjects[0].policySubObjects[1] = policySubObjects[1];
        incomingRequest.policyObjects[2].policySubObjects = new PolicySubObject[3];
        incomingRequest.policyObjects[2].policySubObjects[0] = policySubObjects[2];
        incomingRequest.policyObjects[2].policySubObjects[1] = policySubObjects[3];
        incomingRequest.policyObjects[2].policySubObjects[2] = policySubObjects[4];

        Mockito.when(premiumCalculatorMock.calculate(incomingRequest)).thenCallRealMethod();
        Mockito.when(premiumCalculatorMock.getSumCollector()).thenReturn(sumCollectorMock);
        double premium = 0.1235;
        Mockito.when(sumCollectorMock.calculatePremium()).thenReturn(premium);
        ResponseObject expectingResponse = new ResponseObject(incomingRequest, premium);

        ResponseObject response = premiumCalculatorMock.calculate(incomingRequest);
        Assert.assertEquals(expectingResponse.premium, response.premium, 0);
        Assert.assertEquals(expectingResponse.incomingRequest, response.incomingRequest);
        Mockito.verify(sumCollectorMock, Mockito.times(5)).addSubObject(Mockito.isA(PolicySubObject.class));
    }
}