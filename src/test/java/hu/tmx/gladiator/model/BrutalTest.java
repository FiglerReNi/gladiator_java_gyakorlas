package hu.tmx.gladiator.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrutalTest {

    Brutal brutal;

    @BeforeEach
    public void setUp(){
        brutal = new Brutal("Brien");
    }

    @Test
    public void healthMultiplierIsCorrect(){
        assertEquals(1.25, brutal.getHealthMultiplier());
    }

    @Test
    public void strengthMultiplierIsCorrect(){
        assertEquals(1.25, brutal.getStrengthMultiplier());
    }

    @Test
    public void dexterityMultiplierIsCorrect(){
        assertEquals(0.75, brutal.getDexterityMultiplier());
    }

    @AfterEach
    public void tearDown(){
        brutal = null;
    }

}