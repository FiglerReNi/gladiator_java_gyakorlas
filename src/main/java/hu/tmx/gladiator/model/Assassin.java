package hu.tmx.gladiator.model;

public class Assassin extends Gladiator {

    public Assassin(String name) {
        super(name);
    }

    @Override
    protected double getHealthMultiplier() {
        return StatisticMultiplier.LOW.getValue();
    }

    @Override
    protected double getStrengthMultiplier() {
        return StatisticMultiplier.HIGH.getValue();
    }

    @Override
    protected double getDexterityMultiplier() {
        return StatisticMultiplier.HIGH.getValue();
    }
}
