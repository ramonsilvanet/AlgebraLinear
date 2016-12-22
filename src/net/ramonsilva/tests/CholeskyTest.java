package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.Cholesky;
import net.ramonsilva.solver.GaussElimination;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 22/10/16.
 */
public class CholeskyTest {
    private static final double EPSILON = 1e-10;

    @Test
    public void testCholeskySolver(){
        double[][] data = {
                { 16, 4,  4, -4},
                { 4, 10,  4,  2},
                { 4,  4,  6, -2},
                {-4,  2, -2,  4}
        };

        double[] indepentendTerms = {32, 26, 20, -6};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new Cholesky();
        double[] solution = solver.solve(m);

        assertEquals( 1, solution[0], EPSILON);
        assertEquals( 2, solution[1], EPSILON);
        assertEquals( 1, solution[2], EPSILON);
        assertEquals(-1, solution[3], EPSILON);
    }
}
