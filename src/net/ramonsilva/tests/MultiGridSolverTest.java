package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.Cholesky;
import net.ramonsilva.solver.GaussSiedel;
import net.ramonsilva.solver.MatrixSolver;
import net.ramonsilva.solver.MultiGrid;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 27/11/16.
 */
public class MultiGridSolverTest {


    private static final double EPSILON = 1e-10;

    @Test
    public void testMultiGridSolver(){
        double[][] data = {
                { 2, -1, 0, 0, 0, 0, 0, 0, 0},
                { -1, 2, -1, 0, 0, 0, 0, 0, 0},
                { 0, -1, 2, -1, 0, 0, 0, 0, 0},
                { 0, 0, -1, 2, -1, 0, 0, 0, 0},
                { 0, 0, 0, -1, 2, -1, 0, 0, 0},
                { 0, 0, 0, 0, -1, 2, -1, 0, 0},
                { 0, 0, 0, 0, 0, -1, 2, -1, 0},
                { 0, 0, 0, 0, 0, 0,-1, 2, -1},
                { 0, 0, 0, 0, 0, 0, 0, -1, 2}
        };

        double[] indepentendTerms = {2, 1, 5, 2, 1, 5, 2, 1, 5};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new MultiGrid();
        double[] solution = solver.solve(m);

        assertEquals(1, solution[0], EPSILON);
        assertEquals(1, solution[1], EPSILON);
        assertEquals(2, solution[2], EPSILON);
    }

    @Test
    public void testSolverThreeByThree(){
        double[][] data = { { 10, 2, -1 }, { 1, 5, 1 }, { 2, 3, 10} };
        double[] indepentendTerms = {7, -8, 6};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new MultiGrid();
        double[] solution = solver.solve(m);

        assertEquals(1.2, solution[0], EPSILON);
        assertEquals(-2.0, solution[1], EPSILON);
        assertEquals(1.0, solution[2], EPSILON);
    }

}
