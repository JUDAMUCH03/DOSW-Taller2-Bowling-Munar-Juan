package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

/**
 * Motor de un juego de Bowling para un jugador.
 * Un juego tiene exactamente 10 frames.
 */
public class BowlingGame {

    private static final int MAX_FRAMES = 10;
    private final List<Frame> frames;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
    }

    public void roll(int pins) {
        if (isComplete()) {
            throw new IllegalStateException("El juego ya esta completo. No se permiten mas tiros.");
        }

        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("Numero de pinos invalido: " + pins + ". Debe estar entre 0 y 10.");
        }

        Frame activeFrame = getOrCreateActiveFrame();

        if (activeFrame.getPinsRolled() + pins > 10) {
            throw new IllegalArgumentException("La suma de pinos en el frame no puede superar 10");
        }

        activeFrame.addRoll(pins);
    }

    private Frame getOrCreateActiveFrame() {
        if (frames.isEmpty() || frames.get(frames.size() - 1).isComplete()) {
            Frame newFrame = new Frame();
            frames.add(newFrame);
            return newFrame;
        }
        return frames.get(frames.size() - 1);
    }

    public int score() {
        return 0;
    }

    public boolean isComplete() {
        return frames.size() == MAX_FRAMES && frames.get(MAX_FRAMES - 1).isComplete();
    }

    public List<Frame> getFrames() { 
        return List.copyOf(frames); 
    }
}