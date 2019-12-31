package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.object.ResponseObject;
import json.Formatter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ResponseTest {
    @Mock
    private Response responseMock;
    @Mock
    private ResponseObject responseObjectMock;
    @Mock
    private PrintStream outMock;
    @Mock
    private Formatter formatterMock;

    @Test
    public void dispatchResponse() throws JsonProcessingException {
        String outputMock = "unit tests";
        Mockito.doCallRealMethod().when(responseMock).dispatchResponse();
        Mockito.when(responseMock.getResponse()).thenReturn(responseObjectMock);
        Mockito.when(responseMock.getOut()).thenReturn(outMock);
        Mockito.when(responseMock.getFormatter()).thenReturn(formatterMock);
        Mockito.when(formatterMock.getJsonString(responseObjectMock)).thenReturn(outputMock);

        responseMock.dispatchResponse();

        Mockito.verify(outMock, Mockito.times(1)).println(Mockito.eq(outputMock));
        Mockito.verify(formatterMock, Mockito.times(1)).getJsonString(Mockito.same(responseObjectMock));
    }

    @Test
    public void getResponseException() {
//        StringWriter sw = getStringWriter();
//        this.exception.printStackTrace(new PrintWriter(sw));
//        String exceptionAsString = sw.toString();
//        return String.format("\"%s\"", exceptionAsString);
    }

    @Test
    public void getResponseResponse() {
        ResponseObject responseObjectMock = Mockito.mock(ResponseObject.class);
        Response target = new Response(responseObjectMock);

        Assert.assertSame(responseObjectMock, target.getResponse());
    }

    @Test
    public void getResponseList() {
        List<Response> responses = new ArrayList<>();
        Response target = new Response(responses);

        Assert.assertSame(responses, target.getResponse());
    }
}