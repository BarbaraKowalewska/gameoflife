package sample.Controller;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MainControllerTest {


    @Test

    public void lonelyCellDies() {

        assertFalse(MainController.cellShouldLiveInNextGeneration(true, 0));
        assertFalse(MainController.cellShouldLiveInNextGeneration(true, 1));
        assertTrue(MainController.cellShouldLiveInNextGeneration(true, 2));

    }

    @Test

    public void cellDiesOverpopulation() {

        assertFalse(MainController.cellShouldLiveInNextGeneration(true, 5));
        assertFalse(MainController.cellShouldLiveInNextGeneration(true, 4));
    }

    @Test

    public void cellBorn() {

        assertTrue(MainController.cellShouldLiveInNextGeneration(false, 3));
        assertFalse(MainController.cellShouldLiveInNextGeneration(false, 4));

    }

    @Test

    public void cellStayTheSame() {

        assertTrue(MainController.cellShouldLiveInNextGeneration(true, 3));
        assertTrue(MainController.cellShouldLiveInNextGeneration(true, 2));
        assertFalse(MainController.cellShouldLiveInNextGeneration(false, 2));

    }
}
