package task;

public class WaterCollector extends AbstractCollector {
    @Override
    protected double getCoefficientRate() {
        if (this.getTotalSum() >= 10) {
            return 0.05;
        }
        return 0.1;
    }
}
