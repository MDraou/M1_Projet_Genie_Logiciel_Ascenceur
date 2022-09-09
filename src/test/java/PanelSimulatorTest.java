import elevator.ElevatorSimulator;
import org.junit.jupiter.api.Test;
import static elevator.IElevator.State.STOP;
import static org.assertj.core.api.Assertions.*;

public class PanelSimulatorTest {

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
    boolean ok = (((3 * 3 * 5) + 25) == nbSteps);
    ok = ok && (3.0 == s.getLevel());
    ok = ok && (STOP == s.getState());
    ok = ok && ("-S0-U0-U1-U2-O3-S3".equals(s.getEvents()));
    assertThat(ok).isTrue();
  }
}
