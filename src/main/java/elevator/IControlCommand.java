package elevator;

public interface IControlCommand {

    void saveUp();
    void saveDown();
    void saveStay();
    void checkState();
    void checkAndProcess();
}
