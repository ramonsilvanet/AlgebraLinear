package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

/**
 * Created by ramonsilva on 29/10/16.
 */
public class DescendGradient implements MatrixSolver {

    private static final double EPSILON = 1e-10;
    private static final double OMEGA = 1.00;
    private static int DEFAULT_INTERACTIONS = 100;

    private int interactionsLimit = 0;
    private int k = 0;

    public DescendGradient(){
        this(DEFAULT_INTERACTIONS);
    }

    public DescendGradient(int interactionsLimit){
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


        while(normalize(residual) > EPSILON && k < interactionsLimit){
            double[][] residualT = MatrixUtil.transpose(residual);

            double[][] Ar = MatrixUtil.multiply(A, residualT);
            double alpha = MatrixUtil.multiplyMatrixByVector(residualT, residual)[0] / MatrixUtil.multiplyMatrixByMatrix(residualT, Ar)[0];

            c = VectorUtil.addTwoVectors(c, MatrixUtil.multiplyScalarByVector(alpha, residual));

            k++;
            residual = MatrixUtil.subtractVectors(b,MatrixUtil.multiplyMatrixByVector(A, c));
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
