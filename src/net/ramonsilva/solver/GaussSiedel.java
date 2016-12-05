package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 23/10/16.
 */
public class GaussSiedel implements MatrixSolver {
    private static final double EPSILON = 1e-10;
    private static int DEFAULT_INTERACTIONS = 100;

    private int interactionsLimit = 0;
    private int k = 0;

    public GaussSiedel(){
        this(DEFAULT_INTERACTIONS);
    }

    public GaussSiedel(int interactionsLimit){
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

        if(!MatrixUtil.sassenfeldCriterion(A)){
            throw new RuntimeException("Matrix does not converge");
        }

        double[] c = new double[N];
        double[] x = Siedel(A, b, c);

        return x;
    }

    private double[] Siedel(double[][] A, double[] b, double c[]){
        int N = A.length;

        double[] x = new double[N];
        System.arraycopy(c, 0, x, 0, c.length);

        for(int i = 0; i < N; i++){

            double sum = 0.0;
            for (int j = 0; j < N; j++) {
                if(i != j) {
                    sum += A[i][j] * c[j];
                }
            }

            c[i] = (b[i] - sum) / A[i][i];
        }

        k++;

        boolean conv = true;
        for (int i = 0;i < N; i++){

            double diff = x[i] - c[i];
            if(diff > EPSILON){
                conv = false;
                break;
            }
        }

        if(conv || k == interactionsLimit ){
            System.out.println("Converge in " + k + " interactions.");
            return c;

        } else {
            return Siedel(A, b, c);
        }
    }
}
