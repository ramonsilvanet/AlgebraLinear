package net.ramonsilva.solver;

import net.ramonsilva.CSRMatrix;

/**
 * Created by ramonsilva on 08/01/17.
 */
public class LUDWithCSR implements CSRMatrixSolver{
    @Override
    public double[] solve(CSRMatrix matrix) {

        int LINES = matrix.getNumRows();
        int COLUMNS = matrix.getNumCols();
        int TERMS = matrix.getIndependentTerms().length;

        if(LINES != COLUMNS || COLUMNS != TERMS){
            throw new RuntimeException("Matriz com dimensoes invalidas");
        }


        double [][] LOWER = new double[LINES][COLUMNS];
        double[][] UPPER = new double[LINES][COLUMNS];

        int[] indexes = matrix.getIndex();
        int[] cols = matrix.getNZCols();
        double[] values = matrix.getValues();
        double[] pivots = new double[indexes.length];

        //Find Line Pivots
        for(int i = 0; i < indexes.length; i++){
            int start = indexes[i];
            int end;

            if(i == indexes.length -1){
               end = values.length;
            } else {
                end = indexes[i + 1];
            }

            for(int j = start; j < end; j++){
                if(i == cols[j]){
                    pivots[i] = values[j];
                }
            }
        }

        //for (int i = 0; i < pivots.length; i++) {
        //    System.out.println(pivots[i]);
        //}


        for(int k = 0; k < indexes.length; k++) {
            for (int i = k + 1; i < pivots.length; i++) {
                int start = indexes[i];
                int end;

                if (i == indexes.length - 1) {
                    end = values.length;
                } else {
                    end = indexes[i + 1];
                }

                for (int j = start; j < end; j++) {
                    if(k == cols[j]){
                        double c = values[j] / pivots[k];
                        System.out.println(cols[j] + " " + i + "   " + values[j] + " / " + pivots[k] + " : " + c);
                    }
                }
            }
        }

        return new double[0];
    }
}
