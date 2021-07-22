package hu.tmx.gladiator.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwordsmanTest {

    Swordsman swordsman;

    @BeforeEach
    public void setUp(){
        swordsman = new Swordsman("Brien");
    }

    @Test
    public void healthMultiplierIsCorrect(){
        assertEquals(1.0, swordsman.getHealthMultiplier());
    }

    @Test
    public void strengthMultiplierIsCorrect(){
        assertEquals(1.0, swordsman.getStrengthMultiplier());
    }

    @Test
    public void dexterityMultiplierIsCorrect(){
        assertEquals(1.0, swordsman.getDexterityMultiplier());
    }

    @AfterEach
    public void tearDown(){
        swordsman = null;
    }

}