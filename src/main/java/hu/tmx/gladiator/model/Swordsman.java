package hu.tmx.gladiator.model;

public class Swordsman extends Gladiator {

    public Swordsman(String name) {
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
        return StatisticMultiplier.MEDIUM.getValue();
    }
}
