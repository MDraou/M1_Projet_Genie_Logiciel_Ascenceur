package panel;

import elevator.PanelSimulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestButtonPanel {

    PanelSimulator panel = new PanelSimulator(1);

    @Test
    public void testInitButton() {
        assertFalse(panel.getAndResetInitButton());
        panel.pressInitButton();
        assertTrue(panel.getAndResetInitButton());
        assertFalse(panel.getAndResetInitButton());
    }

    @Test
    public void testStopButton() {
        assertFalse(panel.getAndResetStopButton());
        panel.pressStopButton();
        assertTrue(panel.getAndResetStopButton());
        assertFalse(panel.getAndResetStopButton());
    }

    @Test
    public void testFloorButton() {
        assertFalse(panel.getAndResetFloorButton(0));
        panel.pressFloorButton(0);
        assertTrue(panel.getAndResetFloorButton(0));
        assertFalse(panel.getAndResetFloorButton(0));
    }

    @Test
    public void testUpButton() {
        assertFalse(panel.getAndResetUpButton(0));
        panel.pressUpButton(0);
        assertTrue(panel.getAndResetUpButton(0));
        assertFalse(panel.getAndResetUpButton(0));
    }

    @Test
    public void testDownButton() {
        assertFalse(panel.getAndResetDownButton(0));
        panel.pressDownButton(0);
        assertTrue(panel.getAndResetDownButton(0));
        assertFalse(panel.getAndResetDownButton(0));
    }
}
