package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.*;

import java.util.HashMap;
import java.util.Map;

import static hu.tmx.gladiator.util.Util.RANDOM;

public class Combat {

    private Map<String, Gladiator> competitors = new HashMap<>();
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
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public Gladiator simulation(){
        while(!this.competitors.get("attacker").isDead() && !this.competitors.get("defender").isDead()) {
            hitOrMiss();
            if(isHit()){
                System.out.println(this.competitors.get("attacker"));
                System.out.println(this.competitors.get("defender"));
                double range = (double)(RANDOM.nextInt(5)+1)/10;
                int damage = (int)(this.competitors.get("attacker").getStrength() * range);
                this.competitors.get("defender").decreaseHpBy(damage);
                System.out.println(this.competitors.get("attacker").getFullName() + " deals " + damage + " damage");
                System.out.println(this.competitors.get("attacker"));
                System.out.println(this.competitors.get("defender"));
            }else{
                System.out.println(this.competitors.get("attacker").getFullName() + " missed");
            }

            if (!this.competitors.get("defender").isDead()) {
                Gladiator attacker = this.competitors.get("attacker");
                this.competitors.put("attacker", this.competitors.get("defender"));
                this.competitors.put("defender", attacker);
            }
        }
        this.competitors.get("attacker").levelUp();
        this.competitors.get("attacker").healUp();
        System.out.println(this.competitors.get("defender").getFullName() + " died " + this.competitors.get("attacker").getFullName() + " wins");
        return this.competitors.get("attacker");
    }

    private void hitOrMiss(){
        int percentage = RANDOM.nextInt(100) +1;
        int chanceValue;
        int dexDifference = this.competitors.get("attacker").getDexterity() - this.competitors.get("defender").getDexterity();
        if(dexDifference < 10) {
            chanceValue = 10;
        }else chanceValue = Math.min(dexDifference, 100);
        this.setHit(percentage <= chanceValue);
    }
}
