package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.Algorithms;
import net.ramonsilva.util.MatrixUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ramonsilva on 26/11/16.
 */
public class MultiGrid implements MatrixSolver {

    private static final int ONCE = 1;
    private static final int TWICE = 2;

    MatrixSolver smoother;

    public MultiGrid(){
    }

    @Override
    public double[] solve(Matrix matrix) {

        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        //Initial chute
        double[] e = new double[N];
        for(int i = 0; i < N; i++) e[i] = 1;

        double[] residual = calculateResidual(A, e, b);

        int l = N;
        smooth(A, e, TWICE);

        while(l > 1) {
            restrict(A, e);
            smooth(A, e, ONCE);
            l = e.length;
        }

        while(l < N) {
            interpolate(A, e);
            smooth(A, residual, ONCE);
            l = e.length;
        }

        return Algorithms.backSubstitution(N-1, A, residual);
    }

    private void restrict(double[][] A, double[] v){
        int half  =  (int) Math.floor(v.length / 2);
        int size = v.length - half;

        double[] rh  = new double[size];
        double[][] ah = new double[A.length][A[0].length];

        for(int i = 0; i < v.length; i++){
            if(i % 2 != 0){
                rh[i] = v[i];
            }

            for(int j = 0 ; j < A[0].length; j++){
                if(j % 2 == 0) {
                    ah[i][j] = A[i][j];
                }
            }
        }

    }

    private void interpolate(double[][] A, double[] v){

        int size;

        if(v.length % 2 == 0){
            size = v.length * 2;
        } else {
            size = (v.length * 2) - 1;
        }

        double[] r = new double[size];

        for(int i = 0 ; i < size; i ++){
            if(i % 2 == 0){
                r[i] = v[i];
            } else {
                r[i] = (v[i] + v[i-1]) / 2;
            }
        }

    }

    private double[] smooth(double[][] A, double[] b){
        return smooth(A, b,ONCE);
    }

    private double[] smooth(double[][] A, double[] b, int times){
        this.smoother = new GaussSiedel(times);
        final Matrix m = new Matrix(A, b);

        return this.smoother.solve(m);
    }

    /**
     * R = v - Ax
     * */
    private double[] calculateResidual(double[][] A, double[] x, double[] b) {
        return MatrixUtil.subtractVectors(MatrixUtil.multiplyMatrixByVector(A, x), b);
    }
}
