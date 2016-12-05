package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.Algorithms;
import net.ramonsilva.util.MatrixUtil;

import java.util.ArrayList;
import java.util.List;


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

        //Initial chute
        double[] e = new double[N];
        for(int i = 0; i < N; i++) e[i] = 1;

        double[] residual = calculateResidual(A, e, b);

        smooth(A, e, TWICE);

        Level lv = new Level(A, e, residual);

        while(lv.N > 1) {
            lv = restrict(lv);
            smooth(lv.A, lv.y);
            residual = calculateResidual(lv.A, lv.residual, lv.y);
        }

        while(lv.N < N) {
            interpolate(A, e);
            smooth(A, residual);
        }

        return Algorithms.backSubstitution(N-1, A, residual);
    }

    private Level restrict(Level level){
        int half  =  (int) Math.floor(level.y.length / 2);
        int size = level.y.length - half;

        double[] y2  = new double[size];
        double[][] a2 = new double[size][size];
        double[] rh2 = new double[size];

        for(int i = 0; i < level.y.length; i++){
            if(i % 2 != 0){
                y2[i/2] = level.y[i];
                rh2[i/2] = level.residual[i];

                for(int j = 0 ; j < level.A[0].length; j++){
                    if(j % 2 == 0) {
                        a2[i/2][j/2] = level.A[i][j];
                    }
                }
            }
        }

        Level lv = new Level(a2, y2, rh2);

        return lv;
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


    private class Level{
        public double[][] A;
        public double[] y;
        public double residual[];

        public int N;

        public Level(double[][] A, double[] y, double[] residual){
            this.A = A;
            this.y = y;
            this.residual = residual;

            this.N = this.residual.length;
        }
    }
}
