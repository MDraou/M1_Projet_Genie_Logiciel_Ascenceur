package panel;

import elevator.PanelSimulator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestLightPanel {

    PanelSimulator panel = new PanelSimulator(1);

    @Test
    public void testFloorLight(){
        assertFalse(panel.getFloorLight(0));
        panel.setFloorLight(0, true);
        assertTrue(panel.getFloorLight(0));
        panel.setFloorLight(0, false);
        assertFalse(panel.getFloorLight(0));
    }

    @Test
    public void testUpLight(){
        assertFalse(panel.getUpLight(0));
        panel.setUpLight(0, true);
        assertTrue(panel.getUpLight(0));
        panel.setUpLight(0, false);
        assertFalse(panel.getUpLight(0));
    }

    @Test
    public void testDownLight(){
        assertFalse(panel.getDownLight(0));
        panel.setDownLight(0, true);
        assertTrue(panel.getDownLight(0));
        panel.setDownLight(0, false);
        assertFalse(panel.getDownLight(0));
    }
}
