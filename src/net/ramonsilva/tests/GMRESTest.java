package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.GMRES;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class GMRESTest {

    private static final double EPSILON = 0.2;

    @Test
    public void testSolverThreeByThree(){
        double[][] data = { { 10, 2, -1 }, { 1, 5, 1 }, { 2, 3, 10} };
        double[] indepentendTerms = {7, -8, 6};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new GMRES(50);
        double[] solution = solver.solve(m);

        assertEquals(1.2, solution[0], EPSILON);
        assertEquals(-2.0, solution[1], EPSILON);
        assertEquals(1.0, solution[2], EPSILON);
    }

}
