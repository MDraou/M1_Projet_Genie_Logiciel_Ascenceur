package scheduler;

import java.util.ArrayList;
import java.util.Collections;
import elevator.ElevatorSimulator;

import elevator.IElevator;
import elevator.Scheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static elevator.IElevator.State.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TestScheduler {

    //L'ascenseur monte et :
    // CAS 1 : il reste des requêtes à traiter dans les étages supérieurs
    // CAS 2 : des requêtes vers les étages inférieurs arrivent
    @Test
    public void upTest() {
        Scheduler scheduler = new Scheduler();

        var e = new ElevatorSimulator(5, true);
        scheduler.save(2, UP);
        scheduler.save(4, DOWN);
        e.up();
        int next = scheduler.next((int) e.getLevel(), e.getState());
        assertEquals(2, next);
        while (next != -1) {
            e.oneStep();
            if (e.getLevel() == next - 1) e.stopNext();
            if (e.getLevel() == next) scheduler.remove(next);
            while (e.getState() == DOOR) ;
            next = scheduler.next((int) e.getLevel(), e.getState());
            assertEquals(4, next);
        }

        scheduler.save(3, DOWN);
        scheduler.save(1, UP);
        e.down();
        next = scheduler.next((int) e.getLevel(), e.getState());
        assertEquals(next, 3);
        while (next != -1) {
            e.oneStep();
            if (e.getLevel() == next - 1) e.stopNext();
            if (e.getLevel() == next) scheduler.remove(next);
            while (e.getState() == DOOR) ;
            next = scheduler.next((int) e.getLevel(), e.getState());
            assertEquals(next, 1);
        }


        e.stopSimulator();

    }

    // L'ascenseur est au 1er étage, monte vers le cinquième et une requête enregistrée à la montée au 3ème étage
    @Test
    public void upRequestTest(){
        Scheduler scheduler = new Scheduler();

        var e = new ElevatorSimulator(5, true);
        scheduler.save(5, DOWN);
        e.up();
        while(!e.getAndResetStageSensor()) e.oneStep();
        scheduler.save(3, UP);
            scheduler.next((int) e.getLevel(), UP);
            if (e.getLevel() == 3) {
                e.stopNext();
                scheduler.next((int) e.getLevel(), UP);
            }
        e.stopSimulator();
    }

    // L'ascenseur est au 3ème, à la montée et un utilisateur en cabine souhaite aller au RDC
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
