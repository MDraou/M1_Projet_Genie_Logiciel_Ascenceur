package elevator;

import static elevator.IElevator.State.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.awt.*;

public class TestAutomaticElevator {

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

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		assertEquals(ERROR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());
	}
}
