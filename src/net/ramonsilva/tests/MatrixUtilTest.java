package net.ramonsilva.tests;

import net.ramonsilva.util.MatrixUtil;
import org.junit.Test;

import static org.junit.Assert.*;


public class MatrixUtilTest {

    @Test
    public void testDeterminant(){
        int DETERMINANT_EXPECTED = 15;
        double[][] data = { { 1, 2, 1 }, { 2, -1, 1 }, { 3, 1, -1} };
        double[] indepentendTerms = {8,3,2};

        assertEquals(DETERMINANT_EXPECTED, MatrixUtil.getMatrixDeterminant(data), 0.0);

    }

}