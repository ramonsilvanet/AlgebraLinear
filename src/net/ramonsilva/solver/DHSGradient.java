package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

/**
 * Created by ramonsilva on 14/11/16.
 */
public class DHSGradient implements MatrixSolver {

    private static final double EPSILON = 1e-10;
    private static final double OMEGA = 1.00;
    private static int DEFAULT_INTERACTIONS = 100;

    private int interactionsLimit = 0;
    private int k = 0;

    @Override
    public double[] solve(Matrix matrix) {
        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        if(!MatrixUtil.isSquare(A)){
            throw new RuntimeException("Matrix is not Square");
        }

        /*if(!MatrixUtil.isDiagonalDominant(A)){
            throw new RuntimeException("Matrix is not diagonal dominant");
        }*/

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

            //Modification proposed by Dai-Yu
            if(beta > 0) {
                d = VectorUtil.addTwoVectors(residual, MatrixUtil.multiplyScalarByVector(beta, d));
            } else {
                d = residualT[0];
            }

            k++;

        }

        return c;
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
