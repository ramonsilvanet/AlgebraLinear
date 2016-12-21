package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

/**
 * Created by ramonsilva on 29/10/16.
 */
public class ConjugateGradient implements MatrixSolver {

    private static final double EPSILON = 1e-10;
    private static final double OMEGA = 1.00;
    private static int DEFAULT_INTERACTIONS = 100;

    private int interactionsLimit = 0;
    private int k = 0;


    public ConjugateGradient(){
        this(DEFAULT_INTERACTIONS);
    }

    public ConjugateGradient(int interactionsLimit){
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
            throw new RuntimeException("Matrix is not diagonal dominant");
        }

        double[] c = new double[N];
        double[] y = MatrixUtil.multiplyMatrixByVector(A, c);

        double[] residual =  MatrixUtil.subtractVectors(b,MatrixUtil.multiplyMatrixByVector(A, c));

        double[] d = new double[residual.length];
        System.arraycopy(residual, 0, d, 0, residual.length);

        double[][] residualT = MatrixUtil.transpose(residual);
        double[] delta = MatrixUtil.multiplyMatrixByVector(residualT,residual);

        while(normalize(residual) > EPSILON && k < interactionsLimit){

            double[] q = MatrixUtil.multiplyMatrixByVector(A, d);
            double[][] dTrans = MatrixUtil.transpose(d);
            double alpha = delta[0] / MatrixUtil.multiplyMatrixByVector(dTrans, q)[0];
            c = VectorUtil.addTwoVectors(c, MatrixUtil.multiplyScalarByVector(alpha, q));

            if(k % 50 == 0){
                residual = MatrixUtil.subtractVectors(b, MatrixUtil.multiplyMatrixByVector(A, c));
            } else {
                residual = MatrixUtil.subtractVectors(residual, MatrixUtil.multiplyScalarByVector(alpha, q));
            }

            double[] tempDelta = new double[delta.length];
            System.arraycopy(delta, 0, tempDelta, 0, delta.length);
            residualT = MatrixUtil.transpose(residual);
            delta = MatrixUtil.multiplyMatrixByVector(residualT,residual);
            double beta = delta[0] / tempDelta[0];

            double conjugateCondition = 0;

            if(k  >= 1 ){
                conjugateCondition = MatrixUtil.multiplyMatrixByVector(residualT, y)[0];

                if(conjugateCondition < EPSILON){
                    conjugateCondition = 0.0;
                }
            }

            if(conjugateCondition > 0){
                d = VectorUtil.addTwoVectors(residual, MatrixUtil.multiplyScalarByVector(beta, d));
            } else {
                double lambda = calculateLambda(residual, y, d, k);
                d = VectorUtil.addTwoVectors(
                        MatrixUtil.multiplyScalarByVector(lambda, residual),
                        MatrixUtil.multiplyScalarByVector(beta, d));
            }


            k++;

        }

        return c;
    }

    private double calculateLambda(double[] residual, double[] y, double[] d, int k) {
        double lambda;

        double[][] deltaT = MatrixUtil.transpose(d);
        double[][] residualT = MatrixUtil.transpose(residual);

        double rNorm = normalize(residual);

        lambda = 1 / MatrixUtil.multiplyMatrixByVector(deltaT, y)[0];
        lambda *=  MatrixUtil.multiplyMatrixByVector(residualT, d)[0];
        lambda *= (1/rNorm);
        lambda *= MatrixUtil.multiplyMatrixByVector(residualT, y)[0];

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
}
