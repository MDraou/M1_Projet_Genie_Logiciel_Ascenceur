package elevator;

import static elevator.IElevator.State.STOP;

public class TestAutomaticElevator {

  public static void main(String[] args) throws Exception {
    // 10 étages et mode automatique
    var e = new ElevatorSimulator(10, false);
    // activer la montée
    e.up();
    // surveiller l'évolution de l'ascenseur
    for (int lift = 0; (e.getState() != STOP);) {
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

    // vérifions que tout est OK
    boolean ok = (4.0 == e.getLevel());
    ok = ok && (STOP == e.getState());
    ok = ok && ("-S0-U0-U1-U2-U3-O4-S4".equals(e.getEvents()));
    if (!ok) {
      throw new IllegalStateException();
    }
  }
}