package net.ramonsilva.util;

import net.ramonsilva.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ExecutionException;

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
    private static final int VECTOR_VALUE = 0;

    private int columns;
    private int rows;
    private int nonZeros;

    private double[][] data;
    private double[] rightHandSide;

    private String[] lines;

    public MatrixMarketReader(String matrix_data){
        readMatrixData(matrix_data);
    }

    public MatrixMarketReader(String matrix_data, String rightHandSide){
        this(matrix_data);
        readRightHandSide(rightHandSide);
    }

    private void readRightHandSide(String file_name){
        try{
            FileReader fr = new FileReader(file_name);
            BufferedReader br = new BufferedReader(fr);

            String sCurrentLine = br.readLine();
            String header = null;

            int row = 0;

            while (sCurrentLine != null){
                if(sCurrentLine.startsWith("%")){
                    sCurrentLine = br.readLine();
                    continue;
                }

                if(header == null){
                    header = sCurrentLine;
                    parseVectorHeader(header);
                } else {
                    String[] splited = sCurrentLine.split("\\s");
                    double value = Double.parseDouble(splited[VECTOR_VALUE]);

                    this.rightHandSide[row] = value;
                    row++;
                }

                sCurrentLine = br.readLine();
            }

        } catch (Exception ex){
            System.out.println("Ocorreu um erro ao ler o vetor B. " + ex.getLocalizedMessage());
        }
    }

    private void readMatrixData(String file_name) {
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

    private void parseVectorHeader(String header){
        String[] headerParts = header.split("\\s");
        final int rows = Integer.parseInt(headerParts[HEADER_ROWS]);

        this.rightHandSide = new double[rows];
    }

    public Matrix getMatrix(){

        if(this.rightHandSide == null || this.rightHandSide.length == 0) {
            this.rightHandSide = new double[this.rows];
        }

        return new Matrix(this.data, this.rightHandSide);
    }

}


