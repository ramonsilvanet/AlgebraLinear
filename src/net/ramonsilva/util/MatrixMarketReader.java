package net.ramonsilva.util;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ramonsilva on 30/12/16.
 */
public class MatrixMarketReader {


    private int columns;
    private int rows;
    private int nonZeros;

    private double[][] data;

    private String[] lines;

    public MatrixMarketReader(String file_name){
        readContent(file_name);
    }

    private void readContent(String file_name) {
        BufferedReader br = null;
        FileReader fr = null;

        try{
            fr = new FileReader(file_name);
            br = new BufferedReader(fr);

            String sCurrentLine;



        } catch (Exception ex) {

        }
    }


}


