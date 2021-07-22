package hu.tmx.gladiator.service;

import hu.tmx.gladiator.model.Archer;
import hu.tmx.gladiator.model.Gladiator;
import hu.tmx.gladiator.util.Util;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static hu.tmx.gladiator.service.GladiatorFactory.generateRandomGladiator;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

class GladiatorFactoryTest {

    @Test
    public void createRightGladiator(){
        try (MockedStatic<Util> mocked = Mockito.mockStatic(Util.class)) {
            mocked.when(() -> Util.nextInt(5)).thenReturn(2);
            mocked.when(Util::randomName).thenReturn("John");

            assertEquals("Archer", generateRandomGladiator().getClass().getSimpleName());

            mocked.verify(() -> Util.nextInt(anyInt()), times(7));
            mocked.verify(Util::randomName);
        }
    }

}