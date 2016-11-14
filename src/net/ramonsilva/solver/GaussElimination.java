package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.Algorithms;

public class GaussElimination implements MatrixSolver {

    private static final double EPSILON = 1e-10;

    @Override
    public double[] solve(Matrix matrix) {

        int LINES = matrix.getLines();
        int COLUMNS = matrix.getColumns();
        int TERMS = matrix.getIndependentTerms().length;

        if(LINES != COLUMNS || COLUMNS != TERMS){
            throw new RuntimeException("Matrix com dimensoes invalidas");
        }

        double[][] A = matrix.getData();
        double[] b   = matrix.getIndependentTerms();

        for (int p = 0; p < TERMS; p++) {

            int max = p;
            for (int i = p + 1; i < TERMS; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }
            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
            double   t    = b[p]; b[p] = b[max]; b[max] = t;


            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new RuntimeException("Matrix Singular");
            }

            for (int i = p + 1; i < TERMS; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < TERMS; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        return Algorithms.backSubstitution(LINES-1, A, b);
    }
}
