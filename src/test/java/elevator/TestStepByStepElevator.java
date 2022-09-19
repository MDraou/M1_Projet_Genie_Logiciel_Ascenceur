package elevator;

import org.junit.jupiter.api.Test;

import static elevator.IElevator.State.DOOR;
import static elevator.IElevator.State.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStepByStepElevator {

    @Test
    public void testStepByStepElevator() throws Exception {
        // 3 étages en mode pas à pas
        var e = new ElevatorSimulator(3, true);
        // activer la montée
        e.up();
        // surveiller l'évolution de l'ascenseur
        int level = 0;
        while (e.getState() == UP) {
            e.oneStep();
            // System.out.printf("level = %3.2f\n", e.getLevel());
            if (e.getAndResetStageSensor()) System.out.println("Level "+ ++level);
            if (e.getLevel() == 2) e.stopNext();
            Thread.sleep(100);
        }
        e.stopSimulator();

        // l'ascenseur est au 3ème
        assertEquals(3.0, e.getLevel());
        // l'ascenseur est en erreur
        assertEquals(DOOR, e.getState());
        // les étapes
        assertEquals("-S0-U0-U1-U2-O3", e.getEvents());
    }
}
