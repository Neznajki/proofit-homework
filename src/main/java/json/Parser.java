package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.object.IncomingRequest;

import java.io.File;
import java.io.IOException;

public class Parser {
    private ObjectMapper mapper;
    public Parser()
    {
        this.mapper = new ObjectMapper();
    }

    public IncomingRequest readFile(File file) throws IOException {
        IncomingRequest incomingRequest = this.getMapper().readValue(file, IncomingRequest.class);

        if (incomingRequest.premium != 0.00) {
            throw new RuntimeException("no functionality for case where incoming premium is not 0");
        }

        return incomingRequest;
    }

    protected ObjectMapper getMapper() {
        return mapper;
    }
}
