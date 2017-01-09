package net.ramonsilva.tests;

import net.ramonsilva.CSRMatrix;
import net.ramonsilva.solver.CSRMatrixSolver;
import net.ramonsilva.solver.LUDWithCSR;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 08/01/17.
 */
public class LUDWithCSRTest {

    private static final double EPSILON = 1e-10;

    @Test
    public void testLUSolverFourByFour(){
        double[][] data = {
                { 2, -1, 4, 1},
                { 3, 2, 0, -1 },
                { 1, 2, 0, 2},
                { 1 ,1 , 2 , 0}
        };

        double[] indepentendTerms = {-2, -3 , 10, 2};

        CSRMatrix m = new CSRMatrix(data, indepentendTerms);

        CSRMatrixSolver solver = new LUDWithCSR();
        double[] solution = solver.solve(m);

        /*assertEquals(-2, solution[0], EPSILON);
        assertEquals(3, solution[1], EPSILON);
        assertEquals(0.5, solution[2], EPSILON);
        assertEquals(3, solution[3], EPSILON);*/
    }
}
