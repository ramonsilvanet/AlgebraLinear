package net.ramonsilva.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ramonsilva on 14/11/16.
 */
public class MatrixMarketReader {

    public class SparseMatrixEx2 {
        private String typecode;
        private double[][] matrix;

        public void read(String filename) throws java.io.IOException {
            InputStream s = SparseMatrixEx2.class.getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(s));

            // read type code initial line
            String line = br.readLine();
            typecode = line;

            // read comment lines if any
            boolean comment = true;
            while (comment) {
                line = br.readLine();
                comment = line.startsWith("%");
            }

            // line now contains the size information which needs to be parsed
            String[] str = line.split("( )+");
            int nRows = (Integer.valueOf(str[0].trim())).intValue();
            int nColumns = (Integer.valueOf(str[1].trim())).intValue();
            int nNonZeros = (Integer.valueOf(str[2].trim())).intValue();

            // now we're into the data section
            matrix = new double[nRows][nColumns];
            while (true) {
                line = br.readLine();
                if (line == null) break;
                str = line.split("( )+");
                int i = (Integer.valueOf(str[0].trim())).intValue();
                int j = (Integer.valueOf(str[1].trim())).intValue();
                double x = (Double.valueOf(str[2].trim())).doubleValue();
                matrix[i - 1][j - 1] = x;
            }
            br.close();
        }

        public String getTypeCode() {
            return this.typecode;
        }
    }
}

