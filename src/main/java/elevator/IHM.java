package elevator;

import java.awt.*;

import javax.swing.*;

public class IHM {

    private Label floorLabel, requestsLabel;
    private final int nbFloor;
    private final ControlCommand controlCommand;
    private final ElevatorSimulator elevator;
    private final PanelSimulator panel;
    private final Scheduler scheduler;

    public IHM(int nbFloor, ElevatorSimulator elevator, PanelSimulator panel, Scheduler scheduler) {
        this.nbFloor = nbFloor;
        this.controlCommand = new ControlCommand(scheduler, elevator, panel);
        this.elevator = elevator;
        this.panel = panel;
        this.scheduler = scheduler;
    }

    /*
     * Créer un bouton et lui associer une action
     */
    private JButton newFloorButton(int floor) {
        JButton button = new JButton(String.valueOf(floor));
        button.addActionListener(e -> controlCommand.saveCabin(floor));
        return button;
    }

    private JButton newUpButton(int floor) {
        JButton button = new JButton("^");
        button.addActionListener(e -> controlCommand.saveDown(floor));
        return button;
    }

    private JButton newDownButton(int floor) {
        JButton button = new JButton("v");
        button.addActionListener(e -> controlCommand.saveDown(floor));
        return button;
    }

    private JButton newHaltButton() {
        JButton button = new JButton("H");
        button.addActionListener(e -> panel.pressStopButton());
        return button;
    }

    private JButton newResetButton() {
        JButton button = new JButton("R");
        button.addActionListener(e -> panel.pressInitButton());
        return button;
    }

    private JPanel createPanelIn() {
        JPanel panelIn = new JPanel(new GridLayout(nbFloor + 2, 2));
        for (int i = nbFloor; i >= 0; i--) panelIn.add(newFloorButton(i));
        if (nbFloor % 2 == 0) panelIn.add(new Container());
        panelIn.add(newHaltButton());
        panelIn.add(newResetButton());
        return panelIn;
    }

    private JPanel createPanelOut() {
        JPanel panelOut = new JPanel(new GridLayout(nbFloor + 1, 2));
        panelOut.add(newDownButton(nbFloor));
        panelOut.add(new Container());
        for (int i = nbFloor - 1; i > 0; i--) {
            panelOut.add(newDownButton(i));
            panelOut.add(newUpButton(i));
        }
        panelOut.add(new Container());
        panelOut.add(newUpButton(0));
        return panelOut;
    }

    /*
     * Construire l'IHM de l'ascenceur
     */

    private void createWindows() {
        JFrame frame = new JFrame();
        floorLabel = new Label("0");
        floorLabel.setFont(new Font(null, Font.PLAIN, 100));
        JPanel panelIn = createPanelIn();
        JPanel panelOut = createPanelOut();
        JPanel panel = new JPanel(new GridLayout(2, 3));

        panel.add(floorLabel);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(panelIn);
        panel.add(new JPanel());
        panel.add(panelOut);
        frame.setSize(700, 700);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void run() {
        createWindows();
        while (true) {
            controlCommand.checkAndProcess();
            floorLabel.setText(String.valueOf(controlCommand.getFloor()));
            elevator.oneStep();
        }
    }
}