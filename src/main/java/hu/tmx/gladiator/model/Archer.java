package hu.tmx.gladiator.model;

public class Archer extends Gladiator {


    public Archer(String name) {
        super(name);
    }

    @Override
    protected double getHealthMultiplier() {
        return StatisticMultiplier.MEDIUM.getValue();
    }

    @Override
    protected double getStrengthMultiplier() {
        return StatisticMultiplier.MEDIUM.getValue();
    }

    @Override
    protected double getDexterityMultiplier() {
        return StatisticMultiplier.HIGH.getValue();
    }
}
