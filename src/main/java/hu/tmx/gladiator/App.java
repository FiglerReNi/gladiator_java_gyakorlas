package hu.tmx.gladiator;

import hu.tmx.gladiator.model.Gladiator;
import hu.tmx.gladiator.service.Combat;
import hu.tmx.gladiator.service.GladiatorFactory;
import hu.tmx.gladiator.util.Util;

import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) {

        int competitorsNumber = (int) Math.pow(2,(Util.nextInt(4) + 1));
        List<Gladiator> competitors = new ArrayList<>();
        Combat combat = new Combat();

        for(int i = 0; i < competitorsNumber; i++){
            competitors.add(GladiatorFactory.generateRandomGladiator());
        }

        while (competitors.size() != 1){
            System.out.println("-----------------------------------\n");
            List<Gladiator> temp = new ArrayList<>(competitors);
            competitors.clear();
            for(int i = 0; i < temp.size(); i = i + 2){
                competitors.add(combat.simulation(temp.get(i), temp.get(i+1)));
                System.out.println("\n");
            }
        }
        System.out.println("Final winner: " + competitors.get(0).getFullName());
    }
}
