package data.object;

public class ResponseObject {
    public IncomingRequest incomingRequest;
    public double premium = 0;

    public ResponseObject(IncomingRequest incomingRequest)
    {
        this.incomingRequest = incomingRequest;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }
}
