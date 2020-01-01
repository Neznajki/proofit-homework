package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.object.IncomingRequest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class ParserTest {
    @Mock
    private Parser parserMock;
    @Mock
    private ObjectMapper mapperMock;
    @Mock
    private File fileMock;

    @Test
    public void constructorTest()
    {
        Parser parser = new Parser();

        MatcherAssert.assertThat(parser.getMapper(), new IsInstanceOf(ObjectMapper.class));

    }

    @Test
    public void readFile() throws IOException {
        IncomingRequest expectingResult = Mockito.mock(IncomingRequest.class);
        expectingResult.premium = 0;
        Mockito.when(parserMock.readFile(fileMock)).thenCallRealMethod();
        Mockito.when(parserMock.getMapper()).thenReturn(mapperMock);
        Mockito.when(mapperMock.readValue(fileMock, IncomingRequest.class)).thenReturn(expectingResult);

        Assert.assertSame(expectingResult, parserMock.readFile(fileMock));
        //        return this.getMapper().readValue(file, IncomingRequest.class);
    }

    @Test
    public void readFileException() {
        Exception exception = Assert.assertThrows(Exception.class, () -> {
            IncomingRequest expectingResult = Mockito.mock(IncomingRequest.class);
            expectingResult.premium = 1;
            Mockito.when(parserMock.readFile(fileMock)).thenCallRealMethod();
            Mockito.when(parserMock.getMapper()).thenReturn(mapperMock);
            Mockito.when(mapperMock.readValue(fileMock, IncomingRequest.class)).thenReturn(expectingResult);

            Assert.assertSame(expectingResult, parserMock.readFile(fileMock));
        });

        Assert.assertEquals("no functionality for case where incoming premium is not 0", exception.getMessage());
    }
}