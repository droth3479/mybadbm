package edu.touro.mco152.bm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DiskMarkTest {
    DiskMark diskMark = new DiskMark();

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    void setMarkNum(int markNum) {
        diskMark.setMarkNum(markNum);

        /*
        Right. I am asserting that the results are right.
        Cross check. I am using the getMarkNum to check that the setter method works.
         */
        assertEquals(markNum, diskMark.getMarkNum());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    void getCumMin(int cumMin) {
        diskMark.setCumMin(cumMin);

        /*
        Boundary Conditions. I am asserting that the method can operate with all integers.
        This tests for conformance, that it returns an int. Existence, that the value exists.
        Cardinality, that one value is returned.
         */
        assertEquals(cumMin, diskMark.getCumMin());
    }
}