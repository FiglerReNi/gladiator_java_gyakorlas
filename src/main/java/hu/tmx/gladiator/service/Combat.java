package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.*;

import java.util.HashMap;
import java.util.Map;

import static hu.tmx.gladiator.util.Util.RANDOM;

public class Combat {

    private Map<String, Gladiator> competitors = new HashMap<>();
    private Gladiator winner;
    private boolean hit;

    public Combat(Gladiator gladiatorOne, Gladiator gladiatorTwo) {
        int choice = RANDOM.nextInt(2);
        if (choice == 1) {
            this.competitors.put("attacker", gladiatorOne);
            this.competitors.put("defender", gladiatorTwo);
        } else {
            this.competitors.put("attacker", gladiatorTwo);
            this.competitors.put("defender", gladiatorOne);
        }
        this.simulation();
    }

    public Gladiator getWinner() {
        return this.winner;
    }

    public void setWinner(Gladiator winner) {
        this.winner = winner;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void simulation(){
        while(!this.competitors.get("attacker").isDead() && !this.competitors.get("defender").isDead()) {
            this.hitOrMiss();
            if(this.isHit()){
                System.out.println(this.competitors.get("attacker"));
                System.out.println(this.competitors.get("defender"));
                double range = (double)(RANDOM.nextInt(5)+1)/10;
                int damage = (int)(this.competitors.get("attacker").getStrength() * range);
                this.competitors.get("defender").setCurrentHealth(this.competitors.get("defender").getCurrentHealth() - damage);
                System.out.println(this.competitors.get("attacker").getFullName() + " deals " + damage + " damage");
                System.out.println(this.competitors.get("attacker"));
                System.out.println(this.competitors.get("defender"));
            }else{
                System.out.println(this.competitors.get("attacker").getFullName() + " missed");
            }
            if (this.competitors.get("defender").getCurrentHealth() <= 0) {
                this.competitors.get("defender").setDead(true);
                this.setWinner(this.competitors.get("attacker"));
            }

            if (this.getWinner() == null) {
                Gladiator attacker = this.competitors.get("attacker");
                this.competitors.put("attacker", this.competitors.get("defender"));
                this.competitors.put("defender", attacker);
            }
        }
        System.out.println(this.competitors.get("defender").getFullName() + " died " + this.getWinner().getFullName() + " wins");
    }

    public void hitOrMiss(){
        int percentage = RANDOM.nextInt(100) +1;
        int chanceValue;
        int dexDifference = this.competitors.get("attacker").getDexterity() - this.competitors.get("defender").getDexterity();
        if(dexDifference < 10) {
            chanceValue = 10;
        }else chanceValue = Math.min(dexDifference, 100);
        this.setHit(percentage <= chanceValue);
    }
}
