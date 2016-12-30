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

    @Test
    public void testCholeskyFivebyFive(){
        double[][] data = {
                { 2.5791234, -0.31610442, -0.60484496, -0.7269189,  -0.4848828},
                { -0.3161044, 2.20509109, 0.09002619, 0.8176088, 1.0030809 },
                { -0.6048450, 0.09002619, 3.60547197, -0.4705657, -0.8378352 },
                { -0.7269189, 0.81760883, -0.47056571, 3.9656424, 0.2176908 },
                { -0.4848828, 1.00308087, -0.83783524, 0.2176908, 2.6446711}
        };
        double[] indepentendTerms = {5,4,3,2,1};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new Cholesky();
        double[] solution = solver.solve(m);

        assertEquals(8, solution[0], EPSILON);
        assertEquals(8, solution[1], EPSILON);
        assertEquals(5, solution[2], EPSILON);
    }

}
