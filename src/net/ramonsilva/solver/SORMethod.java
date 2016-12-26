package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;


public class SORMethod implements MatrixSolver {
    private static final double EPSILON = 1e-10;
    private static int DEFAULT_INTERACTIONS = 100;

    private int k = 0;
    private int interactionsLimit = 0;
    private static double OMEGA = 1.05;

    public SORMethod(){
        this(DEFAULT_INTERACTIONS);
    }

    public SORMethod(int interactionsLimit){
        this.interactionsLimit = interactionsLimit;
    }

    @Override
    public double[] solve(Matrix matrix) {
        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        if(!MatrixUtil.isSquare(A)){
            throw new RuntimeException("Matrix is not Square");
        }

        if(!MatrixUtil.isDiagonalDominant(A)){
            throw new RuntimeException("Matix is not diagonal dominant");
        }


        double[] c = new double[N];
        double[] x = SOR(A, b, c, OMEGA);

        return x;
    }

    private double[] SOR(double[][] A, double[] b, double c[], double w){
        int N = A.length;
        boolean conv = false;

        while(k < interactionsLimit && !conv) {

            double[] x = new double[N];
            System.arraycopy(c, 0, x, 0, c.length);

            for (int i = 0; i < N; i++) {
                double sum = 0;
                for (int j = 0; j < N; j++) {
                    if (j != i) {
                        sum += A[i][j] * c[j];
                    }
                }

                c[i] = (1 - w) * c[i] + ((w / A[i][i]) * (b[i] - sum));
            }

            double maxDiff = 0.0;
            for (int i = 0; i < N; i++) {
                double diff = Math.abs(x[i] - c[i]);

                if(diff > maxDiff){
                    maxDiff = diff;
                }
            }

            if(maxDiff < EPSILON){
                conv = true;
            }

            k++;

        }

        System.out.print("Converge in " + k + " interactions");

        return c;
    }
}
