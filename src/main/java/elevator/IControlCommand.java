package elevator;

public interface IControlCommand {

    void saveUp(int floor);
    void saveDown(int floor);
    void saveCabin(int floor);
    void checkState();
    void checkAndProcess();
}
