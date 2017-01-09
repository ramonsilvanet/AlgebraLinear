package net.ramonsilva;

import net.ramonsilva.CSRMatrix;

import java.util.List;

/**
 * Created by ramonsilva on 08/01/17.
 */
public class CSRTest {

    public static void main(String[] args) {
        double[][] data = {
                {1.0, 0, 0, 2.0, 0},
                {3.0, 4.0, 0, 5.0, 0},
                {6.0, 0, 7.0, 8.0, 9.0},
                {0, 0, 10.0, 11.0, 0},
                {0, 0, 0, 0, 12.0}
        };

        CSRMatrix csr = new CSRMatrix(data, new double[5]);

        double[] values = csr.getValues();

        System.out.println("");
        System.out.print("VALUES : ");
        for (Double v: values) {
            System.out.print(v + " ");
        }

        System.out.println("");
        System.out.println("--------------------------------");

        System.out.print("COLS : ");
        int[] JA = csr.getNZCols();
        for (Integer col : JA) {
            System.out.print(col + " ");
        }

        System.out.println("");
        System.out.println("--------------------------------");

        System.out.print("INDEXES : ");
        int[] IA = csr.getIndex();
        for (Integer index : IA) {
            System.out.print(index + " ");
        }

        System.out.println("");
        System.out.println("--------------------------------");

    }
}
