package elevator;

import org.junit.jupiter.api.Test;

import static elevator.IElevator.State.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDoorElevator {

    @Test
    public void testDoorElevator(){
        var e = new ElevatorSimulator(2, true);
        assertEquals("-S0", e.getEvents());
        e.openDoor();
        assertEquals("-S0-O0", e.getEvents());
        e.up();
        e.stopNext();
        while (e.getState() == UP) e.oneStep();
        System.out.println(e.getEvents());
    }
}
