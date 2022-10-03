package scheduler;

import java.util.ArrayList;
import java.util.Collections;
import elevator.ElevatorSimulator;

import elevator.IElevator;
import elevator.Scheduler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static elevator.IElevator.State.DOWN;
import static elevator.IElevator.State.UP;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TestScheduler {

    //L'ascenseur monte et :
    // CAS 1 : il reste des requêtes à traiter dans les étages supérieurs
    // CAS 2 : des requêtes vers les étages inférieurs arrivent
    public void upTest(int floor, IElevator.State state) {

        final ArrayList<Integer> upRequests = new ArrayList<>();
        final ArrayList<Integer> downRequests = new ArrayList<>();
        Scheduler scheduler = new Scheduler();

        var e = new ElevatorSimulator(3, false);
        e.up();

        while (e.getState() == UP) {
            //System.out.printf("level = %3.2f\n", e.getLevel());
            //Thread.sleep(100);
            if (upRequests.contains(floor)) scheduler.next(floor, UP);
            if (downRequests.contains(floor)) scheduler.next(floor, DOWN);
        }
        e.stopSimulator();

    }

    // L'ascenseur :
    // Est au 1er étage, monte vers le cinquième et une requête à la montée au 3ème étage
    public void upRequestTest(int floor, IElevator.State state){

        final ArrayList<Integer> upRequests = new ArrayList<>();
        final ArrayList<Integer> dowRequests = new ArrayList<>();
        Scheduler scheduler = new Scheduler();

        var e = new ElevatorSimulator(1, false);
        e.up();

        while(e.getState() == UP) {
            scheduler.next(floor, UP);
            if (e.getLevel() == 3) {
                e.stopNext();
                scheduler.next(floor, UP);
            }
        }
        e.stopSimulator();
    }

    // L'ascenseur est :
    // au 3ème, à la montée et un utilisateur en cabine souhaite aller au RDC
    public void downRequestTest(int floor, IElevator.State state){

        final ArrayList<Integer> upRequests = new ArrayList<>();
        final ArrayList<Integer> dowRequests = new ArrayList<>();
        Scheduler scheduler = new Scheduler();

        var e = new ElevatorSimulator(3, false);
        e.up();

        while(e.getState() == UP) {
            scheduler.next(floor, DOWN);
            e.reset();
        }
        e.stopSimulator();

    }




}
