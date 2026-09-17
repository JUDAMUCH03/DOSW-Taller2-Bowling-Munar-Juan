package edu.eci.dosw.bowling;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BowlingGameTest {

    @Test
    @DisplayName("A1: roll(0) debe registrar el primer tiro sin lanzar excepciones")
    void rollZeroPins_shouldNotThrowExceptionAndRecordRoll() {
        // Arrange
        BowlingGame game = new BowlingGame();

        // Act & Assert
        assertDoesNotThrow(() -> game.roll(0));
        assertFalse(game.getFrames().isEmpty(), "Debe existir al menos un frame creado");
        assertEquals(1, game.getFrames().size(), "Debe haber exactamente 1 frame tras el primer tiro");
    }

    @Test
    @DisplayName("A2: roll(-1) con pines negativos debe lanzar IllegalArgumentException")
    void rollNegativePins_throwsIllegalArgumentException() {
        // Arrange
        BowlingGame game = new BowlingGame();

        // Act & Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(-1),
            "Lanzar un tiro con pinos negativos debe arrojar IllegalArgumentException"
        );
    }

    @Test
    @DisplayName("A3: roll(11) con pines mayores a 10 debe lanzar IllegalArgumentException")
    void rollMoreThanTenPins_throwsIllegalArgumentException() {
        // Arrange
        BowlingGame game = new BowlingGame();

        // Act & Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(11),
            "Un tiro no puede registrar mas de 10 pinos"
        );
    }

    @Test
    @DisplayName("A4: Dos tiros en un frame suman > 10 lanza IllegalArgumentException")
    void twoRollsInSameFrameExceedingTenPins_throwsIllegalArgumentException() {
        // Arrange
        BowlingGame game = new BowlingGame();
        game.roll(7);

        // Act & Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(6),
            "La suma de dos tiros dentro del mismo frame regular no puede exceder 10"
        );
    }
}