package hu.tmx.gladiator;

import hu.tmx.gladiator.model.Gladiator;
import hu.tmx.gladiator.service.Combat;
import hu.tmx.gladiator.service.GladiatorFactory;

public class App {
    public static void main(String[] args) {
        Gladiator one = GladiatorFactory.generateRandomGladiator();
        Gladiator two = GladiatorFactory.generateRandomGladiator();
        new Combat(one, two);
    }
}
