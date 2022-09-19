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

	@Test
	public void testStepByStepElevator() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		int etage = 0;
		while (e.getState() == UP) {
			e.oneStep();
			// System.out.printf("level = %3.2f\n", e.getLevel());
			if (e.getAndResetStageSensor()) System.out.println("Etage "+ ++etage);
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
	@Test
	public void testButtonPanel() {
		int nbEtage = 4;
		PanelSimulator panel = new PanelSimulator(nbEtage);
		for (int i = 0 ; i < nbEtage ; i++) {
			assertFalse(panel.getAndResetDownButton(i));
			assertFalse(panel.getAndResetUpButton(i));
			assertFalse(panel.getAndResetFloorButton(i));
		}
		assertFalse(panel.getAndResetInitButton());
		assertFalse(panel.getAndResetStopButton());

		for (int i = 0 ; i < nbEtage ; i++) {
			panel.pressDownButton(i);
			assertTrue(panel.getAndResetDownButton(i));
			assertFalse(panel.getAndResetDownButton(i));
			panel.pressUpButton(i);
			assertTrue(panel.getAndResetUpButton(i));
			assertFalse(panel.getAndResetUpButton(i));
			panel.pressFloorButton(i);
			assertTrue(panel.getAndResetFloorButton(i));
			assertFalse(panel.getAndResetFloorButton(i));
		}
		panel.pressInitButton();
		assertTrue(panel.getAndResetInitButton());
		assertFalse(panel.getAndResetInitButton());
		panel.pressStopButton();
		assertTrue(panel.getAndResetStopButton());
		assertFalse(panel.getAndResetStopButton());
	}

	@Test
	public void testLightPanel(){
		int nbEtage = 4;
		PanelSimulator panel = new PanelSimulator(nbEtage);
		assertFalse(panel.getDownLight(1));
		assertFalse(panel.getFloorLight(3));
		assertFalse(panel.getUpLight(1));
		panel.setDownLight(3, true);
		assertTrue(panel.getDownLight(3));
		panel.setFloorLight(2, true);
		assertTrue(panel.getFloorLight(2));
		panel.setUpLight(1, true);
		assertTrue(panel.getUpLight(1));

		panel.setDownLight(3, false);
		assertFalse(panel.getDownLight(3));
		panel.setFloorLight(2, false);
		assertFalse(panel.getFloorLight(2));
		panel.setUpLight(1, false);
		assertFalse(panel.getUpLight(1));
	}

	@Test
	public void testDoorElevator(){
		PanelSimulator panel = new PanelSimulator();
		assertTrue(panel.);
	}
}
