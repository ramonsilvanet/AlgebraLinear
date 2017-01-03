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

    public static double[][] transpose(double data[]){
        int numLines = data.length;
        double[][] transposed = new double[numLines][1];

        for (int i = 0; i < numLines; i++){
            transposed[i][0] = data[i];
        }

        return transposed;
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

    // is square
    public static boolean isSquare(double[][] A) {
        int N = A.length;
        for (int i = 0; i < N; i++) {
            if (A[i].length != N) return false;
        }
        return true;
    }

    public static boolean isDiagonalDominant(double[][] a) {

        int N = a.length;

        for(int i = 0; i < N; i++){
            double sum = 0.0;
            double pivot = 0.0;

            for(int j = 0; j < N; j++){
                if(i == j){
                    pivot = Math.abs(a[i][i]);
                } else {
                    sum += Math.abs(a[i][j]);
                }
            }

            if( pivot <= sum) return false;
        }

        return true;
    }

    public static double[][] plus(double[][] a, double[][] b) {

        int N = a.length;
        double[][] c = new double[N][N];

        for (int i=0; i < N; ++i){
            for (int j = 0; j < N; ++j){
                c[i][j] = a[i][j] + b[i][j];
            }
        }

        return c;
    }

    public static double[][] multiply(double[][] a, double[][] b) {

        int l = a.length;
        int n = a[0].length;
        int m = b.length;

        double[][] c = new double[l][m];

        for (int i=0; i<l; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int k = 0; k < m; ++k) {
                    c[i][k] += a[i][k] * b[k][j];
                }
            }
        }

        return c;
    }

    public static double[] multiplyMatrixByVector(double[][] a, double[] c) {

        int M = a.length;
        int N = c.length;

        double[] y = new double[M];

        for(int i = 0 ; i < M; i++){
            for(int j = 0 ; j < N ; j++){
                y[i] += a[i][j] * c[j];
            }
        }

        return y;
    }

    public static double[] multiplyMatrixByMatrix(double[][] a, double[][] c) {

        int M = a.length;
        int N = c.length;

        double[] y = new double[M];

        for(int i = 0 ; i < M; i++){
            for(int j = 0 ; j < N ; j++){
                y[i] += a[i][j] * c[i][j];
            }
        }

        return y;
    }

    public static double[] subtractVectors(double[] x, double[] y) {
        int N = x.length;
        double[] z = new double[N];


        for(int i = 0; i < N; i++){
            z[i] = x[i] - y[i];
        }

        return z;

    }

    public static double[] multiplyScalarByVector(double scalar, double[] vector){
        int N = vector.length;
        double[] z = new double[N];

        for(int i = 0; i < N; i++){
            z[i] = scalar * vector[i];
        }

        return z;
    }


    public static double[] getColumn(double[][] a, int columnNumber){
        int N = a.length;
        double[] column = new double[N];

        for (int i = 0; i < N; i++) {
            column[i] = a[i][columnNumber];
        }

        return column;
    }


    public static double[][] product(double[][] a, double[][] b) {
        int N = a.length;
        double[][] c = new double[N][N];

        for (int i =0; i < N; i++){
            for(int j =0; j < N; j++){
                c[i][j] = 0;

                for (int k = 0; k < N; k++){
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return c;
    }

    public static double[] product(double[][] a, double[] b) {
        int N = a.length;
        double[] c = new double[N];

        for (int i = 0; i < N; i++){
            c[i] = 0;

            for (int j = 0; j < N; j++){
                c[i] += a[i][j] * b[j];
            }
        }

        return c;
    }

    public static boolean sassenfeldCriterion(double[][] a){

        if(isSquare(a)){
            final int N = a.length;
            double[] alpha = new double[N];

            for(int i = 0 ; i < N; i++ ) alpha[i] = 1;


            for(int k = 0 ; k < N; k ++){
                if(k == 0){
                    double sum = 0.0;
                    for(int j = 1 ; j < N; j++){
                        sum += Math.abs(a[k][j]);
                    }

                    alpha[k] = sum / a[0][0];

                    if(alpha[k] >= 1){
                        return false;
                    }

                } else {
                    double sum = 0.0;

                    for(int j = 0 ; j < N; j++){
                        if(k != j) {
                            sum += Math.abs(a[k][j]) * alpha[k - 1];
                        }
                    }

                    alpha[k] = sum / a[k][k];

                    if(alpha[k] >= 1){
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    public static Matrix copy(Matrix m){
        final Matrix mtx = new Matrix(m.getData(), m.getIndependentTerms());
        return mtx;
    }

    public static boolean isPositiveDefinite(double[][] A) {

        int COLUMNS = A[0].length;
        double[][] LOWER = new double[COLUMNS][COLUMNS];


        if(!isSquare(A)) return false;

        for(int i = 0 ; i < COLUMNS; i++){
            for(int j = 0; j <= i; j++){

                double sum = 0.0;
                for(int k = 0;k < j; k++){
                    sum += A[i][k] * A[i][k];
                }

                if(i == j){
                    LOWER[i][i] = Math.sqrt(A[i][i] - sum);
                } else {
                    LOWER[i][j] = 1.0 / LOWER[j][j] * (A[i][j] - sum);
                }
            }

            if(LOWER[i][i] <= 0){
                throw new RuntimeException("Matrix is not positive definte");
            }
        }

        return true;
    }

    public static void print(double[][] A) {

        int numLines = A.length;
        int numCols = A[0].length;

        for (int i = 0; i < numLines; i++) {
            System.out.print("[");

            for (int j = 0; j < numCols; j++) {
                System.out.printf("%6.2f ", A[i][j]);
            }

            System.out.println("]");
        }
    }
}
