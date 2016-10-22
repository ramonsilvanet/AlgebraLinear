package net.ramonsilva.util;

import net.ramonsilva.Matrix;

/**
 * Created by ramonsilva on 10/10/16.
 */
public class MatrixUtil {

    public static double getMatrixDeterminant(double[][] data){
        int numLines = data.length;
        double determinant = 0;

        if(numLines  == 1){
            determinant = data[0][0];
        } else if(numLines == 2) {
           determinant = data[0][0] * data[1][1] - data[0][1] * data[1][0];
        } else {
            for(int j1=0;j1<numLines;j1++)
            {
                double[][] m = new double[numLines-1][];
                for(int k=0;k<(numLines-1);k++)
                {
                    m[k] = new double[numLines-1];
                }
                for(int i=1;i<numLines;i++)
                {
                    int j2=0;
                    for(int j=0;j<numLines;j++)
                    {
                        if(j == j1)
                            continue;
                        m[i-1][j2] = data[i][j];
                        j2++;
                    }
                }
                determinant += Math.pow(-1.0,1.0+j1+1.0)* data[0][j1] * getMatrixDeterminant(m);
            }
        }

        return determinant;
    }

    public static double[][] transpose(double data[][]){
        int numLines = data.length;
        int numCols = data[0].length;

        double[][] transposeMatrix = new double[numLines][numCols];

        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numLines; j++) {
                transposeMatrix[j][i] = data[i][j];
            }
        }

        return transposeMatrix;
    }

    public static double[][] swapColumn(double[][] data, double[] colunm, int position) {
        double[][] m  = new double[data.length][data[0].length];

        //Clonar a matrix
        for (int j = 0 ; j < data.length; j++){
            m[j] = data[j].clone();
        }

        for(int i =0; i < data.length; i++){
            m[i][position] = colunm[i];
        }

        return m;
    }


    public static double[][] swapRows(double[][] matrix,  int upline, int downline){
        double[] temp = matrix[upline];
        matrix[upline] = matrix[downline];
        matrix[downline] = temp;

        return matrix;
    }

    public static double[] swapRows(double[] matrix,  int upline, int downline){
        double temp = matrix[upline];
        matrix[upline] = matrix[downline];
        matrix[downline] = temp;

        return matrix;
    }

    public static Matrix getArgumentedMatrix(Matrix m){

        double[] zeroEquallity = new double[m.getLines()];
        double[][] argumented = new double[m.getLines()][m.getColumns()+1];
        double[][] data = m.getData();
        double[] independenterms = m.getIndependentTerms();

        for(int i = 0; i < m.getLines(); i++){
            for (int j = 0 ; j < m.getColumns(); j++){
                argumented[i][j] = data[i][j];
            }

            argumented[i][argumented.length] = independenterms[i];
        }

        Matrix ma = new Matrix(argumented, zeroEquallity);
        return ma;
    }

    // is symmetric
    public static boolean isSymmetric(double[][] A) {
        int N = A.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (A[i][j] != A[j][i]) return false;
            }
        }
        return true;
    }

    // is symmetric
    public static boolean isSquare(double[][] A) {
        int N = A.length;
        for (int i = 0; i < N; i++) {
            if (A[i].length != N) return false;
        }
        return true;
    }
}
