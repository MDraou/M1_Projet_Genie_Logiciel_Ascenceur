package elevator;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class IHM {

    private final JButton[] upButtons, downButtons, floorButtons;

    private final Label floorLabel = new Label("0"),
                  nextRequestLabel = new Label("-1"),
                  positionLabel = new Label("0"),
                  stateLabel = new Label("STOP");
    private final int nbFloor;
    private final ControlCommand controlCommand;
    private final ElevatorSimulator elevator;
    private final PanelSimulator panel;
    private final Scheduler scheduler;

    private boolean isRunning = true;

    public IHM(int nbFloor, ElevatorSimulator elevator, PanelSimulator panel, Scheduler scheduler) {
        this.nbFloor = nbFloor;
        this.controlCommand = new ControlCommand(scheduler, elevator, panel);
        this.elevator = elevator;
        this.panel = panel;
        this.scheduler = scheduler;
        this.upButtons = new JButton[nbFloor + 1];
        this.downButtons = new JButton[nbFloor + 1];
        this.floorButtons = new JButton[nbFloor + 1];
    }

    /*
     * Créer un bouton et lui associer une action
     */
    private JButton newFloorButton(int floor) {
        JButton button = new JButton(String.valueOf(floor));
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(e -> { panel.pressFloorButton(floor); controlCommand.saveCabin(floor); });
        return button;
    }

    private JButton newUpButton(int floor) {
        JButton button = new JButton("^");
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(e -> { panel.pressUpButton(floor); controlCommand.saveDown(floor); });
        return button;
    }

    private JButton newDownButton(int floor) {
        JButton button = new JButton("v");
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(e -> { panel.pressUpButton(floor); controlCommand.saveDown(floor); });
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
        for (int i = nbFloor; i >= 0; i--) {
            floorButtons[i] = newFloorButton(i);
            panelIn.add(floorButtons[i]);
        }
        if (nbFloor % 2 == 0) panelIn.add(new Container());
        panelIn.add(newHaltButton());
        panelIn.add(newResetButton());
        return panelIn;
    }

    private JPanel createPanelOut() {
        JPanel panelOut = new JPanel(new GridLayout(nbFloor + 1, 2));
        downButtons[nbFloor] = newDownButton(nbFloor);
        panelOut.add(downButtons[nbFloor]);
        panelOut.add(new Container());
        for (int i = nbFloor - 1; i > 0; i--) {
            upButtons[i] = newUpButton(i);
            downButtons[i] = newDownButton(i);
            panelOut.add(downButtons[i]);
            panelOut.add(upButtons[i]);
        }
        panelOut.add(new Container());
        upButtons[0] = newUpButton(0);
        panelOut.add(upButtons[0]);
        return panelOut;
    }

    private Label newLabel(String message) {
        Label label = new Label(message);
        label.setFont(new Font(null, Font.PLAIN, 50));
        label.setAlignment(Label.CENTER);
        return label;
    }

    /*
     * Construire l'IHM de l'ascenceur
     */

    private void createWindows() {
        JFrame frame = new JFrame();
        floorLabel.setFont(new Font(null, Font.PLAIN, 100));
        floorLabel.setAlignment(Label.CENTER);
        nextRequestLabel.setFont(new Font(null, Font.PLAIN, 100));
        nextRequestLabel.setAlignment(Label.CENTER);
        positionLabel.setFont(new Font(null, Font.PLAIN, 100));
        positionLabel.setAlignment(Label.CENTER);
        stateLabel.setFont(new Font(null, Font.PLAIN, 50));
        stateLabel.setAlignment(Label.CENTER);
        JPanel panelIn = createPanelIn();
        JPanel panelOut = createPanelOut();
        JPanel panel = new JPanel(new GridLayout(3, 3));

        panel.add(newLabel("Level"));
        panel.add(newLabel("Position"));
        panel.add(newLabel("Next"));
        panel.add(floorLabel);
        panel.add(positionLabel);
        panel.add(nextRequestLabel);
        panel.add(panelIn);
        panel.add(stateLabel);
        panel.add(panelOut);
        frame.setSize(700, 700);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { isRunning = false; }
        });
    }

    private void checkLight() {
        for (int i = 0 ; i <= nbFloor ; i++) {
            if (panel.getUpLight(i) && i < nbFloor) upButtons[i].setBackground(Color.YELLOW);
            else if (i < nbFloor) upButtons[i].setBackground(Color.LIGHT_GRAY);
            if (panel.getDownLight(i) && i > 0) downButtons[i].setBackground(Color.YELLOW);
            else if (i > 0) downButtons[i].setBackground(Color.LIGHT_GRAY);
            if (panel.getFloorLight(i)) floorButtons[i].setBackground(Color.YELLOW);
            else floorButtons[i].setBackground(Color.LIGHT_GRAY);
        }
    }

    public void run() {
        createWindows();
        while (isRunning) {
            controlCommand.checkAndProcess();
            checkLight();
            floorLabel.setText(String.valueOf(controlCommand.getFloor()));
            stateLabel.setText(String.valueOf(controlCommand.getElevatorState()));
            positionLabel.setText(String.valueOf(Math.round(elevator.getLevel() * 10.0) / 10.0));
            nextRequestLabel.setText(String.valueOf(controlCommand.getNextRequest()));
            elevator.oneStep();
        }
    }
}