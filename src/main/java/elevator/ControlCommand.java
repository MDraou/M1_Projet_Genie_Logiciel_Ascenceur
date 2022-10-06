package elevator;

import static elevator.IElevator.State.*;

public class ControlCommand implements IControlCommand {

    private final Scheduler scheduler;
    private final IElevator elevator;
    private final IPanel panel;
    private int floor = 0;
    private IElevator.State dir;

    public ControlCommand(Scheduler scheduler, IElevator elevator, IPanel panel) {
        this.scheduler = scheduler;
        this.elevator = elevator;
        this.panel = panel;
    }

    @Override
    public void saveUp() {

    }

    @Override
    public void saveDown() {

    }

    @Override
    public void saveStay() {

    }

    @Override
    public void checkState() {
        if (panel.getAndResetStopButton()) { elevator.halt(); scheduler.removeAll(); }
        if (panel.getAndResetInitButton()) {  elevator.reset(); }
    }

    @Override
    public void checkAndProcess() {
        checkState();
        switch (elevator.getState()) {
            case UP -> {
                if (elevator.getAndResetStageSensor()) floor += 1;
                if (scheduler.next(floor, elevator.getState()) - floor == 1) elevator.stopNext();
            }
            case DOWN -> {
                if (elevator.getAndResetStageSensor()) floor -= 1;
                if (floor - scheduler.next(floor, elevator.getState()) == 1) elevator.stopNext();
            }
            case STOP -> {
                if (scheduler.next(floor, dir) == floor) {
                    scheduler.remove(floor);
                    panel.setFloorLight(floor, panel.getAndResetFloorButton(floor));
                    elevator.openDoor();
                }
                if (scheduler.next(floor, elevator.getState()) > floor) { elevator.up(); dir = UP; }
                if (scheduler.next(floor, elevator.getState()) < floor) { elevator.down(); dir = DOWN; }
            }
            case ERROR -> {}
            case RESET -> {}
            case DOOR -> {}
        }
    }
}