package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

import static net.ramonsilva.util.VectorUtil.Norm2;
import static net.ramonsilva.util.VectorUtil.addTwoVectors;
import static net.ramonsilva.util.VectorUtil.normalize;

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

        if(!MatrixUtil.isPositiveDefinite(A)){
            throw new RuntimeException("Matrix is not positive definite");
        }

        double[] c = new double[N];
        double[] res = VectorUtil.minus(b, MatrixUtil.multiplyMatrixByVector(A, c));

        while(VectorUtil.Norm2(res) > EPSILON && k < interactionsLimit){

            double[][] resT = MatrixUtil.transpose(res);
            double[] Ar = MatrixUtil.multiplyMatrixByVector(A, res);

            double alpha = multiply(resT, res)[0] / multiply(resT, Ar)[0];

            c = addTwoVectors(c, MatrixUtil.multiplyScalarByVector(alpha, res));
            res = VectorUtil.minus(b, MatrixUtil.multiplyMatrixByVector(A, c));
            k++;
        }

        if(Norm2(res) > EPSILON){
            System.out.println("Not Converged");
        }else {
            System.out.println("Converge after " + k + " interactions.");
        }
        return c;
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
