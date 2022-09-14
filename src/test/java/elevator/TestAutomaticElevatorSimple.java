package elevator;

import static elevator.IElevator.State.ERROR;
import static elevator.IElevator.State.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAutomaticElevatorSimple {

	@Test
	public void testAutomaticElevator() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, false);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		while (e.getState() == UP) {
			System.out.printf("level = %3.2f\n", e.getLevel());
			Thread.sleep(100);
		}
		e.stopSimulator();

		// l'ascenseur est au 4ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est à l'arret
		assertEquals(ERROR, e.getState());
		// les étapes
//		assertEquals("à déterminer", e.getEvents());
	}

}