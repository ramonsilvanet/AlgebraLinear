package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

/**
 * Created by ramonsilva on 14/11/16.
 */
public class GradientWSDC implements MatrixSolver {

    private static final double EPSILON = 1e-4;
    private static int DEFAULT_INTERACTIONS = 100;

    private int interactionsLimit = 0;
    private int k = 0;

    public GradientWSDC(){
        this(DEFAULT_INTERACTIONS);
    }

    public GradientWSDC(int interactionsLimit){
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

        if(!MatrixUtil.isPositiveDefinite(A)){
            throw new RuntimeException("Matrix is not positive definite");
        }

        int k = 0;

        double[] guess = new double[N];
        double[][] d = new double[interactionsLimit][N];
        double[][] r = new double[interactionsLimit][N];
        double[][] x = new double[interactionsLimit][N];
        double[] alpha = new double[interactionsLimit];

        double conjugCondition = 1.0;

        d[k] = MatrixUtil.subtractVectors(b , multiply(A, guess));
        r[k] = d[k];
        x[k] = guess;

        while(k < (interactionsLimit - 1) && normalize(r[k]) > EPSILON ){
            double[][] rT = MatrixUtil.transpose(r[k]);
            double[][] dT = MatrixUtil.transpose(d[k]);

            double[] Ad = multiply(A, d[k]);
            alpha[k] = (1 / multiply(dT, Ad)[0]) * multiply(rT, r[k])[0];

            x[k + 1] = VectorUtil.addTwoVectors(x[k], MatrixUtil.multiplyScalarByVector(alpha[k], d[k]));
            r[k + 1] = MatrixUtil.subtractVectors(r[k], MatrixUtil.multiplyScalarByVector(alpha[k], Ad));

            double beta = 1 / multiply(rT, r[k])[0];
            rT = MatrixUtil.transpose(r[k + 1]);
            beta *= multiply(rT, r[k + 1])[0];


            if( k  >= 1 ){
                conjugCondition = multiply(rT,b)[0];

                if( conjugCondition < EPSILON ){
                    conjugCondition = 0.0;
                }
            }

            if( conjugCondition > 0){
                double[] betaD = MatrixUtil.multiplyScalarByVector(beta, d[k]);
                d[k + 1] = VectorUtil.addTwoVectors(r[k + 1], betaD);
            } else {
                double lambda = calculateLambda(r[k], b, d[k], k);
                d[k+1] = VectorUtil.addTwoVectors(
                        MatrixUtil.multiplyScalarByVector(lambda, r[k]),
                        MatrixUtil.multiplyScalarByVector(beta, d[k]));
            }

            k++;
        }

        System.out.println("Gradient With Sufficient Descend Condition converges in " + (k+1) + " interactions.");

        return x[k];
    }

    private double calculateLambda(double[] residual, double[] y, double[] d, int k) {
        double lambda;

        double[][] deltaT = MatrixUtil.transpose(d);
        double[][] residualT = MatrixUtil.transpose(residual);

        double rNorm = normalize(residual);

        lambda = 1 / multiply(deltaT, y)[0];
        lambda *=  multiply(residualT, d)[0];
        lambda *= (1/rNorm);
        lambda *= multiply(residualT, y)[0];

        return lambda + 1;
    }


    private double normalize(double[] x){
        double sum = 0.0;
        int N = x.length;

        for (int i = 0 ; i < N; i++){
            sum += Math.pow(x[i], 2);
        }

        return Math.sqrt(sum);
    }

    private double[] multiply(double[][] A, double[] x){

        int m = A[0].length;
        int n = x.length;

        double[] y = new double[m];

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                y[i] += A[j][i] * x[j];
            }
        }

        return y;
    }
}
