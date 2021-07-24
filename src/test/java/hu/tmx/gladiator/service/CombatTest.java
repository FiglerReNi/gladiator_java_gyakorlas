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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

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
        gladiatorTwo.setHealth(3);
        gladiatorTwo.setStrength(3);
        gladiatorTwo.setDexterity(3);
        gladiatorTwo.setCurrentHealth(3);
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
                    ()->assertEquals(-9, combat.getCompetitors().get("defender").getCurrentHealth())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(3));
        }

    }

    @Test
    public void combatWithParalyzedWinFirstAttackerInTwoRound(){
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
            mocked.verify(() -> Util.nextInt(anyInt()), times(7));
        }
    }

    @Test
    public void alreadyParalyzedSecondRound(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(10);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.PARALYZING);
            gladiatorTwo.setWeaponEffectTurns(4);
            assertAll(
                    ()->assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    ()->assertEquals(3, combat.getCompetitors().get("defender").getWeaponEffectTurns()),
                    ()->assertTrue(combat.getCompetitors().get("defender").isParalyzed())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
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
                    () -> assertEquals(12.56, combat.getDamage()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getBleeding())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
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
                    () -> assertEquals(12.62, combat.getDamage()),
                    () -> assertEquals(2, combat.getCompetitors().get("defender").getBleeding())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
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
                    () -> assertEquals(12.65, combat.getDamage()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getPoisoned())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
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
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
        }
    }

    @Test
    public void alreadyPoisonedSecondRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(20);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.POISON);
            gladiatorTwo.setPoisoned(1);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.65, combat.getDamage()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getPoisoned()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getWeaponEffectTurns())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
        }
    }

    @Test
    public void endOfPoisoningAfterThreeRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(20);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.POISON);
            gladiatorTwo.setPoisoned(1);
            gladiatorTwo.setWeaponEffectTurns(3);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.5, combat.getDamage()),
                    () -> assertEquals(0, combat.getCompetitors().get("defender").getPoisoned()),
                    () -> assertEquals(0, combat.getCompetitors().get("defender").getWeaponEffectTurns())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
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
                    () -> assertEquals(12.65, combat.getDamage()),
                    () -> assertEquals(5, combat.getCompetitors().get("defender").getWeaponEffectTurns())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(5));
        }
    }

    @Test
    public void alreadyBurningSecondRound() {
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(2)).thenReturn(1);
            mocked.when(() -> Util.nextInt(100)).thenReturn(16);
            mocked.when(() -> Util.nextInt(5)).thenReturn(4);
            gladiatorOne.setWeaponType(WeaponType.BURNING);
            gladiatorTwo.setWeaponEffectTurns(2);
            assertAll(
                    () -> assertEquals(gladiatorOne, combat.simulation(gladiatorOne, gladiatorTwo)),
                    () -> assertEquals(12.65, combat.getDamage()),
                    () -> assertEquals(1, combat.getCompetitors().get("defender").getWeaponEffectTurns())
            );
            mocked.verify(() -> Util.nextInt(anyInt()), times(4));
        }
    }

    @AfterEach
    public void tearDown(){
        combat = null;
        gladiatorOne = null;
        gladiatorTwo = null;
    }
}