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

        for(int i = 0; i < indexes.length; i++){
            int start = indexes[i];
            int end;

            if(i == indexes.length -1){
               end = values.length;
            } else {
                end = indexes[i + 1];
            }

            double pivot;

            for(int j = start; j < end; j++){
                if(i == cols[j]){
                    pivot = values[j];
                }
            }

            for(int j = start; j < end; j++){
                int col =  cols[j];

                if(col > i){
                    //System.out.println(i + " " + col + " = " + values[j]);
                }
            }


        }


        return new double[0];
    }
}
