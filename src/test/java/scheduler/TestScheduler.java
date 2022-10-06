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

    // L'ascenseur monte et il reste des requêtes à traiter dans les étages supérieurs
    @Test
    public void upTest() {
        Scheduler scheduler = new Scheduler();
        scheduler.save(2, UP);

        assertEquals(2, scheduler.next(0, UP));

        scheduler.save(4, UP);
        scheduler.save(6, DOWN);

        scheduler.remove(2);

        assertEquals(4, scheduler.next(2, UP));

        scheduler.remove(4);
        scheduler.save(5, DOWN);

        assertEquals(6, scheduler.next(4, UP));

        scheduler.remove(6);

        assertEquals(5, scheduler.next(6, UP));

        scheduler.remove(5);

        assertEquals(-1, scheduler.next(5, DOWN));
    }

    // L'ascenseur monte et des requêtes vers les étages inférieurs arrivent
    @Test
    public void upDownTest() {
        Scheduler scheduler = new Scheduler();
        scheduler.save(2, UP);

        assertEquals(2, scheduler.next(0, UP));

        scheduler.save(4, DOWN);
        scheduler.save(6, DOWN);
        scheduler.save(1, DOWN);

        scheduler.remove(2);

        assertEquals(6, scheduler.next(2, UP));

        scheduler.remove(6);
        scheduler.save(5, UP);

        assertEquals(4, scheduler.next(6, UP));

        scheduler.remove(4);

        assertEquals(1, scheduler.next(4, DOWN));

        scheduler.remove(1);

        assertEquals(5, scheduler.next(1, DOWN));

        scheduler.remove(5);

        assertEquals(-1, scheduler.next(1, DOWN));
    }

    // L'ascenseur est au 1er étage, monte vers le cinquième et une requête enregistrée à la montée au 3ème étage
    @Test
    public void upThreeToFiveRequestTest(){
        Scheduler scheduler = new Scheduler();
        scheduler.save(1, UP);
        scheduler.save(5, UP);

        assertEquals(1, scheduler.next(0, UP));

        scheduler.remove(1);

        assertEquals(5, scheduler.next(1, UP));

        scheduler.save(3, UP);

        assertEquals(3, scheduler.next(2, UP));

        scheduler.remove(3);

        assertEquals(5, scheduler.next(1, UP));

        scheduler.remove(5);

        assertEquals(-1, scheduler.next(5, UP));
    }

    // L'ascenseur est au 3ème, à la montée et un utilisateur en cabine souhaite aller au RDC
    @Test
    public void upThreeToZeroRequestTest() {
        Scheduler scheduler = new Scheduler();
        scheduler.save(3, UP);

        assertEquals(3, scheduler.next(3, UP));

        scheduler.save(0, DOWN);
        scheduler.remove(3);

        assertEquals(0, scheduler.next(3, UP));

        scheduler.remove(0);

        assertEquals(-1, scheduler.next(0, DOWN));
    }




}
