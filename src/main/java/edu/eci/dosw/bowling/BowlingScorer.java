package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {

    private static final int REGULAR_FRAMES_LIMIT = 9;
    private static final int STRIKE_BASE_SCORE = 10;
    private static final int SPARE_BASE_SCORE = 10;

    public int calculate(List<Frame> frames) {
        int totalScore = 0;

        for (int i = 0; i < frames.size(); i++) {
            totalScore += scoreForFrame(frames, i);
        }

        return totalScore;
    }

    private int scoreForFrame(List<Frame> frames, int index) {
        Frame frame = frames.get(index);

        if (index < REGULAR_FRAMES_LIMIT && frame.getType() == FrameType.STRIKE) {
            return STRIKE_BASE_SCORE + calculateStrikeBonus(frames, index);
        }

        if (index < REGULAR_FRAMES_LIMIT && frame.getType() == FrameType.SPARE) {
            return SPARE_BASE_SCORE + calculateSpareBonus(frames, index);
        }

        return frame.getPinsRolled();
    }

    private int calculateStrikeBonus(List<Frame> frames, int index) {
        Frame nextFrame = frames.get(index + 1);
        int firstBonusRoll = nextFrame.getRolls().get(0);

        if (nextFrame.getType() == FrameType.STRIKE && (index + 1) < REGULAR_FRAMES_LIMIT) {
            int secondBonusRoll = frames.get(index + 2).getRolls().get(0);
            return firstBonusRoll + secondBonusRoll;
        }

        return firstBonusRoll + nextFrame.getRolls().get(1);
    }

    private int calculateSpareBonus(List<Frame> frames, int index) {
        return frames.get(index + 1).getRolls().get(0);
    }
}