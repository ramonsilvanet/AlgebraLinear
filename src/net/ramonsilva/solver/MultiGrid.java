package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;


public class MultiGrid implements MatrixSolver {


    private MatrixSolver smoother;

    public MultiGrid(){
    }

    @Override
    public double[] solve(Matrix matrix) {

        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        //Initial chute
        double[] u = new double[N];
        for(int i = 0; i < N; i++) u[i] = 1;

        //Step 1 : Criar grids


        //Step 2 : Resolva usando uma iteração de Guass-Siedel
        double[] omega = smooth(A, b);

        //Step 3 : Calcular Residuo para o Grid mais fino
        double[] rf = calculateResidual(A, b, omega);

        //Step 4 : Transferir o residuo para o grid mais grosso

        int half  =  (int) Math.floor(N / 2);
        int NC = N - half;

        double[] rc = sweep(NC, rf, u);
        double[] uc = restrict(N, u, rf, NC, rc);

        //Step 5 : Resolva
        //Step 6 : Interpola
        //Step 7 :



        return new double[0];
    }

    private double[] restrict(double[] fine){
        int half  =  (int) Math.floor(fine.length / 2);
        int size = fine.length - half;

        double[] restricted =  new double[size];

        for(int i = 0; i < fine.length; i++){
            if(i % 2 == 0){
                restricted[i/2] = fine[i];
            }
        }

        return restricted;
    }

    /*private double[] interpolate(int NC, double[] UC, int NF, double[] UF){
        int ic;
        int iff;

        for ( ic = 0; ic < NC; ic++ ) {
            iff = 2 * ic;
            UF[iff] = UF[iff] + UC[ic];
        }

        for ( ic = 0; ic < NC - 1; ic++ ) {
            iff = 2 * ic + 1;
            UF[iff] = UF[iff] + 0.5 * ( UC[ic] + UC[ic+1] );
        }

        return UF;
    }*/

    private double[] restrict(int NF, double[] UF, double[] RF, int NC, double[] RC){

        int ic;
        int iff;

        double[] UC = new double[NC];

        RC[0] = 0.0;

        for ( ic = 1; ic < NC - 1; ic++ ) {
            iff = 2 * ic;
            RC[ic] = 4.0 * ( RF[iff] + UF[iff-1] - 2.0 * UF[iff] + UF[iff+1] );
        }

        RC[NC-1] = 0.0;

        return UC;
    }

    /**
     * Gauss-Siedel
     * */
    private double[] sweep(int N, double[] R, double[] U){
        int i;
        double u_old;

        for ( i = 1; i < N - 1; i++ )
        {
            u_old = U[i];
            U[i] = 0.5 * ( U[i-1] + U[i+1] + R[i] );
        }

        return U;
    }

    private double[] smooth(double[][] A, double[] b){
        return smooth(A, b, 1);
    }

    private double[] smooth(double[][] A, double[] b, int times){
        this.smoother = new GaussSiedel(times);
        final Matrix m = new Matrix(A, b);

        return this.smoother.solve(m);
    }

    /**
     * R = v - Ax
     * */
    private double[] calculateResidual(double[][] A, double[] b, double[] x) {
        return MatrixUtil.subtractVectors(MatrixUtil.multiplyMatrixByVector(A, x), b);
    }
}