package hu.tmx.gladiator.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static hu.tmx.gladiator.util.Util.RANDOM;

public abstract class Gladiator{

    private static final double BLEEDING_DAMAGE = 0.02;
    private static final double POISON_BURNING_DAMAGE = 0.05;
    private final String name;
    private int level;
    private int health;
    private int strength;
    private int dexterity;
    private int currentHealth;
    private WeaponType weaponType;
    private boolean paralyzed;
    private int bleeding;
    private int poisoned;
    private int turns;
    private WeaponEffectSystem weaponEffectSystem;

    public Gladiator(String name) {
        this.name = name;
        this.health = RANDOM.nextInt(76)+25;
        this.strength = RANDOM.nextInt(76)+25;
        this.dexterity = RANDOM.nextInt(76)+25;
        this.level = RANDOM.nextInt(5)+1;
        this.currentHealth = this.health;
        this.chooseWeaponType();
        this.paralyzed = false;
        this.bleeding = 0;
        this.poisoned =0;
        this.turns = 0;
        this.weaponEffectSystem = new WeaponEffectSystem();
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public boolean isDead() { return currentHealth <= 0; }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public WeaponEffectSystem getWeaponEffectSystem() {
        return weaponEffectSystem;
    }

    public boolean isParalyzed() {
        return paralyzed;
    }

    public void setParalyzed(boolean paralyzed) {
        this.paralyzed = paralyzed;
    }

    public String getFullName(){
        return getClass().getSimpleName() + " " + getName();
    }

    public void levelUp(){
        setLevel(getLevel() + 1);
        setHealth(maxHealth());
        setStrength(maxStrength());
        setDexterity(maxDexterity());
    }

    protected abstract double getHealthMultiplier();
    protected abstract double getStrengthMultiplier();
    protected abstract double getDexterityMultiplier();

    private int maxHealth(){
        return (int) (getHealth() * getHealthMultiplier() * getLevel());
    }

    private int maxStrength(){
        return (int) (getStrength() * getStrengthMultiplier() * getLevel());
    }

    private int maxDexterity(){
        return (int) (getDexterity() * getDexterityMultiplier() * getLevel());
    }

    public void decreaseHpBy(int damage){
        setCurrentHealth(getCurrentHealth()-damage);
    }

    public void healUp(){
        setCurrentHealth(getHealth());
    }

    private void chooseWeaponType(){
        if((RANDOM.nextInt(100)+1) <=10){
            List<WeaponType> values = Collections.unmodifiableList(Arrays.asList(WeaponType.values()));
            this.weaponType = values.get(RANDOM.nextInt(values.size()-1));
        }else{
            this.weaponType = WeaponType.NORMAL;
        }
    }

    @Override
    public String toString() {
        return "Gladiator{" +
                "name='" + getFullName() + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", strength=" + strength +
                ", dexterity=" + dexterity +
                ", currentHealth=" + currentHealth +
                ", weapontype=" + weaponEffectSystem.getWeaponType() +
                '}';
    }
}
