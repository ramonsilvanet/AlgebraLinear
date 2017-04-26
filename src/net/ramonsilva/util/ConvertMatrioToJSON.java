package net.ramonsilva.util;

import com.google.gson.Gson;
import net.ramonsilva.Matrix;

import java.io.*;

/**
 * Created by rsilva on 26/04/17.
 */
public class ConvertMatrioToJSON {

    public static void main(String[] args) {
        Gson gson = new Gson();

        /*MatrixMarketReader mmr = new MatrixMarketReader(
                "matrices/nasa2146/nasa2146.mtx",
                "matrices/nasa2146/nasa2146_b.mtx");

        */

        MatrixMarketReader mmr = new MatrixMarketReader(
                "matrices/nasa4704/nasa4704.mtx",
                "matrices/nasa4704/nasa4704_b.mtx");

        Matrix m = new Matrix(mmr.getMatrix().getData(), mmr.getMatrix().getIndependentTerms());

        String nasa2146_data = gson.toJson(mmr.getMatrix().getData());
        String nasa2146_b = gson.toJson(mmr.getMatrix().getIndependentTerms());

        saveFile(nasa2146_data, "nasa4704.json");
        saveFile(nasa2146_b, "nasa4704_b.json");

    }

    private static void saveFile(String data, String name){
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("matrices/json/" + name), "utf-8"));
            writer.write(data);
        } catch (IOException ex) {
            System.err.println("Deu merda na importação " + ex.getMessage());
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
    }

}


