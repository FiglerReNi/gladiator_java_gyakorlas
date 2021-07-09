package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.*;

import static hu.tmx.gladiator.util.Util.RANDOM;


public class GladiatorFactory {
    public Gladiator generateRandomGladiator(){
       int choice =  RANDOM.nextInt(5);
       Gladiator gladiator;
       switch (choice){
           case 0:
               gladiator = new Brutal("Brutus");
               break;
           case 1:
               gladiator = new Assassin("Géza");
               break;
           case 2:
               gladiator = new Archer("Leo");
               break;
           default:
               gladiator = new Swordsman("Józsi");
               break;
       }
       return gladiator;
    }
}
