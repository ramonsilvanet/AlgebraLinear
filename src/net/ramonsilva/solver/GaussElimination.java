package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

public class GaussElimination implements MatrixSolver {

    private static final double EPSILON = 1e-10;

    @Override
    public double[] solve(Matrix matrix) {

        int LINES = matrix.getLines();
        int COLUMNS = matrix.getColumns();
        int TERMS = matrix.getIndependentTerms().length;

        if(LINES != COLUMNS || COLUMNS != TERMS){
            throw new RuntimeException("Invalid matrix");
        }

        double[][] A = matrix.getData();
        double[] b   = matrix.getIndependentTerms();

        for (int p = 0; p < TERMS; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < TERMS; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new RuntimeException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < TERMS; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < TERMS; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[TERMS];
        for (int i = TERMS - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < TERMS; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    private double[] applyBacksubstituition(double[][] A, double[] b) {
        double[] solution = new double[A.length];

        for (int j = A.length - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < A.length; k++)
                t += A[j][k] * solution[k];
            solution[j] = (b[j] - t) / A[j][j];
        }

        return solution;
    }
}
