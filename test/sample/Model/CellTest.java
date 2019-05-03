package sample.Model;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CellTest {


    @Test
    public void isAliveFalseWhenCellBorn() {

        //when
        Cell newCell = new Cell();

        //then
        assertFalse(newCell.isAlive());
    }


    @Test
    public void isSetAliveWorking() {

        // when
        Cell trial = new Cell();

        //then
        trial.setAlive(true);

        assertTrue(trial.isAlive());

    }



}
