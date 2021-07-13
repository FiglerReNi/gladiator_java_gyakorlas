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
        if(this.competitors.get("attacker") == null && this.competitors.get("defender") == null)
                return null;
        else if(this.competitors.get("attacker") == null)
                return this.competitors.get("defender");
        else if(this.competitors.get("defender") == null)
                return this.competitors.get("attacker");

        System.out.println(this.competitors.get("attacker").toString() + "-" + this.competitors.get("defender").toString());
        while(!this.competitors.get("attacker").isDead() && !this.competitors.get("defender").isDead()) {
            hitOrMiss();
            if(!this.competitors.get("defender").isParalized()) {
                if (isHit()) {
                    attack();
                } else {
                    System.out.println(this.competitors.get("attacker").getFullName() + " missed");
                }
            }else{
               attack();
            }

            if (!this.competitors.get("defender").isDead() && !this.competitors.get("defender").isParalized()) {
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

    private double plusDamage(int defenderCurrentHealth) {
        WeaponType weaponType = this.competitors.get("attacker").getWeaponEffectSystem().getWeaponType();
        if(weaponType != null) {
            switch (weaponType) {
                case BLEEDING:
                    return this.competitors.get("attacker").getWeaponEffectSystem().bleeding(defenderCurrentHealth);
                case POISON:
                    return this.competitors.get("attacker").getWeaponEffectSystem().poison(defenderCurrentHealth);
                case BURNING:
                    return this.competitors.get("attacker").getWeaponEffectSystem().burning(defenderCurrentHealth);
                case PARALYZING:
                    this.competitors.get("defender").setParalized(this.competitors.get("attacker").getWeaponEffectSystem().paralyzing());
                    break;
            }
        }
        return 0;
    }

    private void attack(){
        double range = (double) (RANDOM.nextInt(5) + 1) / 10;
        double plusDamage = plusDamage(this.competitors.get("defender").getCurrentHealth());
        int damage = (int) ((this.competitors.get("attacker").getStrength() * range) + plusDamage);
        this.competitors.get("defender").decreaseHpBy(damage);
        System.out.println(this.competitors.get("attacker").getFullName() + " deals " + damage + " damage");
    }
}
