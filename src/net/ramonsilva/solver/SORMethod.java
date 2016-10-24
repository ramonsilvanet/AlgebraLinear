package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 24/10/16.
 */
public class SORMethod implements MatrixSolver {
    private static final double EPSILON = 1e-10;
    private static int DEFAULT_INTERACTIONS = 100;
    private static final double OMEGA = 1.0;

    private int k = 0;
    private int interactionsLimit = 0;

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
        double[] x = SOR(A, b, c);

        return x;
    }

    private double[] SOR(double[][] A, double[] b, double c[]){
        int N = A.length;

        double[] x = new double[N];
        System.arraycopy(c, 0, x, 0, c.length);

        for (int i = 0; i < N; i++){
            double sum = 0.0;

            for (int j = 0; j < N; j++) {
                if(i != j) {
                    sum += A[i][j] * x[j];
                }
            }

            x[i] = x[i] + OMEGA * (b[i] - sum) / A[i][i];

        }

        k++;

        boolean conv = true;
        for (int i = 0;i < N; i++){

            double diff = Math.abs(x[i] - c[i]);
            if(diff > EPSILON){
                conv = false;
                break;
            }
        }


        if(conv || k == interactionsLimit ){
            System.out.println("Converge in " + k + " interactions.");
            return c;
        } else {
            return SOR(A, b, c);
        }
    }
}
