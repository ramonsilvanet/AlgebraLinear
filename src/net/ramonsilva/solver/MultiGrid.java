package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.Algorithms;
import net.ramonsilva.util.MatrixUtil;

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

        double[] x = new double[N];

        //R = b - Ax
        double[] res = calculateResidual(A, x, b);

        res = smooth(A, b, TWICE);

        res = restrict(res);
        //TODO: Restringir a matriz tambem
        res = smooth(A, res);

        res = interpolate(res);
        //TODO: Interpolar a matriz tambem
        res = smooth(A, res);

        return Algorithms.backSubstitution(N-1, A, res);
    }

    private double[] restrict(double[] res){

        int half  =  (int) Math.floor(res.length / 2);
        int size = res.length - half;

        double[] restricted = new double[size];

        for(int i = 0; i < res.length; i++){
            if(i % 2 != 0){
                restricted[i] = res[i];
            }
        }

        return restricted;
    }

    private double[] interpolate(double[] res){
        int size;

        if(res.length % 2 == 0){
            size = res.length * 2;
        } else {
            size = (res.length * 2) - 1;
        }

        double[] interpolated = new double[size];

        for(int i = 0 ; i < size; i ++){
            if(i % 2 == 0){
                interpolated[i] = res[i];
            } else {
                interpolated[i] = (res[i] + res[i-1]) / 2;
            }
        }

        return interpolated;
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
}
