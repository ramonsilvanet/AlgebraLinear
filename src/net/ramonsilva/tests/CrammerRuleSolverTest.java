package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.CrammerRule;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CrammerRuleSolverTest {

    @Test
    public void testCrammerRuleSolverTwoByTwo(){
        double[][] data = { { 3, 1}, { 2, 3} };
        double[] indepentendTerms = {9,13};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new CrammerRule();
        double[] solution = solver.solve(m);

        assertEquals(2, solution[0], 0.0);
        assertEquals(3, solution[1], 0.0);
    }

    @Test
    public void testCrammerRuleSolverThreeByThree(){
        double[][] data = { { 1, 2, 1 }, { 2, -1, 1 }, { 3, 1, -1} };
        double[] indepentendTerms = {8,3,2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new CrammerRule();
        double[] solution = solver.solve(m);

        assertEquals(1, solution[0], 0.0);
        assertEquals(2, solution[1], 0.0);
        assertEquals(3, solution[2], 0.0);
    }

    @Test
    public void testCrammerRuleSolverFourByFour(){
        double[][] data = { { 2, -1, 4, 1}, { 3, 2, 0, -1 }, { 1, 2, 0, 2}, { 1 ,1 , 2 , 0} };
        double[] indepentendTerms = {-2, -3 , 10, 2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new CrammerRule();
        double[] solution = solver.solve(m);

        assertEquals(-2, solution[0], 0.0);
        assertEquals(3, solution[1], 0.0);
        assertEquals(0.5, solution[2], 0.0);
        assertEquals(3, solution[3], 0.0);
    }
}
