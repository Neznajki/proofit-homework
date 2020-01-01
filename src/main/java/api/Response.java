package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.object.ResponseObject;
import json.Formatter;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class Response {
    private boolean error;
    private Exception exception = null;
    private ResponseObject response = null;
    private List<Response> responseList;
    private Formatter formatter = new Formatter();
    private PrintStream out = System.out;

    public Response(Exception exception)
    {
        this.exception = exception;
        this.error = true;
    }

    public Response(ResponseObject response)
    {
        this.response = response;
        this.error = false;
    }

    public Response(List<Response> responseList)
    {
        this.responseList = responseList;
        this.error = false;
    }

    public void dispatchResponse() throws JsonProcessingException {
        this.getOut().println(this.getFormatter().getJsonString(this.getResponse()));
    }

    /**
     * better would be to pass response to the file
     */
    public Object getResponse() {
        if (this.isError()) {
            StringWriter sw = this.getStringWriter();
            this.getException().printStackTrace(new PrintWriter(sw));
            return String.format("\"%s\"", sw.toString());
            //could be some api response in case we need
        } else if (this.response != null) {
            return this.response;
        } else {
            return this.responseList;
        }
    }

    public Exception getException() {
        return exception;
    }

    protected boolean isError() {
        return this.error;
    }

    protected StringWriter getStringWriter() {
        return new StringWriter();
    }

    protected Formatter getFormatter() {
        return formatter;
    }

    protected PrintStream getOut() {
        return out;
    }

}
