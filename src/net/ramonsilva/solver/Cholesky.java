package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 22/10/16.
 */
public class Cholesky implements MatrixSolver {
    @Override
    public double[] solve(Matrix matrix) {

        int LINES = matrix.getLines();
        int COLUMNS = matrix.getColumns();
        int TERMS = matrix.getIndependentTerms().length;

        double[][] A = matrix.getData();
        double[] b   = matrix.getIndependentTerms();

        if (!MatrixUtil.isSquare(A)) {
            throw new RuntimeException("Matrix não é quadrada");
        }
        if (!MatrixUtil.isSymmetric(A)) {
            throw new RuntimeException("Matrix nao é simetrica");
        }

        double [][] LOWER = new double[LINES][COLUMNS];
        for(int k = 0 ; k < COLUMNS; k++){
            for(int i = k ; i < COLUMNS; i++){
                LOWER[k][k] -= Math.pow(LOWER[k][i], 2);
            }

            LOWER[k][k] = Math.sqrt(A[k][k] - LOWER[k][k]);

            for(int i = k+1 ; i < COLUMNS; i++){
                for(int j = 0; j < LINES; j++){
                    LOWER[i][k] -= LOWER[i][j] * LOWER[k][j];
                }

                LOWER[i][k] = (A[k][i] - LOWER[i][k])/ LOWER[k][k];
            }
        }

        double[][] TRANSPOSED = MatrixUtil.transpose(LOWER);

        //  Perform substitution Ly=b
        double[] y = new double[TERMS];
        for(int i = 0; i < TERMS; i++) {
            y[i] = b[i] / LOWER[i][i];

            for(int k = 0; k < i; k++){
                y[i] -= y[k] * LOWER[i][k];
            }
        }

        //Solve the upper matrix
        double[] x = new double[TERMS];

        for (int i = TERMS - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < TERMS; j++) {
                sum += TRANSPOSED[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / TRANSPOSED[i][i];
        }

        return x;
    }


}
