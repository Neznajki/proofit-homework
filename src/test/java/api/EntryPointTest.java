package api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntryPointTest {

    @Test
    public void main() {
        String[] arguments = {"testing"};
//        EntryPoint spy = Mockito.spy(new EntryPoint(arguments[0]));
        Api apiMock = Mockito.mock(Api.class);
//        PowerMockito.whenNew(Api.class).withAnyArguments().thenReturn(apiMock);
        EntryPoint.testInstance = apiMock;
        EntryPoint.main(arguments);
        Mockito.verify(apiMock, Mockito.times(1)).handle();
    }
}