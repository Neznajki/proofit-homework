package api.factory;

import api.Response;
import data.object.IncomingRequest;
import task.PremiumCalculator;

import java.io.InvalidClassException;
import java.util.List;

public class ResponseFactory {

    public Response createResponse(Exception e) {
        return new Response(e);
    }

    public Response createResponse(List<Response> responses) {
        return new Response(responses);
    }

    public Response createResponse(IncomingRequest incomingRequest, PremiumCalculator calculator) throws InvalidClassException {
        return new Response(calculator.calculate(incomingRequest));
    }
}
