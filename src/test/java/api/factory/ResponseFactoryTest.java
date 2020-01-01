package api.factory;

import api.Response;
import data.object.IncomingRequest;
import data.object.ResponseObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import task.PremiumCalculator;

import java.io.InvalidClassException;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ResponseFactoryTest {
    @Mock
    private Exception exceptionMock;
    @Mock
    private List<Response> responseListMock;
    @Mock
    private IncomingRequest incomingRequestMock;
    @Mock
    private PremiumCalculator premiumCalculatorMock;
    @Mock
    private ResponseObject responseObjectMock;

    @Test
    public void createResponse() {
        ResponseFactory factory = new ResponseFactory();
        Assert.assertEquals(exceptionMock, factory.createResponse(exceptionMock).getException());
    }

    @Test
    public void createResponseFromList() {
        ResponseFactory factory = new ResponseFactory();
        Assert.assertEquals(responseListMock, factory.createResponse(responseListMock).getResponse());
    }

    @Test
    public void createResponseFromIncomingRequest() throws InvalidClassException {
        Mockito.when(premiumCalculatorMock.calculate(incomingRequestMock)).thenReturn(responseObjectMock);
        ResponseFactory factory = new ResponseFactory();
        Assert.assertEquals(responseObjectMock, factory.createResponse(incomingRequestMock, premiumCalculatorMock).getResponse());
    }
}