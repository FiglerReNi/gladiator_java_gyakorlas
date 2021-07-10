package hu.tmx.gladiator;

import hu.tmx.gladiator.model.Gladiator;
import hu.tmx.gladiator.service.GladiatorFactory;

public class App {
    public static void main(String[] args) {
        Gladiator one = GladiatorFactory.generateRandomGladiator();
        System.out.println(one.getFullName());
        System.out.println(one.toString());
    }
}
