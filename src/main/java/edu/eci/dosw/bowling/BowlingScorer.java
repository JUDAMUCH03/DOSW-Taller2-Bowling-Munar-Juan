package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {

    public int calculate(List<Frame> frames) {
        return frames.stream().mapToInt(Frame::getPinsRolled).sum();
    }
}