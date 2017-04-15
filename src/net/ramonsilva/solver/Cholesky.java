package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

public class Cholesky implements MatrixSolver {
    @Override
    public double[] solve(Matrix matrix) {

        int LINES = matrix.getLines();
        int COLUMNS = matrix.getColumns();
        int TERMS = matrix.getIndependentTerms().length;

        double[][] A = matrix.getData();
        double[] b   = matrix.getIndependentTerms();

        double EPSILON = 0.0001;

        if (!MatrixUtil.isSquare(A)) {
            throw new RuntimeException("Matrix não é quadrada");
        }

        //if (!MatrixUtil.isSymmetric(A)) {
        //    throw new RuntimeException("Matrix nao é simetrica");
        //}

        double [][] LOWER = new double[LINES][COLUMNS];
        for(int i = 0 ; i < COLUMNS; i++){
            for(int j = 0; j <= i; j++){

                double sum = 0.0;
                for(int k = 0;k < j; k++){
                    sum += LOWER[i][k] * LOWER[i][k];
                }

                if(i == j){
                    LOWER[i][i] = Math.sqrt(A[i][i] - sum);
                } else {
                    double denominator = LOWER[j][j] * (A[i][j] - sum);
                    if(denominator != 0) {
                        LOWER[i][j] = 1.0 / LOWER[j][j] * (A[i][j] - sum);
                    } else {
                        LOWER[i][j] = 0.0;
                    }
                }
            }

            if(LOWER[i][i] <= 0){
                throw new RuntimeException("Matrix is not positive definte");
            }
        }

        double[][] TRANSPOSED = MatrixUtil.transpose(LOWER);

        //  Perform substitution Ly=v
        double[] y = new double[TERMS];
        for(int i = 0; i < TERMS; i++) {
            double sum = 0.0;

            for(int k = 0; k < i; k++){
                sum += y[k] * LOWER[i][k];
            }

            if(LOWER[i][i] < EPSILON) {
                y[i] = 0.0;
            } else {
                y[i] = (b[i] - sum) / LOWER[i][i];
            }
        }

       //Back substituition
        double[] x = new double[TERMS];

        for (int i = TERMS - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < TERMS; j++) {
                sum += TRANSPOSED[i][j] * x[j];
            }

            if(TRANSPOSED[i][i] < EPSILON) {
                x[i] = 0.0;
            } else {
                x[i] = (y[i] - sum) / TRANSPOSED[i][i];
            }
        }

        return x;
    }
}
