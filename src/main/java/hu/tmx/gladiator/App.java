package hu.tmx.gladiator;

import hu.tmx.gladiator.model.Gladiator;
import hu.tmx.gladiator.service.GladiatorFactory;

public class App {
    public static void main(String[] args) {
        GladiatorFactory gladiatorFactory = new GladiatorFactory();
        Gladiator one = gladiatorFactory.generateRandomGladiator();
        System.out.println(one.getFullName());
    }
}
