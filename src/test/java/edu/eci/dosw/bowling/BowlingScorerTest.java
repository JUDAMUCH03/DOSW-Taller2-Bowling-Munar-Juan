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
        // Arrange
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);

        // Act y Assert
        assertEquals(0, game.score(), "Una partida sin pinos derribados debe sumar 0 puntos");
    }

    @Test
    @DisplayName("B2: Juego sin strikes ni spares calcula suma directa de pinos")
    void openGameWithoutStrikesOrSpares_scoresSumOfAllPins() {
        // Arrange
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 1);

        // Act y Assert
        assertEquals(20, game.score(), "20 tiros de 1 pino deben sumar 20 puntos");
    }

    @Test
    @DisplayName("B3: Spare en frame 1 suma 10 mas el primer tiro del frame 2")
    void spareInFirstFrame_addsNextRollBonus() {
        // Arrange
        BowlingGame game = new BowlingGame();
        // Frame 1: Spare (5 + 5)
        game.roll(5);
        game.roll(5);
        // Frame 2: primer tiro 3, segundo tiro 0
        game.roll(3);
        game.roll(0);
        // Frames 3 al 10: 16 tiros a 0 para completar los 10 frames
        rollMany(game, 16, 0);

        // Act & Assert: Frame 1 (13) + Frame 2 (3) = 16
        assertEquals(16, game.score(), "El spare debe bonificar el primer tiro del frame siguiente (13 + 3 = 16)");
    }

    @Test
    @DisplayName("B4: Strike en frame 1 suma 10 mas los siguientes dos tiros")
    void strikeInFirstFrame_addsNextTwoRollsBonus() {
        // Arrange
        BowlingGame game = new BowlingGame();
        game.roll(10); // Frame 1: Strike
        game.roll(4);  // Frame 2: tiro 1
        game.roll(3);  // Frame 2: tiro 2
        rollMany(game, 16, 0); // Frames 3 al 10

        // Act & Assert: Frame 1 (17) + Frame 2 (7) = 24
        assertEquals(24, game.score(), "Frame 1 (17) + Frame 2 (7) debe dar 24");
    }

    @Test
    @DisplayName("B5: Dos strikes consecutivos suman bono con tiro de dos frames adelante")
    void twoConsecutiveStrikes_scoresBonusCorrectly() {
        // Arrange
        BowlingGame game = new BowlingGame();
        game.roll(10); // Frame 1: Strike
        game.roll(10); // Frame 2: Strike
        game.roll(5);  // Frame 3: tiro 1
        game.roll(0);  // Frame 3: tiro 2
        rollMany(game, 14, 0); // Frames 4 al 10

        // Act & Assert
        assertEquals(45, game.score(), "Dos strikes consecutivos deben totalizar 45 puntos");
    }

    // Helper para jugar una partida completa de spares
    private void rollAllSpares(BowlingGame game, int lastBonus) {
        for (int i = 0; i < 10; i++) {
            game.roll(5);
            game.roll(5);
        }
        game.roll(lastBonus);
    }

    @Test
    @DisplayName("B6: Todos spares mas ultimo tiro de 5 debe dar score 150")
    void allSparesWithFiveBonus_scores150() {
        // Arrange
        BowlingGame game = new BowlingGame();

        // Act
        rollAllSpares(game, 5);

        // Assert
        assertEquals(150, game.score(), "Una partida de solo spares con bono final de 5 debe sumar 150");
    }
}