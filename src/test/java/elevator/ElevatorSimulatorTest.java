package elevator;
import static elevator.IElevator.State.STOP;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ElevatorSimulatorTest {

  @Test
  void testGetEvents(){
    var s = new ElevatorSimulator(10, true);
    var nbSteps = 0;
    // activer la montée
    s.up();
    // surveiller l'évolution de l'ascenseur
    for (; (s.getLevel() < 2.5); nbSteps++) {
      s.oneStep();
    }
    s.stopNext();
    for (; (s.getState() != STOP); nbSteps++) {
      s.oneStep();
    }
    s.stopSimulator();

    // vérifions que tout est OK
    // (3 étages * 3 mètres * 5 étapes) + 25 étapes pour les portes
    assertEquals(70, nbSteps);
    // l'ascenseur doit être au 3ème
    assertEquals(3.0, s.getLevel());
    // l'ascenseur doit être à l'arret
    assertEquals(STOP, s.getState());
    // les étapes sont
    assertEquals("-S0-U0-U1-U2-O3-S3", s.getEvents());
  }
}
