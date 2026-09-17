package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {

    public int calculate(List<Frame> frames) {
        int totalScore = 0;

        for (int i = 0; i < frames.size(); i++) {
            Frame currentFrame = frames.get(i);

            if (i < 9 && currentFrame.getType() == FrameType.SPARE) {
                int bonus = frames.get(i + 1).getRolls().get(0);
                totalScore += 10 + bonus;
            } else {
                totalScore += currentFrame.getPinsRolled();
            }
        }

        return totalScore;
    }
}