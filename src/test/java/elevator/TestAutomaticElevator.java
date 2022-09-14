package elevator;

import static elevator.IElevator.State.STOP;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAutomaticElevator {

	@Test
	public void testAutomaticElevator() throws Exception {
		// 10 étages et mode automatique
		var e = new ElevatorSimulator(10, false);
		// initialement, l'ascenseur est au RdC
		int lift = 0;
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		for (; (e.getState() != STOP);) {
			Thread.sleep(100);
			// tester le franchissement d'étage
			if (e.getAndResetStageSensor()) {
				// au troisième étage, stopper au suivant
				if (++lift == 3) {
					e.stopNext();
				}
			}
		}
		e.stopSimulator();

		// l'ascenseur est au 4ème
		assertEquals(4.0, e.getLevel());
		// l'ascenseur est au 4ème
		assertEquals(4, lift);
		// l'ascenseur est à l'arret
		assertEquals(STOP, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-O4-S4", e.getEvents());
	}

}