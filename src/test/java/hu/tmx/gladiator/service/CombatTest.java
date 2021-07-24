package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.Archer;
import hu.tmx.gladiator.model.Swordsman;
import hu.tmx.gladiator.model.WeaponType;
import hu.tmx.gladiator.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

    Combat combat;
    Archer gladiatorOne;
    Swordsman gladiatorTwo;

    @BeforeEach
    public void setUp() {
        combat = new Combat();
        gladiatorOne = new Archer("John");
        gladiatorOne.setHealth(25);
        gladiatorOne.setStrength(25);
        gladiatorOne.setDexterity(25);
        gladiatorOne.setCurrentHealth(25);
        gladiatorOne.setLevel(1);
        gladiatorTwo = new Swordsman("James");
        gladiatorTwo.setHealth(5);
        gladiatorTwo.setStrength(5);
        gladiatorTwo.setDexterity(5);
        gladiatorTwo.setCurrentHealth(5);
        gladiatorTwo.setLevel(1);
    }

    @Test
    public void attackerChoiceWorks() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            gladiatorTwo.setCurrentHealth(0);
            assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo));
            mocked.verify(() -> Util.nextInt(2));
        }
    }

    @Test
    public void bothGladiatorObjectIsNull(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            assertNull(combat.simulation(null, null));
            mocked.verify(() -> Util.nextInt(2));
        }
    }

    @Test
    public void oneOfGladiatorObjectIsNull(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            assertEquals(gladiatorOne, combat.simulation(gladiatorOne, null));
            mocked.verify(() -> Util.nextInt(2));
        }
    }

    @Test
    public void combatWithNormalWeaponWinFirstAttackerInOneRound(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(1);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.NORMAL);
            gladiatorTwo.setWeaponType(WeaponType.NORMAL);
            assertAll(
                    ()->assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    ()->assertEquals(12.5, combat.getDamage()),
                    ()->assertTrue(combat.getCompetitors().get("defender").isDead()),
                    ()->assertEquals(-7, combat.getCompetitors().get("defender").getCurrentHealth())
            );
        }
    }

    @Test
    public void combatWithParalizedWinFirstAttackerInTwoRound(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(1);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorTwo.setHealth(20);
            gladiatorTwo.setStrength(20);
            gladiatorTwo.setDexterity(20);
            gladiatorTwo.setCurrentHealth(20);
            gladiatorOne.setWeaponType(WeaponType.PARALYZING);
            assertAll(
                    ()->assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    ()->assertTrue(combat.getCompetitors().get("defender").isDead()),
                    ()->assertEquals(4, combat.getCompetitors().get("defender").getWeaponEffectTurns()),
                    ()->assertTrue(combat.getCompetitors().get("defender").isParalyzed()),
                    ()->assertEquals(-4, combat.getCompetitors().get("defender").getCurrentHealth())
            );
        }
    }

    @Test
    public void combatWithBleedingWinFirstAttackerInOneRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(1);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.BLEEDING);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.6, combat.getDamage()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getBleeding())
            );
        }
    }

    @Test
    public void alreadyBleedingSecondRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(10);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.BLEEDING);
            gladiatorTwo.setBleeding(2);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.7, combat.getDamage()),
                    () -> assertEquals(2, combat.getCompetitors().get("defender").getBleeding())
            );
        }
    }

    @Test
    public void combatWithPoisonWinFirstAttackerInOneRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(1);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.POISON);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.75, combat.getDamage()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getPoisoned())
            );
        }
    }

    @Test
    public void poisonCausesDeath() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(1);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.POISON);
            gladiatorTwo.setPoisoned(1);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(-12, combat.getCompetitors().get("defender").getCurrentHealth()),
                    () -> assertEquals(12.5, combat.getDamage()),
                    () -> assertEquals(2, combat.getCompetitors().get("defender").getPoisoned())
            );
        }
    }


    @Test
    public void combatWithBurningWinFirstAttackerInOneRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(1);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.BURNING);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.75, combat.getDamage()),
                    () -> assertEquals(5, combat.getCompetitors().get("defender").getWeaponEffectTurns())
            );
        }
    }



    @AfterEach
    public void tearDown(){
        combat = null;
        gladiatorOne = null;
        gladiatorTwo = null;
    }
}