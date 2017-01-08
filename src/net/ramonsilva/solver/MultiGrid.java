package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;


public class MultiGrid implements MatrixSolver {


    private MatrixSolver smoother;

    public MultiGrid(){
    }

    @Override
    public double[] solve(Matrix matrix) {
        //Step 1 : Criar grids

        System.out.println("Multigrid [");

        double[][] A = matrix.getData();
        double[] b = matrix.getIndependentTerms();
        int N = matrix.getLines();

        //Step 2 : Resolva usando uma iteração de Guass-Siedel
        double[] omega = smooth(A, b);

        //Step 3 : Calcular Residuo para o Grid mais fino
        double[] rf = calculateResidual(A, b, omega);

        //Step 4 : Transferir o residuo para o grid mais grosso

        double[] rc = restrict(rf);
        double[][] ac = restrict(A);


        //Step 5 : Resolva
        double[] omegac = smooth(ac, rc, 2);

        //Step 6 : Prolongamento
        double[]  omegaCF = interpolate(omegac);


        //Step 7 : Atualiza a solucao
        omega = VectorUtil.addTwoVectors(omega, omegaCF);

        System.out.println("]");


        return omega;
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

    private double[][] restrict(double[][] matrix){

        int half  =  (int) Math.floor(matrix.length / 2);
        int size = matrix.length - half;

        double[][] a2 = new double[size][size];

        for(int i = 0; i < matrix.length; i++){
            if(i % 2 == 0){
                for(int j = 0 ; j < matrix[0].length; j++){
                    if(j % 2 == 0) {
                        a2[i/2][j/2] = matrix[i][j];
                    }
                }
            }
        }

        return a2;
    }


    private double[] interpolate(double[] coarse){
        int size;

        if(coarse.length % 2 == 0){
            size = coarse.length * 2;
        } else {
            size = (coarse.length * 2) - 1;
        }

        double[] fine = new double[size];
        int j = 0;

        for(int i = 0 ; i < size; i ++) {
            if (i % 2 == 0) {
                j = i/2;
                fine[i] = coarse[j];
            } else {
                fine[i] = (coarse[j] + coarse[j+1]) / 2;
            }
        }

        return fine;
    }

    private double[][] interpolate(double[][] coarse){
        int size;

        if(coarse.length % 2 == 0){
            size = coarse.length * 2;
        } else {
            size = (coarse.length * 2) - 1;
        }

        double[][] fine = new double[size][size];
        int k = 0;
        int l = 0;
        for(int i = 0 ; i < size; i ++){

            if(i % 2 == 0) k = i / 2;

            for(int j = 0 ; j < size; j++){

                if(j % 2 == 0) l = j / 2;

                if(i % 2 == 0) {
                    fine[i][j] = coarse[k][l];
                } else {
                    fine[i][j] = (coarse[k][l] + coarse[k+1][l+1]) / 2;
                }
            }
        }

        return fine;
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
    }

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
    }*/

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