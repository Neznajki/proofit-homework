package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.object.ResponseObject;
import json.Formatter;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class Response {
    private boolean isError;
    private Exception exception = null;
    private ResponseObject response = null;
    private List<Response> responseList;
    private Formatter formatter;
    private PrintStream out;

    public Response(Exception exception)
    {
        this.exception = exception;
        initDependency(true);
    }

    public Response(ResponseObject response)
    {
        this.response = response;
        initDependency(false);
    }

    public Response(List<Response> responseList)
    {
        this.responseList = responseList;
        initDependency(false);
    }

    public void dispatchResponse() throws JsonProcessingException {
        this.getOut().println(this.getFormatter().getJsonString(this.getResponse()));
    }

    /**
     * better would be to pass response to the file
     */
    public Object getResponse() {
        if (this.isError) {
            StringWriter sw = getStringWriter();
            this.exception.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return String.format("\"%s\"", exceptionAsString);
            //could be some api response in case we need
        } else if (this.response != null) {
            return this.response;
        } else {
            return this.responseList;
        }
    }

    private StringWriter getStringWriter() {
        return new StringWriter();
    }

    protected Formatter getFormatter() {
        return formatter;
    }

    protected PrintStream getOut() {
        return out;
    }

    protected void initDependency(boolean isError) {
        this.formatter = new Formatter();
        this.isError = isError;
        this.out = System.out;
    }
}
