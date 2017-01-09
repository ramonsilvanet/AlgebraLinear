package net.ramonsilva;

import net.ramonsilva.solver.*;
import net.ramonsilva.util.MatrixMarketReader;

/**
 * Created by ramonsilva on 03/01/17.
 */
public class MatrixReaderTest {

    public static void main(String[] args) {
        MatrixMarketReader mmr =
                new MatrixMarketReader(
                        "matrices/nasa4704/nasa4704.mtx",
                        "matrices/nasa4704/nasa4704_b.mtx");

        double[] independent = mmr.getMatrix().getIndependentTerms();

        for(int i = 0 ; i < independent.length; i++){
            System.out.println(" i -> " + i + " = " + independent[i]);
        }
    }
}
