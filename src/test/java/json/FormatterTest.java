package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FormatterTest {
    @Mock
    private Formatter formatterMock;
    @Mock
    private ObjectMapper mapperMock;
    @Mock
    private ObjectWriter objectWriterMock;

    @Test
    public void constructorTest()
    {
        Formatter formatter = new Formatter();

        MatcherAssert.assertThat(formatter.getMapper(), new IsInstanceOf(ObjectMapper.class));
    }

    @Test
    public void getJsonString() throws JsonProcessingException {
        String objectMock = "unit tests", expectingResult = "result for function";
        Mockito.when(formatterMock.getJsonString(objectMock)).thenCallRealMethod();
        Mockito.when(formatterMock.getMapper()).thenReturn(mapperMock);
        Mockito.when(mapperMock.writerWithDefaultPrettyPrinter()).thenReturn(objectWriterMock);
        Mockito.when(objectWriterMock.writeValueAsString(objectMock)).thenReturn(expectingResult);

        Assert.assertEquals(expectingResult, formatterMock.getJsonString(objectMock));

        //this.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data)
    }
}