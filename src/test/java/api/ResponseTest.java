package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.object.ResponseObject;
import json.Formatter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsSame;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
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
    @Mock
    private StringWriter stringWriterMock;
    @Mock
    private Exception exceptionMock;

    @Test
    public void constructorTest()
    {
        Exception exception = Mockito.mock(Exception.class);
        Response response = new Response(exception);
        Assert.assertSame(exception, response.getException());

        MatcherAssert.assertThat(response.getStringWriter(), new IsInstanceOf(StringWriter.class));
        Assert.assertSame(System.out, response.getOut());
        MatcherAssert.assertThat(response.getFormatter(), new IsInstanceOf(Formatter.class));
    }

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
        Mockito.when(responseMock.getResponse()).thenCallRealMethod();
        Mockito.when(responseMock.isError()).thenReturn(true);
        Mockito.when(responseMock.getStringWriter()).thenReturn(stringWriterMock);
        Mockito.when(responseMock.getException()).thenReturn(exceptionMock);
        Mockito.doNothing().when(exceptionMock).printStackTrace(Mockito.eq(new PrintWriter(stringWriterMock)));
        String messageMocked = "unit tests";
        Mockito.when(stringWriterMock.toString()).thenReturn(messageMocked);

        Assert.assertEquals(String.format("\"%s\"", messageMocked), responseMock.getResponse());
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