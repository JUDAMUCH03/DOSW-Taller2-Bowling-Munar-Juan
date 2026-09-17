package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {

    public int calculate(List<Frame> frames) {
        int totalScore = 0;
        for (Frame frame : frames) {
            totalScore += frame.getPinsRolled();
        }
        return totalScore;
    }
}