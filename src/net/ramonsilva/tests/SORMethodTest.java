package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.GaussJacobi;
import net.ramonsilva.solver.MatrixSolver;
import net.ramonsilva.solver.SORMethod;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ramonsilva on 24/10/16.
 */
public class SORMethodTest {
        private static final double EPSILON = 0.05;

        @Test
        public void testSolverThreeByThree(){
            double[][] data = { { 9, 4, 0 }, { 4, 9, 1 }, { 0, -1, 9} };
            double[] indepentendTerms = {20, 12, 51};
            Matrix m = new Matrix(data, indepentendTerms);

            MatrixSolver solver = new SORMethod(2000);
            double[] solution = solver.solve(m);

            assertEquals(1.2, solution[0], EPSILON);
            assertEquals(-2.0, solution[1], EPSILON);
            assertEquals(1.0, solution[2], EPSILON);
        }

}
