package hu.tmx.gladiator;

import hu.tmx.gladiator.model.Gladiator;
import hu.tmx.gladiator.service.Combat;
import hu.tmx.gladiator.service.GladiatorFactory;

import java.util.ArrayList;
import java.util.List;

import static hu.tmx.gladiator.util.Util.RANDOM;

public class App {
    public static void main(String[] args) {

        int competitorsNumber = (int) Math.pow(2,(RANDOM.nextInt(4) + 1));
        List<Gladiator[]> competitors = new ArrayList<>();
        List<Gladiator> winners = new ArrayList<>();
        for(int i = 0; i < competitorsNumber/2; i++){
            competitors.add(new Gladiator[]{GladiatorFactory.generateRandomGladiator(), GladiatorFactory.generateRandomGladiator()});
        }

        for(Gladiator[] combatParticipants: competitors){
            Combat combat = new Combat(combatParticipants[0], combatParticipants[1]);
            winners.add(combat.simulation());
            System.out.println("----------------- END -------------------");
        }
    }
}
