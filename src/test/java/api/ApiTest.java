package api;

import api.factory.ResponseFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import data.object.IncomingRequest;
import helper.Debug;
import helper.FileSystem;
import json.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import task.PremiumCalculator;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * could look better and cleaner but insight into tests can take to much time I would spend on task.
 * no clear reason to do that for a mystic chance of something.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiTest {
    @Mock
    protected Api apiMock;
    @Mock
    protected File fileMock;
    @Mock
    protected Response responseMock;
    @Mock
    protected ResponseFactory responseFactoryMock;
    @Mock
    protected FileSystem fileSystemMock;
    @Mock
    protected PrintStream outMock;
    @Mock
    protected Debug debugMock;
    @Mock
    protected Parser parserMock;
    @Mock
    protected PremiumCalculator premiumCalculatorMock;


    @SuppressWarnings("ConstantConditions")
    @Test
    public void testConstructor() {
        Api api = new Api("unit test");

        Assert.assertTrue(api.getFileSystem() instanceof FileSystem);
        Assert.assertTrue(api.getResponseFactory() instanceof ResponseFactory);
        Assert.assertTrue(api.getOut() instanceof PrintStream);
        Assert.assertTrue(api.getDebug() instanceof Debug);
        Assert.assertTrue(api.getParser() instanceof Parser);
        Assert.assertTrue(api.getCalculator() instanceof PremiumCalculator);
        Assert.assertTrue(api.getCalculator() instanceof PremiumCalculator);
        Assert.assertNotSame(api.getCalculator(), api.getCalculator());//reset for each batched single request

        String filePathMock = "unit test";
        Assert.assertEquals(new File(filePathMock), api.createFile());
    }

    @Before
    public void init() {
        apiMock.filePath = "mocked testing";

        Mockito.when(apiMock.createFile()).thenReturn(fileMock);
        Mockito.when(apiMock.getResponseFactory()).thenReturn(responseFactoryMock);
        Mockito.when(apiMock.getFileSystem()).thenReturn(fileSystemMock);
        Mockito.when(apiMock.getOut()).thenReturn(outMock);
        Mockito.when(apiMock.getDebug()).thenReturn(debugMock);
        Mockito.when(apiMock.getParser()).thenReturn(parserMock);
        Mockito.when(apiMock.getCalculator()).thenReturn(premiumCalculatorMock);
    }

    @Test
    public void handle() throws Exception {
        Mockito.doCallRealMethod().when(apiMock).handle();
        Mockito.when(fileMock.isDirectory()).thenReturn(false);
        Mockito.doReturn(responseMock).when(apiMock).handleFile(Mockito.isA(File.class));
        Mockito.doNothing().when(responseMock).dispatchResponse();

        apiMock.handle();

        Mockito.verify(apiMock, Mockito.times(1)).createFile();
        Mockito.verify(apiMock, Mockito.times(1)).handleFile(Mockito.isA(File.class));
        Mockito.verify(responseMock, Mockito.times(1)).dispatchResponse();
    }

    @Test
    public void handleFolder() throws Exception {
        Mockito.doCallRealMethod().when(apiMock).handle();
        Mockito.when(fileMock.isDirectory()).thenReturn(true);
        Mockito.doReturn(responseMock).when(apiMock).handleDirectory(Mockito.isA(File.class));
        Mockito.doNothing().when(responseMock).dispatchResponse();

        apiMock.handle();

        Mockito.verify(apiMock, Mockito.times(1)).createFile();
        Mockito.verify(apiMock, Mockito.times(1)).handleDirectory(Mockito.isA(File.class));
        Mockito.verify(responseMock, Mockito.times(1)).dispatchResponse();
    }

    @Test
    public void handleExceptionFolder() throws Exception {
        Mockito.doCallRealMethod().when(apiMock).handle();
        Mockito.when(fileMock.isDirectory()).thenReturn(true);
        Mockito.doThrow(new Exception("unit testing")).when(apiMock).handleDirectory(Mockito.isA(File.class));
        Mockito.doReturn(responseMock).when(responseFactoryMock).createResponse(Mockito.isA(Exception.class));
        Mockito.doNothing().when(responseMock).dispatchResponse();

        apiMock.handle();

        Mockito.verify(apiMock, Mockito.times(1)).createFile();
        Mockito.verify(apiMock, Mockito.times(1)).handleDirectory(Mockito.isA(File.class));
        Mockito.verify(responseFactoryMock, Mockito.times(1)).createResponse(Mockito.isA(Exception.class));
        Mockito.verify(responseMock, Mockito.times(1)).dispatchResponse();
        Mockito.verify(debugMock, Mockito.times(1)).handleException(Mockito.isA(Exception.class));
    }

    @Test
    public void handleExceptionFile() throws Exception {
        Mockito.doCallRealMethod().when(apiMock).handle();
        Mockito.when(fileMock.isDirectory()).thenReturn(false);
        Mockito.doThrow(new Exception("unit testing")).when(apiMock).handleFile(Mockito.isA(File.class));
        Mockito.doReturn(responseMock).when(responseFactoryMock).createResponse(Mockito.isA(Exception.class));
        Mockito.doNothing().when(responseMock).dispatchResponse();

        apiMock.handle();

        Mockito.verify(apiMock, Mockito.times(1)).createFile();
        Mockito.verify(apiMock, Mockito.times(1)).handleFile(Mockito.isA(File.class));
        Mockito.verify(responseFactoryMock, Mockito.times(1)).createResponse(Mockito.isA(Exception.class));
        Mockito.verify(responseMock, Mockito.times(1)).dispatchResponse();
        Mockito.verify(debugMock, Mockito.times(1)).handleException(Mockito.isA(Exception.class));
    }

    @Test
    public void handleInvalidExceptionResponse() throws Exception {
        JsonProcessingException processingExceptionMock = Mockito.mock(JsonProcessingException.class);

        Mockito.doCallRealMethod().when(apiMock).handle();
        Mockito.when(fileMock.isDirectory()).thenReturn(false);
        Mockito.doNothing().when(outMock).println(Mockito.isA(String.class));
        Mockito.doThrow(new Exception("unit testing")).when(apiMock).handleFile(Mockito.isA(File.class));
        Mockito.doReturn(responseMock).when(responseFactoryMock).createResponse(Mockito.isA(Exception.class));
        Mockito.doThrow(processingExceptionMock).when(responseMock).dispatchResponse();

        apiMock.handle();

        Mockito.verify(apiMock, Mockito.times(1)).createFile();
        Mockito.verify(apiMock, Mockito.times(1)).handleFile(Mockito.isA(File.class));
        Mockito.verify(responseFactoryMock, Mockito.times(1)).createResponse(Mockito.isA(Exception.class));
        Mockito.verify(outMock, Mockito.times(1)).println(Mockito.isA(String.class));
        Mockito.verify(responseMock, Mockito.times(1)).dispatchResponse();
        Mockito.verify(debugMock, Mockito.times(2)).handleException(Mockito.isA(Exception.class));
    }

    @Test
    public void handleDirectoryException() {
        String folderMock = "unit tests";
        Exception exception = Assert.assertThrows(Exception.class, () -> {
            Mockito.when(apiMock.handleDirectory(fileMock)).thenCallRealMethod();
            Mockito.when(fileMock.getAbsolutePath()).thenReturn(folderMock);

            apiMock.handleDirectory(fileMock);
        });

        Assert.assertEquals(String.format("there are no valid files in folder : %s", folderMock), exception.getMessage());
    }

    @Test
    public void handleDirectory() throws Exception {
        Mockito.when(apiMock.handleDirectory(fileMock)).thenCallRealMethod();
        ArrayList<Response> responses = new ArrayList<>();

        responses.add(Mockito.mock(Response.class));
        responses.add(Mockito.mock(Response.class));
        responses.add(Mockito.mock(Response.class));
        ArrayList<File> foundFiles = new ArrayList<>();

        foundFiles.add(Mockito.mock(File.class));
        foundFiles.add(Mockito.mock(File.class));
        foundFiles.add(Mockito.mock(File.class));
        Exception exception = new Exception("unit testing");

        Mockito.when(fileSystemMock.getAllDirectoryFiles(fileMock, false)).thenReturn(foundFiles);

        Mockito.when(responseFactoryMock.createResponse(exception)).thenReturn(responses.get(1));
        Mockito.when(apiMock.handleFile(foundFiles.get(0))).thenReturn(responses.get(0));
        Mockito.when(apiMock.handleFile(foundFiles.get(1))).thenThrow(exception);
        Mockito.when(apiMock.handleFile(foundFiles.get(2))).thenReturn(responses.get(2));

        apiMock.handleDirectory(fileMock);

        Mockito.verify(responseFactoryMock, Mockito.times(1)).createResponse(Mockito.isA(Exception.class));
        Mockito.verify(responseFactoryMock, Mockito.times(1)).createResponse(Mockito.eq(responses));
        Mockito.verify(debugMock, Mockito.times(1)).handleException(Mockito.isA(Exception.class));
    }

    @Test
    public void handleFileNotJson() {
        String fileNameMock = "unit/test/notJson.php";
        Exception exception = Assert.assertThrows(Exception.class, () -> {
            Mockito.when(apiMock.handleFile(fileMock)).thenCallRealMethod();
            Mockito.when(fileMock.getName()).thenReturn("notJson.php");
            Mockito.when(fileMock.getAbsolutePath()).thenReturn(fileNameMock);

            apiMock.handleFile(fileMock);
        });

        Assert.assertEquals(String.format("file should be json : %s", fileNameMock), exception.getMessage());
    }

    @Test
    public void handleFile() throws Exception {
        IncomingRequest incomingRequestMock = Mockito.mock(IncomingRequest.class);
        Mockito.when(fileMock.getName()).thenReturn("something.json");
        Mockito.when(apiMock.handleFile(fileMock)).thenCallRealMethod();
        Mockito.when(parserMock.readFile(fileMock)).thenReturn(incomingRequestMock);
        Mockito.when(responseFactoryMock.createResponse(incomingRequestMock, premiumCalculatorMock)).thenReturn(responseMock);

        apiMock.handleFile(fileMock);

        Mockito.verify(responseFactoryMock, Mockito.times(1))
               .createResponse(Mockito.same(incomingRequestMock), Mockito.same(premiumCalculatorMock));
    }
}