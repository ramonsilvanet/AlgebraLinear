package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.CrammerRule;
import net.ramonsilva.solver.MatrixSolver;
import net.ramonsilva.util.MatrixUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MatrixSolverTest {

    private MatrixSolver solver;
    double[][] data = { { 1, 2, 1 }, { 2, -1, 1 }, { 3, 1, -1} };
    double[] indepentendTerms = {8,3,2};
    private Matrix m;

    @Before
    public void setUp(){
        m = new Matrix(data, indepentendTerms);
    }

    @After
    public void tearDown(){
        m = null;
    }

    @Test
    public void testCrammerRuleSolver(){
        MatrixSolver solver = new CrammerRule();
        double[] solution = solver.solve(m);

        assertEquals(1, solution[0], 0.0);
        assertEquals(2, solution[1], 0.0);
        assertEquals(3, solution[2], 0.0);
    }

}
