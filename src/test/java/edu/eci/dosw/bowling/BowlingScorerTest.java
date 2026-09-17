package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BowlingScorerTest {

    // Helper para simular N tiros identicos
    private void rollMany(BowlingGame game, int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

    @Test
    @DisplayName("B1: Juego con todos los tiros a 0 debe dar score 0")
    void gutterGame_scoresZero() {
        // Arrange: Partida completa de 20 tiros al canal
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);

        // Act & Assert
        assertEquals(0, game.score(), "Una partida sin pinos derribados debe sumar 0 puntos");
    }
}