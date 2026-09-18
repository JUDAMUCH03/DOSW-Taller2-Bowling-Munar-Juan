package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private final List<Integer> rolls = new ArrayList<>();
    private final boolean isTenth;

    public Frame() {
        this(false);
    }

    public Frame(boolean isTenth) {
        this.isTenth = isTenth;
    }

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
        if (isTenth) {
            return FrameType.TENTH;
        }
        if (!rolls.isEmpty() && rolls.get(0) == 10) {
            return FrameType.STRIKE;
        }
        if (rolls.size() == 2 && getPinsRolled() == 10) {
            return FrameType.SPARE;
        }
        return FrameType.NORMAL;
    }

    public boolean isComplete() {
        if (isTenth) {
            if (rolls.size() < 2) {
                return false;
            }
            if (rolls.size() == 2) {
                boolean hasBonus = rolls.get(0) == 10 || (rolls.get(0) + rolls.get(1) == 10);
                return !hasBonus;
            }
            return rolls.size() == 3;
        }
        return getType() == FrameType.STRIKE || rolls.size() == 2;
    }

    public boolean isTenth() {
        return isTenth;
    }
}