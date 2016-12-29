package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.DescendGradient;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 26/12/16.
 */
public class DescendGradientTest {

    private static final double EPSILON = 0.05;
    private static final int LIMIT = 2000;

    @Test
    public void testDescendGradientSolverThreeByThree(){
        double[][] data = { { 10, 2, -1 }, { 1, 5, 1 }, { 2, 3, 10} };
        double[] indepentendTerms = {7, -8, 6};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new DescendGradient(LIMIT);
        double[] solution = solver.solve(m);

        assertEquals(1.2, solution[0], EPSILON);
        assertEquals(-2.0, solution[1], EPSILON);
        assertEquals(1.0, solution[2], EPSILON);
    }

    @Test
    public void testDescentGradientRuleSolver(){
        double[][] data = { { 1, -1, 2}, { -1, 5, -4}, { 2, -4, 6} };
        double[] indepentendTerms = {0, 1 , 0};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new DescendGradient(LIMIT);
        double[] solution = solver.solve(m);

        assertEquals(-0.5, solution[0], EPSILON);
        assertEquals(0.5, solution[1], EPSILON);
        assertEquals(0.5, solution[2], EPSILON);
    }

}
