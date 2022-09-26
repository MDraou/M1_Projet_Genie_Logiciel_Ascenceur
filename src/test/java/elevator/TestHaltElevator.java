package elevator;

import org.junit.jupiter.api.Test;

import static elevator.IElevator.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHaltElevator {
    @Test
    public void testHaltElevator(){
        var e = new ElevatorSimulator(2, true);
        e.up();
        while (e.getState() == UP) e.oneStep();
        assertEquals("-S0-U0-U1-U2-E2", e.getEvents());
        e.halt();
        assertEquals("-S0-U0-U1-U2-E2-E2", e.getEvents());
        e.stopSimulator();
    }
}
