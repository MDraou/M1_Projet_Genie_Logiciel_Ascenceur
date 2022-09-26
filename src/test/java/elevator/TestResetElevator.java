package elevator;

import org.junit.jupiter.api.Test;

import static elevator.IElevator.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResetElevator {
    @Test
    public void testResetElevator(){
        var e = new ElevatorSimulator(2, true);
        e.up();
        while (e.getState() != ERROR) e.oneStep();
        assertEquals("-S0-U0-U1-U2-E2", e.getEvents());
        e.reset();
        assertEquals("-S0-U0-U1-U2-E2-R2", e.getEvents());
        while (e.getState() == RESET) e.oneStep();
        assertEquals("-S0-U0-U1-U2-E2-R2-R1-S0", e.getEvents());
        assertEquals(e.getLevel(), 0);
    }
}
