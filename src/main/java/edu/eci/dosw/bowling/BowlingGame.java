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

        // En frames 1 al 9, la suma no puede exceder 10
        if (!activeFrame.isTenth() && activeFrame.getPinsRolled() + pins > 10) {
            throw new IllegalArgumentException("La suma de pinos en el frame no puede superar 10");
        }

        activeFrame.addRoll(pins);
    }

    private Frame getOrCreateActiveFrame() {
        if (frames.isEmpty()) {
            Frame firstFrame = new Frame(false);
            frames.add(firstFrame);
            return firstFrame;
        }

        Frame current = frames.get(frames.size() - 1);
        if (current.isComplete()) {
            boolean isLast = (frames.size() == MAX_FRAMES - 1);
            Frame nextFrame = new Frame(isLast);
            frames.add(nextFrame);
            return nextFrame;
        }

        return current;
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