package net.ramonsilva;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramonsilva on 08/01/17.
 */
public class CSRMatrix {

    private List<Double> AA;
    private List<Integer> JA;
    private List<Integer> IA;

    private double[] independentTerms;

    private int numRows;
    private int numCols;

    public CSRMatrix(double[][] A, double[] b){
        AA = new ArrayList<>();
        IA =  new ArrayList<>();
        JA = new ArrayList<>();

        independentTerms = b;

        transform(A);
    }

    private void transform(double[][] data){

        numRows = data.length;
        numCols = data[0].length;

        int k = 0;
        for (int i = 0; i < numRows; i++ ){
            int old_k = k;
            for (int j = 0 ; j < numCols; j++){
                if(data[i][j] != 0){
                    AA.add(data[i][j]);
                    JA.add(j);
                    k++;
                }
            }
            IA.add(old_k);
        }
    }

    public double[] getValues(){
        double[] _aa = new double[AA.size()];
        int i = 0;

        for (Double d : AA) {
            _aa[i++] = d;
        }

        return _aa;
    }

    public int[] getIndex(){

        int[] _ia = new int[IA.size()];

        int index = 0;
        for (Integer val : IA) {
            _ia[index++] = val;
        }

        return _ia;
    }

    public int[] getNZCols(){

        int[] _nz = new int[JA.size()];

        int index = 0;
        for (Integer val : JA) {
            _nz[index++] = val;
        }

        return _nz;
    }

    public int getNumRows(){
        return numRows;
    }

    public int getNumCols(){
        return numCols;
    }

    public double[] getIndependentTerms(){
        return this.independentTerms;
    }

}
