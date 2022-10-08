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
        ArrayList<Integer> floors = new ArrayList<>();
        switch (state) {
            case UP -> {
                floors.addAll(upRequests);
                floors.removeIf(f -> (f < floor));
                try { return (floors.isEmpty()) ? downRequests.get(downRequests.size() - 1) : floors.get(0); }
                catch (IndexOutOfBoundsException e) { return upRequests.get(0); }
            }
            case DOWN -> {
                floors.addAll(downRequests);
                floors.removeIf(f -> (f > floor));
                try { return (floors.isEmpty()) ? upRequests.get(0) : floors.get(floors.size() - 1); }
                catch (IndexOutOfBoundsException e) { return downRequests.get(downRequests.size() - 1); }
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
