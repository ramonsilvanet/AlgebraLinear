package net.ramonsilva.solver;

import net.ramonsilva.Matrix;

/**
 * Created by ramonsilva on 10/10/16.
 */
public class LUDecomposite implements MatrixSolver {
    @Override
    public double[] solve(Matrix matrix) {

        int LINES = matrix.getLines();
        int COLUMNS = matrix.getColumns();
        int TERMS = matrix.getIndependentTerms().length;

        if(LINES != COLUMNS || COLUMNS != TERMS){
            throw new RuntimeException("Matrix com dimensoes invalidas");
        }


        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();

        double [][] LOWER = new double[LINES][COLUMNS];
        double[][] UPPER = new double[LINES][COLUMNS];

        //Fill Lower Main diagonal
        for(int i=0; i < LINES; i++){
            LOWER[i][i] = 1;
        }

        //Fill upper
        for(int i=0; i< LINES; i++){
            for(int j = 0; j < COLUMNS; j++){
                UPPER[i][j] = A[i][j];
            }
        }

        //Find LU matrices
        for(int i = 0 ; i < LINES; i++){

            //Subtract lines
            for (int k = i + 1; k < LINES; k++){
                double c = (UPPER[k][i]) / UPPER[i][i];
                LOWER[k][i] = c; // Store the coeficient

                //System.out.println(i + " " + k + "    " +  UPPER[k][i] + " / " + UPPER[i][i] + " : " + c);

                for(int j = i ; j < LINES; j++){
                    UPPER[k][j] -= c * UPPER[i][j]; // Multiply with the pivot line and subtract
                }
            }

            //Zerofill belloow this row
            for(int k  = i + 1 ; k < LINES; k++){
                UPPER[k][i] = 0;
            }
        }

        //  Perform substitution Ly=v
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
                sum += UPPER[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / UPPER[i][i];
        }

        return x;
    }
}
