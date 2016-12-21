package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.Algorithms;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class GMRES implements MatrixSolver{

    private static final double EPSILON = 1e-10;
    private static int DEFAULT_INTERACTIONS = 100;

    private int interactionsLimit = 0;
    private int k = 0;

    double[] INITIAL_CHUTE = {1.0,1.0,10};

    public GMRES(){
        this(DEFAULT_INTERACTIONS);
    }

    public GMRES(int interactionsLimit){
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

        double[] x = new double[N];

        double norm = VectorUtil.Norm2(b);
        double[][] Q = new double[N][N];
        for (int i = 0; i < N; i++) {
            Q[i][0] = b[i] / norm;
        }

        double[][] H = new double[N][N];
        double[][] Omega = new double[N][N];
        double[][] R = new double[N][N];

        //Iteractions
        do {

            Arnoldi.iterate(k, A, Q, H);

            double[] y = Algorithms.leastSquares(k, b, H, Omega, R);

            for (int i = 0; i < N; i++) {
                x[i] = 0;

                for (int j = 0; j <= k; j++){
                    x[i] += Q[i][j] * y[j];
                }
            }

            k++;

        } while(stopCondition(A, b, x));


        if(k == N - 1){
            double[] v = MatrixUtil.multiplyMatrixByVector(A, MatrixUtil.getColumn(Q, k));

            for (int i = 0; i <= k; i++){
                H[i][k] = VectorUtil.multiplyVectors(MatrixUtil.getColumn(Q, k), v);
            }
        }

        double[] r = new double[k + 1];
        for (int i = 0; i <= k; i++){

            r[i] = 0;
            for (int j = 0; j <= k; j++){
                r[i] += Omega[i][j] * H[j][k];
            }
        }

        for (int i = 0; i <= k; i++) {
            R[i][k] = r[i];
        }

        double[] g = new double[k + 1];
        for (int i = 0; i <= k; i++){
            g[i] = norm * Omega[i][0];
        }

        double[] y = Algorithms.backSubstitution(k, R, g);

        for (int i = 0; i < N; i++) {
            x[i] = 0;

            for (int j = 0; j <= k; j++){
                x[i] += Q[i][j] * y[j];
            }
        }

        return x;
    }

    private boolean stopCondition(double [][] A, double[] b, double[] x){
        int N = A.length;

        double[] Ar = MatrixUtil.product(A, x);
        double[] r = VectorUtil.minus(Ar, b);

        double rNorm = VectorUtil.Norm2(r);

        return ((rNorm > EPSILON) && (k < N - 1));
    }
}
