package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;


public class MultiGrid implements MatrixSolver {

    private static final int ONCE = 1;
    private static final int TWICE = 2;

    private MatrixSolver smoother;

    public MultiGrid(){
    }

    @Override
    public double[] solve(Matrix matrix) {

        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        double[] guess = new double[N];
        for(int i = 0; i < N; i++) guess[i] = 1;

        double[] res = calculateResidual(A, guess, b);

        return MatrixUtil.subtractVectors(guess, res);
    }


    /**
     * R = v - Ax
     * */
    private double[] calculateResidual(double[][] A, double[] x, double[] b) {
        return MatrixUtil.subtractVectors(MatrixUtil.multiplyMatrixByVector(A, x), b);
    }

}
