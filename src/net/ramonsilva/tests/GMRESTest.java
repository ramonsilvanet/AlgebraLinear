package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.GMRES;
import net.ramonsilva.solver.MatrixSolver;
import net.ramonsilva.util.VectorUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class GMRESTest {

    private static final double EPSILON = 0.5;

    @Test
    public void testSolverThreeByThree(){
        double[][] data = { { 2, -1, 4, 1}, { 3, 2, 0, -1 }, { 1, 2, 0, 2}, { 1 ,1 , 2 , 0} };
        double[] indepentendTerms = {-2, -3 , 10, 2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new GMRES(50);
        double[] solution = solver.solve(m);

        assertEquals(-2, solution[0], EPSILON);
        assertEquals(3, solution[1], EPSILON);
        assertEquals(0.5, solution[2], EPSILON);
        assertEquals(3, solution[3], EPSILON);
    }

}
