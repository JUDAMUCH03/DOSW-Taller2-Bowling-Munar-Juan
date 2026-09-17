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
        int sum = 0;
        for (int roll : rolls) {
            sum += roll;
        }
        return sum;
    }

    public boolean isComplete() {
        return rolls.size() == 2 || (!rolls.isEmpty() && rolls.get(0) == 10);
    }
}