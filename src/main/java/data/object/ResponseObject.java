package data.object;

public class ResponseObject {
    public IncomingRequest incomingRequest;
    public double premium;

    public ResponseObject(IncomingRequest incomingRequest, double premium)
    {
        this.incomingRequest = incomingRequest;
        this.premium = premium;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }
}
