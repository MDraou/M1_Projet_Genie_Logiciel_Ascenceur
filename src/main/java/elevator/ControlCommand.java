package elevator;

import static elevator.IElevator.State.*;

public class ControlCommand implements IControlCommand {

    private final Scheduler scheduler;
    private final IElevator elevator;
    private final IPanel panel;
    private int floor = 0, next = -1;
    private IElevator.State dir = UP;

    public ControlCommand(Scheduler scheduler, IElevator elevator, IPanel panel) {
        this.scheduler = scheduler;
        this.elevator = elevator;
        this.panel = panel;
    }

    @Override
    public void saveUp(int floor) {
        scheduler.save(floor, UP);
        panel.setUpLight(floor, true);
    }

    @Override
    public void saveDown(int floor) {
        scheduler.save(floor, DOWN);
        panel.setDownLight(floor, true);
    }

    @Override
    public void saveCabin(int floor) {
        if (floor > this.floor) scheduler.save(floor, UP);
        else if (floor < this.floor) scheduler.save(floor, DOWN);
        else scheduler.save(floor, dir);
        panel.setFloorLight(floor, true);
    }

    @Override
    public void checkState() {
        next = scheduler.next(floor, dir);
        if (elevator.getAndResetStageSensor()) floor += (dir == UP) ? 1 : -1;
        if (panel.getAndResetStopButton()) elevator.halt();
        if (panel.getAndResetInitButton() && (elevator.getState() == STOP || elevator.getState() == ERROR))
            elevator.reset();
    }

    @Override
    public void checkAndProcess() {
        checkState();
        switch (elevator.getState()) {
            case UP -> { if (next - floor == 1) elevator.stopNext(); }
            case DOWN -> { if (floor - next == 1) elevator.stopNext(); }
            case STOP -> {
                if (next == -1) break ;
                if (next == floor) elevator.openDoor();
                if (next > floor) { elevator.up(); dir = UP; }
                if (next < floor) { elevator.down(); dir = DOWN; }
            }
            case RESET -> { dir = DOWN; scheduler.removeAll(); floor = 0; }
            case DOOR -> { scheduler.remove(floor); switchOffLights(floor); }
            case ERROR -> scheduler.removeAll();
        }
        try { Thread.sleep(200); } catch (InterruptedException e) { throw new RuntimeException(e); }
    }

    public int getFloor() {
        return floor;
    }

    public IElevator.State getElevatorState() {
        return elevator.getState();
    }

    public int getNextRequest() {
        return scheduler.next(floor, dir);
    }

    private void switchOffLights(int floor) {
        panel.setFloorLight(floor, false);
        panel.setUpLight(floor, false);
        panel.setDownLight(floor, false);
    }
}
