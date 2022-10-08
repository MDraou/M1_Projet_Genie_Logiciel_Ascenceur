package elevator;

public class MainElevator {
	
	static public void main(String args[]) {
		int nbFloor = 4;
		var cal = new IHM(nbFloor, new ElevatorSimulator(nbFloor, true), new PanelSimulator(nbFloor), new Scheduler());
		cal.run();
	}

}
