package helper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileSystemTest {
    @Mock
    private File fileMock;

    @Test
    public void getAllDirectoryFiles() {
        List<File> expectingResult = new ArrayList<>();

        expectingResult.add(createFreshValidFileMock());
        expectingResult.add(createFreshValidFileMock());
        expectingResult.add(createFreshValidFileMock());

        File[] allFiles = new File[5];

        allFiles[0] = expectingResult.get(0);
        allFiles[1] = Mockito.mock(File.class);
        allFiles[2] = expectingResult.get(1);
        allFiles[4] = Mockito.mock(File.class);
        allFiles[3] = expectingResult.get(2);

        Mockito.when(allFiles[4].isFile()).thenReturn(true);
        Mockito.when(allFiles[4].getName()).thenReturn("notJson.php");

        Mockito.when(fileMock.listFiles()).thenReturn(allFiles);

        FileSystem fileSystem = new FileSystem();
        assertEquals(expectingResult, fileSystem.getAllDirectoryFiles(fileMock));

    }

    private File createFreshValidFileMock() {
        File mock = Mockito.mock(File.class);

        Mockito.when(mock.getName()).thenReturn("something.json");
        Mockito.when(mock.isFile()).thenReturn(true);

        return mock;
    }
}