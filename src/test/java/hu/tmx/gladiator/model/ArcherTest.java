package hu.tmx.gladiator.model;

import hu.tmx.gladiator.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.fest.reflect.core.Reflection.method;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

class ArcherTest {

    Archer archer;

    @BeforeEach
    public void setUp(){
        archer = new Archer("Brien");
    }

    //Konstruktor teszt példa
    @Test
    public void constructorWorksCorrect(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(76)).thenReturn(25);
            mocked.when(() -> Util.nextInt(5)).thenReturn(2);
            mocked.when(() -> Util.nextInt(100)).thenReturn(9);
            mocked.when(() -> Util.nextInt(4)).thenReturn(2);
            Archer archerTest = new Archer("John");
            WeaponType expected = WeaponType.BURNING;
            assertAll(
                    () ->assertEquals( 3, archerTest.getLevel()),
                    () ->assertEquals(50, archerTest.getHealth()),
                    () ->assertEquals(50, archerTest.getStrength()),
                    () ->assertEquals(50, archerTest.getDexterity()),
                    () ->assertEquals(50, archerTest.getCurrentHealth()),
                    () ->assertEquals(expected, archerTest.getWeaponType())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(6));
        }
    }

    @Test
    public void healthMultiplierIsCorrect(){
        assertEquals(1.0, archer.getHealthMultiplier());
    }

    @Test
    public void strengthMultiplierIsCorrect(){
        assertEquals(1.0, archer.getStrengthMultiplier());
    }

    @Test
    public void dexterityMultiplierIsCorrect(){
        assertEquals(1.25, archer.getDexterityMultiplier());
    }

    @Test
    public void getFullNameCorrect(){
        assertEquals("Archer Brien", archer.getFullName());
    }

    @Test
    public void gladiatorLevelUpValuesChangesCorrect(){
        archer.setHealth(25);
        archer.setStrength(25);
        archer.setDexterity(25);
        archer.setLevel(1);
        archer.levelUp();
        assertAll(
                () ->assertEquals( 2, archer.getLevel()),
                () ->assertEquals(50, archer.getHealth()),
                () ->assertEquals(50, archer.getStrength()),
                () ->assertEquals(62, archer.getDexterity())
        );
    }

    @Test
    public void currentHealthIsDecrease(){
        archer.setCurrentHealth(25);
        archer.decreaseHpBy(10);
        assertEquals(15, archer.getCurrentHealth());
    }

    @Test
    public void isCurrentHealthValueEqualsHealthValue(){
        archer.setCurrentHealth(15);
        archer.setHealth(25);
        archer.healUp();
        assertEquals(25, archer.getCurrentHealth());
    }

    //static függvény mockolásra példa
    //privat method tesztelésre példa
    @Test
    public void weaponTypeChoiceWhenRandomNumberLessThenTen() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(anyInt())).thenReturn(9).thenReturn(2);
            method("chooseWeaponType").in(archer).invoke();
            WeaponType expected = WeaponType.BURNING;
            assertEquals(expected, archer.getWeaponType());
            mocked.verify(() -> Util.nextInt(anyInt()), times(2));
        }
    }

    @Test
    public void weaponTypeChoiceWhenRandomNumberBiggerThenTen() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(anyInt())).thenReturn(12);
            method("chooseWeaponType").in(archer).invoke();
            WeaponType expected = WeaponType.NORMAL;
            assertEquals(expected, archer.getWeaponType());
            mocked.verify(() -> Util.nextInt(anyInt()), times(1));
        }
    }

    @AfterEach
    public void tearDown() {
        archer = null;
    }

}