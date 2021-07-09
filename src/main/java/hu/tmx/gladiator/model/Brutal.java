package hu.tmx.gladiator.model;

public class Brutal extends Gladiator {

    public Brutal(String name) {
        super(name);
    }

    @Override
    protected double getHealthMultiplier() {
        return StatisticMultiplier.HIGH.getValue();
    }

    @Override
    protected double getStrengthMultiplier() {
        return StatisticMultiplier.HIGH.getValue();
    }

    @Override
    protected double getDexterityMultiplier() {
        return StatisticMultiplier.LOW.getValue();
    }
}
