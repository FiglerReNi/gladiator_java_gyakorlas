package hu.tmx.gladiator.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static hu.tmx.gladiator.util.Util.RANDOM;

public class WeaponEffectSystem {

    private static final double BLEEDING_DAMAGE = 0.02;
    private static final double POISON_BURNING_DAMAGE = 0.05;
    private WeaponType weaponType;
    private int bleeading;
    private int poisoned;
    private int turns;

    public WeaponEffectSystem() {
        this.bleeading = 0;
        this.poisoned =0;
        this.turns = 0;
        chooseWeaponType();
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public double bleeding(int currentHealth){
        if((RANDOM.nextInt(100)+1) <= 5){
            bleeading++;
            return (currentHealth * BLEEDING_DAMAGE * this.bleeading);
        }else{
            if(bleeading != 0) {
                return (currentHealth * BLEEDING_DAMAGE * this.bleeading);
            }
        }
        return currentHealth;
    }

    public double poison(int currentHealth){
        if((RANDOM.nextInt(100)+1) <= 20){
            poisoned++;
            if(poisoned > 1){
                System.out.println(0);
                return 0;
            }
            return (currentHealth * POISON_BURNING_DAMAGE);
        }else{
            if(poisoned != 0 && turns < 3){
                turns++;
                return (currentHealth * POISON_BURNING_DAMAGE);
            }
            if(turns == 3) {
                turns = 0;
            }
            return currentHealth;
        }
    }

    public double burning(int currentHealth){
        if((RANDOM.nextInt(100)+1) <= 15){
            turns = turns + (RANDOM.nextInt(5)+1);
            return (currentHealth * POISON_BURNING_DAMAGE);
        }else{
            if(turns != 0){
                turns = turns - 1;
                return (currentHealth * POISON_BURNING_DAMAGE);
            }
            return 0;
        }
    }

    public boolean paralyzing(){
        if((RANDOM.nextInt(100)+1) <= 10){
            turns = 4;
            return true;
        }else{
            if(turns > 0){
                turns = turns - 1;
            }
            return (turns != 0);
        }
    }

    private void chooseWeaponType(){
        if((RANDOM.nextInt(100)+1) <=10){
            List<WeaponType> values = Collections.unmodifiableList(Arrays.asList(WeaponType.values()));
            this.weaponType = values.get(RANDOM.nextInt(values.size()));
        }
    }
}
