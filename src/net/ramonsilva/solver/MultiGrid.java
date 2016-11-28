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


    List<Level> levels;

    public MultiGrid(){
        levels = new ArrayList<>();
    }

    @Override
    public double[] solve(Matrix matrix) {

        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        //Initial chute
        double[] x = new double[N];
        for(int i = 0; i < N; i++) x[i] = 1;

        Level level_0 = new Level();

        System.arraycopy(A, 0, level_0.A, 0, N);
        System.arraycopy(b, 0, level_0.b, 0, N);
        System.arraycopy(x, 0, level_0.r, 0, N);

        levels.add(level_0);

        //R = b - Ax
        double[] res = calculateResidual(A, x, b);

        res = smooth(level_0.A, level_0.r, TWICE);

        Level level_1 = restrict(level_0.A, level_0.r);
        res = smooth(level_1.A, level_1.r);

        Level l = interpolate(level_1);
        level_0.r = l.r;
        level_0.A = l.A;

        level_0.r = smooth(level_0.A, level_0.r);

        return Algorithms.backSubstitution(N-1, level_0.A, level_0.r);
    }

    private Level restrict(double[][] A, double[] res){

        Level level = new Level();

        int half  =  (int) Math.floor(res.length / 2);
        int size = res.length - half;

        level.r  = new double[size];
        level.A = new double[A.length][A[0].length];

        for(int i = 0; i < res.length; i++){
            if(i % 2 != 0){
                level.r[i] = res[i];
            }

            for(int j = 0 ; j < A[0].length; j++){
                if(j % 2 == 0) {
                    level.A[i][j] = A[i][j];
                }
            }
        }

        return level;
    }

    private Level interpolate(Level level){

        Level l = new Level();

        int size;

        if(level.r.length % 2 == 0){
            size = level.r.length * 2;
        } else {
            size = (level.r.length * 2) - 1;
        }

        l.r = new double[size];

        for(int i = 0 ; i < size; i ++){
            if(i % 2 == 0){
                l.r[i] = level.r[i];
            } else {
                l.r[i] = (level.r[i] + level.r[i-1]) / 2;
            }
        }

        return l;
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
     * R = b - Ax
     * */
    private double[] calculateResidual(double[][] A, double[] x, double[] b) {
        return MatrixUtil.subtractVectors(MatrixUtil.multiplyMatrixByVector(A, x), b);
    }


    private class Level{
        double[][] A;
        double[] b;
        double[] r;
    }
}
