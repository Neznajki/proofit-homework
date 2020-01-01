package helper;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class DebugTest {

    @Test
    public void handleException() {
        Debug debug = new Debug();
        debug.handleException(Mockito.mock(Exception.class));
    }
}