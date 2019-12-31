package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.object.IncomingRequest;

import java.io.File;
import java.io.IOException;

public class Parser {
    public IncomingRequest readFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, IncomingRequest.class);
    }
}
