package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 23/10/16.
 */
public class GaussJacobi implements MatrixSolver {

    private static final double EPSILON = 1e-10;
    private static int DEFAULT_INTERACTIONS = 100;

    private int k = 0;
    private int interactionsLimit = 0;

    public GaussJacobi(){
        this(DEFAULT_INTERACTIONS);
    }

    public GaussJacobi(int interactionsLimit){
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
        double[] x = Jacobi(A, b, c);

        return x;
    }

    private double[] Jacobi(double[][] A, double[] b, double c[]){
        int N = A.length;

        double[] x = new double[N];
        System.arraycopy(c, 0, x, 0, c.length);

        for(int i = 0; i < N; i++){

            double sum = 0.0;
            for (int j = 0; j < N; j++) {
                if(i != j) {
                    sum += A[i][j] * x[j];
                }
            }

            c[i] = (b[i] - sum) / A[i][i];
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
            System.out.println("Gauss-Jacobi Converge in " + k + " interactions.");
            return c;

        } else {
            return Jacobi(A, b, c);
        }
    }
}
