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
        int L = matrix.getLines();

        double[] u = new double[L];
        for(int i = 0; i < L; i++) u[i] = 1;


       return new double[L];
    }

    /**
     * R = v - Ax
     * */
    private double[] calculateResidual(double[][] A, double[] x, double[] b) {
        return MatrixUtil.subtractVectors(MatrixUtil.multiplyMatrixByVector(A, x), b);
    }

}
