package hu.tmx.gladiator.model;

import static hu.tmx.gladiator.util.Util.RANDOM;

public abstract class Gladiator {

    private final String name;
    private int level;
    private int health;
    private int strength;
    private int dexterity;
    private int currentHealth;

    public Gladiator(String name) {
        this.name = name;
        this.health = RANDOM.nextInt(76)+25;
        this.strength = RANDOM.nextInt(76)+25;
        this.dexterity = RANDOM.nextInt(76)+25;
        this.level = RANDOM.nextInt(5)+1;
        this.level = this.health;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFullName(){
        return getClass().getSimpleName() + " " + name;
    }

    public void levelUp(){
        setLevel(getLevel()+1);
    }

    protected abstract double getHealthMultiplier();
    protected abstract double getStrengthMultiplier();
    protected abstract double getDexterityMultiplier();

    public int maxHealth(){
        return (int) (health * getHealthMultiplier() * level);
    }

    public int maxStrength(){
        return (int) (strength * getStrengthMultiplier() * level);
    }

    public int maxDexterity(){
        return (int) (dexterity * getDexterityMultiplier() * level);
    }










}
