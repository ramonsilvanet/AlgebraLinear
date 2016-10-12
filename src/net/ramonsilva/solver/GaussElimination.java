package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.Matriz;
import net.ramonsilva.util.MatrixUtil;

public class GaussElimination implements MatrixSolver {

    @Override
    public double[] solve(Matrix matrix) {

        int LINES = matrix.getLines();
        int COLUMNS = matrix.getColumns();
        int TERMS = matrix.getIndependentTerms().length;

        if(LINES != COLUMNS || COLUMNS != TERMS){
            throw new RuntimeException("Invalid matrix");
        }

        double[][] A = matrix.getData();
        double[][] b = new double[1][LINES];
        b[0] = matrix.getIndependentTerms();

        for(int i = 0; i < COLUMNS; i++){
            int pivot = i;

            for (int j = i + 1; j < LINES; j++){
                if(Math.abs(A[i][j]) > Math.abs(A[pivot][j])){
                    pivot = j;
                }
            }

            A = MatrixUtil.swapRows(A, i, pivot);
            b = MatrixUtil.swapRows(b, i, pivot);

            if(A[i][i] == 0.0) throw new RuntimeException("Singular Matrix");

            //Pivot B
            for(int j = i + 1; i < LINES; j++){
                b[0][j] -= b[0][i] * A[j][i] / A[i][i];
            }

            //Pivot A
            for (int j = i + 1; j < LINES; j++) {
                double m = A[j][i] / A[i][i];
                for (int k = i+1; k < LINES; k++) {
                    A[j][k] -= A[i][k] * m;
                }
                //Desnecessário zerar a parte debaixo da matrix
                //A[j][i] = 0.0;
            }
        }

        return applyBacksubstituition(A, b);
    }

    private double[] applyBacksubstituition(double[][] A, double[][] b) {
        double[] solution = new double[A.length];

        for (int j = A.length - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < A.length; k++)
                t += A[j][k] * solution[k];
            solution[j] = (b[0][j] - t) / A[j][j];
        }

        return solution;
    }
}
