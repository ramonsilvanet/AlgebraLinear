package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.GaussElimination;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 12/10/16.
 */
public class GaussEliminationTest {
    @Test
    public void testCrammerRuleSolverTwoByTwo(){
        double[][] data = { { 3, 1}, { 2, 3} };
        double[] indepentendTerms = {9,13};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new GaussElimination();
        double[] solution = solver.solve(m);

        assertEquals(2, solution[0], 0.0);
        assertEquals(3, solution[1], 0.0);

    }

}
