package data.object;

public class ResponseObject {
    public IncomingRequest incomingRequest;
    public double premium;

    public ResponseObject(IncomingRequest incomingRequest, double premium)
    {
        this.incomingRequest = incomingRequest;
        this.premium = premium;
    }
}
