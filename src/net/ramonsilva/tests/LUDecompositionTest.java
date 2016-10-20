package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.GaussElimination;
import net.ramonsilva.solver.LUDecomposite;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 17/10/16.
 */
public class LUDecompositionTest {
    private static final double EPSILON = 1e-10;

    @Test
    public void testLUSolverTwoByTwo(){
        double[][] data = { { 3, 1}, { 2, 3} };
        double[] indepentendTerms = {9,13};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new LUDecomposite();
        double[] solution = solver.solve(m);

        assertEquals(2, solution[0], EPSILON);
        assertEquals(3, solution[1], EPSILON);
    }

    @Test
    public void testLUSolverThreeByThree(){
        double[][] data = { { 1, 2, 1 }, { 2, -1, 1 }, { 3, 1, -1} };
        double[] indepentendTerms = {8,3,2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new LUDecomposite();
        double[] solution = solver.solve(m);

        assertEquals(1, solution[0], EPSILON);
        assertEquals(2, solution[1], EPSILON);
        assertEquals(3, solution[2], EPSILON);
    }

    @Test
    public void testLUSolverFourByFour(){
        double[][] data = { { 2, -1, 4, 1}, { 3, 2, 0, -1 }, { 1, 2, 0, 2}, { 1 ,1 , 2 , 0} };
        double[] indepentendTerms = {-2, -3 , 10, 2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new LUDecomposite();
        double[] solution = solver.solve(m);

        assertEquals(-2, solution[0], EPSILON);
        assertEquals(3, solution[1], EPSILON);
        assertEquals(0.5, solution[2], EPSILON);
        assertEquals(3, solution[3], EPSILON);
    }

}
