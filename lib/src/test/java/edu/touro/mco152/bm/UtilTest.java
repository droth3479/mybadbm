package edu.touro.mco152.bm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void randInt() {
        int rand = Util.randInt(0, 1);

        assertTrue(rand >= 0);
        assertTrue(rand <= 1);
    }

    /*
    Performance. I am ensuring that the method executes in less than 1.5 seconds.
     */
    @Test
    void randIntTime() {
        long startTime = System.nanoTime();
        Util.randInt(0, 1);
        long endTime = System.nanoTime();

        assertTrue((endTime-startTime)/1000000.0 < 1.5);
    }

    /*
    Error. I forced an Illegal Argument Exception by passing a min that is greater than the max,
    resulting in bound that is negative to be sent to rand within the method.
     */
    @Test
    void randIntError() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, ()->{
            Util.randInt(1000, 1);
        });
        assertEquals("bound must be positive", iae.getMessage());
    }

    @Test
    void displayString() {
        String result = Util.displayString(1.0);

        assertEquals("1", result);
    }
}