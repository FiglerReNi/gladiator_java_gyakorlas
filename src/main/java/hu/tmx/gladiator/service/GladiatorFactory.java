package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.*;
import static hu.tmx.gladiator.util.Util.RANDOM;
import static hu.tmx.gladiator.util.Util.randomName;


public class GladiatorFactory {
    public Gladiator generateRandomGladiator(){
       int choice =  RANDOM.nextInt(5);
       Gladiator gladiator;
       switch (choice){
           case 0:
               gladiator = new Brutal(randomName());
               break;
           case 1:
               gladiator = new Assassin(randomName());
               break;
           case 2:
               gladiator = new Archer(randomName());
               break;
           default:
               gladiator = new Swordsman(randomName());
               break;
       }
       return gladiator;
    }
}
