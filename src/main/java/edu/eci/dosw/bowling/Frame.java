package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private final List<Integer> rolls = new ArrayList<>();

    public void addRoll(int pins) {
        rolls.add(pins);
    }

    public List<Integer> getRolls() {
        return List.copyOf(rolls);
    }

    public int getPinsRolled() {
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    public FrameType getType() {
        if (!rolls.isEmpty() && rolls.get(0) == 10) {
            return FrameType.STRIKE;
        }
        return FrameType.NORMAL;
    }

    public boolean isComplete() {
        return getType() == FrameType.STRIKE || rolls.size() == 2;
    }
}