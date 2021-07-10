package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.*;

import java.util.Map;

import static hu.tmx.gladiator.util.Util.RANDOM;

public class Combat {

    private  Map<String, Gladiator> competitors;
    private Gladiator winner;
    private boolean hit;

    public Combat(Gladiator gladiatorOne,Gladiator gladiatorTwo) {
        int choice = RANDOM.nextInt(2);
        if (choice == 1) {
            this.competitors.put("attacker", gladiatorOne);
            this.competitors.put("defender", gladiatorTwo);
        } else {
            this.competitors.put("attacker", gladiatorTwo);
            this.competitors.put("defender", gladiatorOne);
        }
    }

    public Gladiator getWinner() {
        return this.winner;
    }

    public void setWinner(Gladiator winner) {
        this.winner = winner;
    }

    public void simulation(){
        //csata
        while(!this.competitors.get("attacker").isDead() && !this.competitors.get("defender").isDead()) {
            //every attack
            hitOrMiss();


            if (this.competitors.get("attacker").getCurrentHealth() <= 0)
                this.competitors.get("attacker").setDead(true);
            if (this.competitors.get("defender").getCurrentHealth() <= 0)
                this.competitors.get("defender").setDead(true);


            //after attack
            if (this.competitors.get("attacker").isDead() || this.competitors.get("defender").isDead()) {
                setWinner(setWinnerGladiator());
            }
            if (getWinner() == null) {
                Gladiator attacker = this.competitors.get("attacker");
                this.competitors.put("attacker", this.competitors.get("defender"));
                this.competitors.put("defender", attacker);
            }
        }
    }

    public void hitOrMiss(){
        int percentage = RANDOM.nextInt(100) +1;
        int chanceValue;
        int dexDifference = this.competitors.get("attacker").getDexterity() - this.competitors.get("defender").getDexterity();
        if(dexDifference < 10) {
            chanceValue = 10;
        }else chanceValue = Math.min(dexDifference, 100);
        this.hit = (percentage <= chanceValue);
    }

    public Gladiator setWinnerGladiator(){
        if(!this.competitors.get("attacker").isDead())
            return this.competitors.get("attacker");
        else if(!this.competitors.get("defender").isDead())
            return this.competitors.get("defender");
        else
            return null;
    }
}
