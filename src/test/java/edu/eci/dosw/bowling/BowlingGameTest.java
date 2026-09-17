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

    @Test
    @DisplayName("A5: roll() cuando el juego ya esta completo debe lanzar IllegalStateException")
    void rollWhenGameIsComplete_throwsIllegalStateException() {
        // Arrange: Se juegan 20 tiros de 0 pinos (10 frames completos de 2 tiros cada uno)
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 20; i++) {
            game.roll(0);
        }

        // Act & Assert: El tiro 21 debe ser rechazado por juego terminado
        assertThrows(
            IllegalStateException.class,
            () -> game.roll(0),
            "No se pueden registrar mas tiros si el juego ya termino"
        );
    }
}