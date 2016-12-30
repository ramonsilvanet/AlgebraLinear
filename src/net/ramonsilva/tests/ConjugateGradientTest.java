package net.ramonsilva.tests;

import net.ramonsilva.Matrix;
import net.ramonsilva.solver.ConjugateGradient;
import net.ramonsilva.solver.MatrixSolver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConjugateGradientTest {

    private static final double EPSILON = 0.05;
    private static final int LIMIT = 500;

    @Test
    public void testConjugateGradientThreebyThree(){
        double[][] data = { { 2, -1, 0 }, { -1, 2, -1 }, { 0, -1, 2} };
        double[] indepentendTerms = {8,3,2};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new ConjugateGradient(LIMIT);
        double[] solution = solver.solve(m);

        assertEquals(8, solution[0], EPSILON);
        assertEquals(8, solution[1], EPSILON);
        assertEquals(5, solution[2], EPSILON);
    }

    @Test
    public void testConjugateGradientSolver(){
        double[][] data = { { 1, -1, 2}, { -1, 5, -4}, { 2, -4, 6} };
        double[] indepentendTerms = {0, 1 , 0};
        Matrix m = new Matrix(data, indepentendTerms);

        MatrixSolver solver = new ConjugateGradient(LIMIT);
        double[] solution = solver.solve(m);

        assertEquals(-0.5, solution[0], EPSILON);
        assertEquals(0.5, solution[1], EPSILON);
        assertEquals(0.5, solution[2], EPSILON);
    }



}
