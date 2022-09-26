package elevator;

import java.util.ArrayList;
import java.util.Collections;

import static elevator.IElevator.State.*;

public class Scheduler {
    private final ArrayList<Request> requests = new ArrayList<>();

    public void save(int floor, IElevator.State state) {
        if (state != UP && state != DOWN) throw new IllegalStateException("Must be UP or DOWN");
        Request request = new Request(state, floor);
        if (!requests.contains(request)) requests.add(request);
    }

    public void save(int floor) {
        Request request = new Request(null, floor);
        if (!requests.contains(request)) requests.add(request);
    }

    public Request next(int floor, IElevator.State state) {
        if (state != UP && state != DOWN) throw new IllegalStateException("Must be UP or DOWN");
        if (requests.isEmpty()) return null;
        Collections.sort(requests);
        ArrayList<Request> sameState = new ArrayList<>(requests);
        sameState.removeIf(e -> e.getState() != state);
        if (sameState.isEmpty()) return;
        else {

        }
    }
}
