package net.ramonsilva.util;

import com.google.gson.Gson;
import net.ramonsilva.Matrix;
import net.ramonsilva.solver.Cholesky;
import net.ramonsilva.solver.GaussElimination;
import net.ramonsilva.solver.LUDecomposite;
import net.ramonsilva.solver.MatrixSolver;

/**
 * Created by ramonsilva on 15/04/17.
 */
public class CompareMethodsInTime {

    public static void main(String[] args){



        /*MatrixMarketReader mmr = new MatrixMarketReader(
                "matrices/nasa4704/nasa4704.mtx",
                "matrices/nasa4704/nasa4704_b.mtx");
        */

        MatrixMarketReader mmr = new MatrixMarketReader(
                "matrices/nasa2146/nasa2146.mtx",
                "matrices/nasa2146/nasa2146_b.mtx");


        Matrix m = new Matrix(mmr.getMatrix().getData(), mmr.getMatrix().getIndependentTerms());

        //MatrixSolver solver = new GaussElimination();
        //MatrixSolver solver = new LUDecomposite();
        MatrixSolver solver = new Cholesky();

        long timeAcc = 0;

        for (int i = 0 ; i < 10; i++) {

            System.out.print("Rodada " + (i + 1) + " ");

            long startTime = System.currentTimeMillis();
            double[] solution = solver.solve(m);
            long stopTime = System.currentTimeMillis();

            long elapsedTime = stopTime - startTime;

            System.out.println("Elapsed time : " +  elapsedTime + " ms ");

            timeAcc += elapsedTime;

        }

        System.out.print("Matrix " + mmr.getMatrix().getLines() + "x" +  mmr.getMatrix().getColumns());
        double avg = timeAcc / 10;
        System.out.println("Average time : " + avg + " ms ");

    }

}
