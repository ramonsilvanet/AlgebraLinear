package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.Cholesky;
import net.ramonsilva.solver.ConjugateGradient;
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
    public void testCholeskySolverThreeByThree(){
        double[][] data = { { 1, 1, 0 }, { 1, 2, -1 }, { 0, -1, 3} };
        double[] indepentendTerms = {2, 1, 5};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new Cholesky();
        double[] solution = solver.solve(m);

        assertEquals(1, solution[0], EPSILON);
        assertEquals(1, solution[1], EPSILON);
        assertEquals(2, solution[2], EPSILON);
    }

    @Test
    public void testCholesky(){
        double[][] data = { { 2, -1, 0 }, { -1, 2, -1 }, { 0, -1, 2} };
        double[] indepentendTerms = {8,3,2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new Cholesky();
        double[] solution = solver.solve(m);

        assertEquals(8, solution[0], EPSILON);
        assertEquals(8, solution[1], EPSILON);
        assertEquals(5, solution[2], EPSILON);
    }

}
