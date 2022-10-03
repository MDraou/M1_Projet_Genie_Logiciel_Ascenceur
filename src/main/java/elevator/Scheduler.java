package elevator;

import java.util.ArrayList;
import java.util.Collections;

import static elevator.IElevator.State.*;

public class Scheduler {
    private final ArrayList<Integer> upRequests = new ArrayList<>();
    private final ArrayList<Integer> downRequests = new ArrayList<>();

    public void save(int floor, IElevator.State state) {
        switch (state) {
            case UP -> {
                if (!upRequests.contains(floor)) {
                    upRequests.add(floor);
                    Collections.sort(upRequests);
                }
            }
            case DOWN -> {
                if (!downRequests.contains(floor)) {
                    downRequests.add(floor);
                    Collections.sort(downRequests);
                }
            }
            default -> throw new IllegalStateException("Must be UP or DOWN");
        }
    }

    public Integer next(int floor, IElevator.State state) {
        if (upRequests.isEmpty() && downRequests.isEmpty()) return -1;
        ArrayList<Integer> floors = (state == UP) ? upRequests : downRequests;
        switch (state) {
            case UP -> {
                if (upRequests.isEmpty()) return downRequests.get(0);
                floors.removeIf(f -> (f <= floor));
                return floors.get(0);
            }
            case DOWN -> {
                if (downRequests.isEmpty()) return upRequests.get(upRequests.size() - 1);
                floors.removeIf(f -> (f >= floor));
                return floors.get(floors.size() - 1);
            }
            default -> throw new IllegalStateException("Must be UP or DOWN");
        }
    }

    public void remove(int floor) {
        upRequests.removeIf(f -> (f == floor));
        downRequests.removeIf(f -> (f == floor));
    }

    public void removeAll() {
        upRequests.clear();
        downRequests.clear();
    }
}
