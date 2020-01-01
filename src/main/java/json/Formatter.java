package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Formatter {
    private ObjectMapper mapper;

    public Formatter()
    {
        this.mapper = new ObjectMapper();
    }

    public String getJsonString(Object data) throws JsonProcessingException {

        return this.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data);
    }

    protected ObjectMapper getMapper() {
        return mapper;
    }
}
