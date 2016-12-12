package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.Algorithms;
import net.ramonsilva.util.MatrixUtil;
import sun.util.resources.cldr.ewo.LocaleNames_ewo;

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

        int it = N;

        Level lv = new Level(A, b, e);

        while(it > 1) {
            lv = restrict(A, b, e);
            smooth(lv.A, lv.y);
            it = lv.N;
        }

        while(it < N) {
            lv = interpolate(lv.A, lv.y, lv.residual);
            smooth(A, residual);

        }

        return Algorithms.backSubstitution(N-1, A, residual);
    }

    private Level restrict(double[][] A, double[] b, double[] e){
        int half  =  (int) Math.floor(e.length / 2);
        int size = e.length - half;

        double[] b2  = new double[size];
        double[][] a2 = new double[size][size];
        double[] rh2 = new double[size];

        for(int i = 0; i < e.length; i++){
            if(i % 2 != 0){
                b2[i/2] = b[i];
                rh2[i/2] = e[i];

                for(int j = 0 ; j < A[0].length; j++){
                    if(j % 2 == 0) {
                        a2[i/2][j/2] = A[i][j];
                    }
                }
            }
        }

        Level lv = new Level(a2, b, rh2);
        return lv;
    }

    private Level interpolate(double[][] A, double[] b, double[] r){

        int size;

        if(r.length % 2 == 0){
            size = r.length * 2;
        } else {
            size = (r.length * 2) - 1;
        }

        double[] rh = new double[size];
        double[] bh = new double[size];
        double[][] Ah = new double[size][size];

        for(int i = 0 ; i < size; i ++){
            if(i % 2 == 0){
                rh[i] = r[i];
                b[i] = b[i];
            } else {
                rh[i] = (r[i] + r[i-1]) / 2;
                bh[i] = (b[i] + b[i-1]) / 2;
            }

            for(int j = 0 ; j < size; j++){
                if(i % 2 == 0) {
                    Ah[i][j] = A[i][j];
                } else {
                    Ah[i][j] = (A[i][j] + A[i-1][j-1]) / 2;
                }
            }
        }

        Level fine = new Level(A, bh, rh);
        return fine;

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
