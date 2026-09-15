import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
}