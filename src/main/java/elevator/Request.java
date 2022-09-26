package elevator;

public class Request implements Comparable<Request>{

    private IElevator.State state;
    private int floor;

    public Request(IElevator.State state, int floor) {
        this.state = state;
        this.floor = floor;
    }

    public IElevator.State getState() {
        return this.state;
    }

    public int getFloor() {
        return this.floor;
    }

    @Override
    public int compareTo(Request request) {
        return this.floor - request.getFloor();
    }
}
