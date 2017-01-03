package net.ramonsilva.util;

import net.ramonsilva.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ramonsilva on 30/12/16.
 */
public class MatrixMarketReader {

    private static final int HEADER_ROWS = 0;
    private static final int HEADER_COLUMNS = 1;
    private static final int HEADER_NONZEROS = 2;

    private static final int FIELD_ROW = 0;
    private static final int FIELD_COLUMN = 1;
    private static final int FIELD_VALUE = 2;

    private int columns;
    private int rows;
    private int nonZeros;

    private double[][] data;

    private String[] lines;

    public MatrixMarketReader(String file_name){
        readContent(file_name);
    }

    private void readContent(String file_name) {
        BufferedReader br;
        FileReader fr;

        try{
            fr = new FileReader(file_name);
            br = new BufferedReader(fr);

            String sCurrentLine = br.readLine();
            String header = null;

            while (sCurrentLine != null){
                if(sCurrentLine.startsWith("%")){
                    sCurrentLine = br.readLine();
                    continue;
                }

                if(header == null){
                    header = sCurrentLine;
                    parseHeader(header);
                } else {
                    String[] splited = sCurrentLine.split("\\s");

                    int row = (Integer.parseInt(splited[FIELD_ROW]) - 1);
                    int colunm = (Integer.parseInt(splited[FIELD_COLUMN]) - 1);
                    double value = Double.parseDouble(splited[FIELD_VALUE]);

                    this.data[row][colunm] = value;
                }

                sCurrentLine = br.readLine();
            }


        } catch (Exception ex) {
            System.out.println("Ocorreu um erro ao ler a matrix. " + ex.getLocalizedMessage());
        }
    }


    private void parseHeader(String header){
        String[] headerParts = header.split("\\s");

        rows = Integer.parseInt(headerParts[HEADER_ROWS]);
        columns = Integer.parseInt(headerParts[HEADER_COLUMNS]);
        nonZeros = Integer.parseInt(headerParts[HEADER_NONZEROS]);

        this.data = new double[rows][columns];
    }

    public Matrix getMatrix(){
        double[] rightHandSide = new double[this.rows];
        return new Matrix(this.data, rightHandSide);
    }

}


