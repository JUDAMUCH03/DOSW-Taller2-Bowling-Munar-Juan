package edu.eci.dosw.bowling;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
        // Arrange
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);

        // Act & Assert
        assertThrows(
            IllegalStateException.class,
            () -> game.roll(0),
            "No se pueden registrar mas tiros si el juego ya termino"
        );
    }

    // Helper para simular N lanzamientos idénticos
    private void rollMany(BowlingGame game, int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

    @Test
    @DisplayName("A6: roll(10) detecta strike, marca el frame y avanza al siguiente")
    void rollTenPins_detectsStrikeAndAdvancesFrame() {
        // Arrange
        BowlingGame game = new BowlingGame();

        // Act: Frame 1 es Strike, siguiente tiro pertenece al Frame 2
        game.roll(10);
        game.roll(4);

        // Assert
        List<Frame> frames = game.getFrames();
        assertEquals(2, frames.size(), "Debe haber 2 frames creados");
        assertEquals(FrameType.STRIKE, frames.get(0).getType(), "El frame 1 debe ser STRIKE");
        assertEquals(1, frames.get(0).getRolls().size(), "El frame con strike solo tiene 1 tiro");
        assertEquals(4, frames.get(1).getRolls().get(0), "El frame 2 recibe el tiro siguiente");
    }

    @Test 
    @DisplayName ("A7: roll(5) y luego roll(5) detecta spare")
    void rollFiveAndFive_detectSpare() {
        // Arrange
        BowlingGame game = new BowlingGame();

        //Act
        game.roll(5);
        game.roll(5);

        //Assert
        List<Frame> frames = game.getFrames();
        assertEquals(1, frames.size(), "Debe haber 1 frame creado");
        assertEquals(FrameType.SPARE, frames.get(0).getType(), "El frame debe ser SPARE");

    }

    @Test
    @DisplayName("A8: Frame 10 con strike acepta hasta 3 tiros sin lanzar excepcion")
    void tenthFrameWithStrike_acceptsUpToThreeRolls() {
        // Arrange
        BowlingGame game = new BowlingGame();
        rollMany(game, 18, 0);

        // Act
        assertDoesNotThrow(() -> {
            game.roll(10);
            game.roll(10);
            game.roll(10);
        }, "El frame 10 con strike debe permitir hasta 3 tiros");

        //Assert
        List<Frame> frames = game.getFrames();
        assertEquals(10, frames.size(), "El juego debe tener exactamente 10 frames");
        Frame tenthFrame = frames.get(9);
        assertEquals(3, tenthFrame.getRolls().size(), "El frame 10 debe almacenar los 3 tiros de bonificacion");
        assertEquals(FrameType.TENTH, tenthFrame.getType(), "El ultimo frame debe identificarse como TENTH");
    }
}