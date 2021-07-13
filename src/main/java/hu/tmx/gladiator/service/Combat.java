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

    public Gladiator simulation() {
        if (competitors.get("attacker") == null && competitors.get("defender") == null)
            return null;
        else if (competitors.get("attacker") == null)
            return competitors.get("defender");
        else if (competitors.get("defender") == null)
            return competitors.get("attacker");

        System.out.println(competitors.get("attacker").toString() + "-" + competitors.get("defender").toString());
        while (!competitors.get("attacker").isDead() && !competitors.get("defender").isDead()) {
            hitOrMiss();
            if (!competitors.get("defender").isParalyzed()) {
                if (isHit()) {
                    attack();
                } else {
                    System.out.println(competitors.get("attacker").getFullName() + " missed");
                }
            } else {
                attack();
            }

            if (!competitors.get("defender").isDead() && !competitors.get("defender").isParalyzed()) {
                Gladiator attacker = competitors.get("attacker");
                competitors.put("attacker", competitors.get("defender"));
                competitors.put("defender", attacker);
            }
        }

        competitors.get("attacker").levelUp();
        competitors.get("attacker").healUp();
        System.out.println(competitors.get("defender").getFullName() + " died " + competitors.get("attacker").getFullName() + " wins");
        return competitors.get("attacker");
    }

    private void hitOrMiss() {
        int percentage = RANDOM.nextInt(100) + 1;
        int chanceValue;
        int dexDifference = competitors.get("attacker").getDexterity() - competitors.get("defender").getDexterity();
        if (dexDifference < 10) {
            chanceValue = 10;
        } else chanceValue = Math.min(dexDifference, 100);
        this.setHit(percentage <= chanceValue);
    }

    private double plusDamage(int defenderCurrentHealth) {
        switch (competitors.get("attacker").getWeaponType()) {
            case BLEEDING:
                return this.bleeding(defenderCurrentHealth);
            case POISON:
                return this.poison(defenderCurrentHealth);
            case BURNING:
                return this.burning(defenderCurrentHealth);
            case PARALYZING:
                competitors.get("defender").setParalyzed(paralyzing());
                break;
        }
        return 0;
    }

    private void attack() {
        double range = (double) (RANDOM.nextInt(5) + 1) / 10;
        double plusDamage = plusDamage(competitors.get("defender").getCurrentHealth());
        int damage = (int) ((competitors.get("attacker").getStrength() * range) + plusDamage);
        competitors.get("defender").decreaseHpBy(damage);
        System.out.println(competitors.get("attacker").getFullName() + " deals " + damage + " damage");
    }

    private double bleeding(int currentHealth) {
        if ((RANDOM.nextInt(100) + 1) <= 5) {
            competitors.get("attacker").setBleeding(competitors.get("attacker").getBleeding() + 1);
            return (currentHealth * Gladiator.BLEEDING_DAMAGE * competitors.get("attacker").getBleeding());
        } else {
            if (competitors.get("attacker").getBleeding() != 0) {
                return (currentHealth * Gladiator.BLEEDING_DAMAGE * competitors.get("attacker").getBleeding());
            }
        }
        return 0;
    }

    private double poison(int currentHealth) {
        if ((RANDOM.nextInt(100) + 1) <= 20) {
            competitors.get("attacker").setPoisoned(competitors.get("attacker").getPoisoned() + 1);
            if (competitors.get("attacker").getPoisoned() > 1) {
                return 0;
            }
            return (currentHealth * Gladiator.POISON_BURNING_DAMAGE);
        } else {
            if (competitors.get("attacker").getPoisoned() != 0 && competitors.get("attacker").getTurns() < 3) {
                competitors.get("attacker").setPoisoned(competitors.get("attacker").getPoisoned() + 1);
                return (currentHealth * Gladiator.POISON_BURNING_DAMAGE);
            }
            if (competitors.get("attacker").getTurns() == 3) {
                competitors.get("attacker").setPoisoned(0);
            }
            return 0;
        }
    }

    private double burning(int currentHealth) {
        if ((RANDOM.nextInt(100) + 1) <= 15) {
            competitors.get("attacker").setTurns(competitors.get("attacker").getTurns() + (RANDOM.nextInt(5) + 1));
            return (currentHealth * Gladiator.POISON_BURNING_DAMAGE);
        } else {
            if (competitors.get("attacker").getTurns() != 0) {
                competitors.get("attacker").setTurns(competitors.get("attacker").getTurns() - 1);
                return (currentHealth * Gladiator.POISON_BURNING_DAMAGE);
            }
            return 0;
        }
    }

    private boolean paralyzing() {
        if ((RANDOM.nextInt(100) + 1) <= 10) {
            competitors.get("attacker").setTurns(4);
            return true;
        } else {
            if (competitors.get("attacker").getTurns() > 0) {
                competitors.get("attacker").setTurns(competitors.get("attacker").getTurns() - 1);
            }
            return (competitors.get("attacker").getTurns() != 0);
        }
    }
}
