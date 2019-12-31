package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import data.object.ResponseObject;
import json.Formatter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Response {
    private boolean isError;
    private Exception exception = null;
    private ResponseObject response = null;
    private List<Response> responseList;

    public Response(Exception exception)
    {
        this.exception = exception;
        this.isError = true;
    }

    public Response(ResponseObject response)
    {
        this.response = response;
        this.isError = false;
    }

    public Response(List<Response> responseList)
    {
        this.responseList = responseList;
        this.isError = false;
    }

    public void dispatchResponse() throws JsonProcessingException {
        System.out.println(this.getFormattedResponse(this.getResponse()));
    }

    /**
     * better would be to pass response to the file
     */
    public Object getResponse() {
        if (this.isError) {
            StringWriter sw = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            return String.format("\"%s\"", exceptionAsString);
            //could be some api response in case we need
        } else if (this.response != null) {
            return this.response;
        } else {
            List<Object> responseData = new ArrayList<>();
            for (Response response: this.responseList) {
                responseData.add(response.getResponse());
            }

            return responseData;
        }
    }

    private String getFormattedResponse(Object response) throws JsonProcessingException {
        Formatter formatter = new Formatter();

        return formatter.getJsonString(response);
    }
}
