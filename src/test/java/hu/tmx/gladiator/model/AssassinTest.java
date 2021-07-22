package hu.tmx.gladiator.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssassinTest {

    Assassin assassin;

    @BeforeEach
    public void setUp(){
        assassin = new Assassin("Brien");
    }

    @Test
    public void healthMultiplierIsCorrect(){
        assertEquals(0.75, assassin.getHealthMultiplier());
    }

    @Test
    public void strengthMultiplierIsCorrect(){
        assertEquals(1.25, assassin.getStrengthMultiplier());
    }

    @Test
    public void dexterityMultiplierIsCorrect(){
        assertEquals(1.25, assassin.getDexterityMultiplier());
    }

    @AfterEach
    public void tearDown(){
        assassin = null;
    }
}