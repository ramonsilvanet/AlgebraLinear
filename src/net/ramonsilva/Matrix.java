package net.ramonsilva;

/**
 * Created by ramonsilva on 10/10/16.
 */
public class Matrix {

    private int numLines;
    private int numCols;

    private double[][] data;
    private double[] independentTerms;


    public Matrix(double[][] data, double[] independentTerms){
        numLines = data.length;
        numCols = data[0].length;

        this.data = new double[numLines][numCols];
        for(int i = 0; i < numLines; i++){
            for(int j = 0; j < numCols; j++){
                this.data[i][j] = data[i][j];
            }
        }

        this.independentTerms = new double[independentTerms.length];
        for(int k = 0; k < independentTerms.length; k++){
            this.independentTerms[k] = independentTerms[k];
        }
    }


    public double[][] getData(){
        double[][] copy = new double[numLines][numCols];
        for(int i = 0; i < numLines; i++){
            for(int j = 0; j < numCols; j++){
                copy[i][j] = data[i][j];
            }
        }

        return copy;
    }

    public double[] getIndependentTerms(){
        double[] copy = new double[independentTerms.length];
        for(int k = 0; k < independentTerms.length; k++){
            copy[k] = independentTerms[k];
        }

        return copy;
    }

    public void print(boolean showRightHandSide) {
        for (int i = 0; i < numLines; i++) {
            System.out.print("|");

            for (int j = 0; j < numCols; j++) {
                System.out.printf("%6.2f ", this.data[i][j]);
            }

            if(showRightHandSide) {
                System.out.printf(" |%6.2f ", this.independentTerms[i]);
            }

            System.out.println("|");
        }

        System.out.println("");
        System.out.println("Matrix with " + this.numLines + " rows and " + numCols + " columns");
    }

    public void print(){
        print(true);
    }


    public int getLines() {
        return numLines;
    }

    public int getColumns() {
        return numCols;
    }
}
