package task;

public class FireCollector extends AbstractCollector {
    @Override
    protected double getCoefficientRate() {
        if (this.getTotalSum() > 100) {
            return 0.023;
        }
        return 0.013;
    }
}
