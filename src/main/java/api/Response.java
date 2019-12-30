package api;

import data.object.ResponseObject;
import json.Formatter;
import staticAccess.Helper;

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

    /**
     * better would be to pass response to the file
     */
    public void dispatchResponse()
    {
        if (this.isError) {
            //could be some api response in case we need
        } else if (this.response != null) {
            dispatchSingleResponse();
        } else {
            System.out.println("[");
            for (Response response: this.responseList) {
                response.dispatchResponse();
            }
            System.out.println("]");
        }
    }

    private void dispatchSingleResponse() {
        try {
            Formatter formatter = new Formatter();

            System.out.println(formatter.getJsonString(this.response));
        } catch (Exception e) {
            Helper.handleException(e);
            Response errorResponse = new Response(e);
            errorResponse.dispatchResponse();
        }
    }
}
