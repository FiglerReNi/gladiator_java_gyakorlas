package hu.tmx.gladiator.model;

public enum StatisticMultiplier {
    LOW(0.75),
    MEDIUM(1.0),
    HIGH(1.25);

    private final double value;

    StatisticMultiplier(double value){
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
