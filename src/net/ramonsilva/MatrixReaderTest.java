package net.ramonsilva;

import net.ramonsilva.solver.*;
import net.ramonsilva.util.MatrixMarketReader;

/**
 * Created by ramonsilva on 03/01/17.
 */
public class MatrixReaderTest {

    public static void main(String[] args) {
        //MatrixMarketReader mmr = new MatrixMarketReader("matrices/sparse_matrix_3_4.mtx");
        MatrixMarketReader mmr = new MatrixMarketReader("matrices/494_bus.mtx");
    }
}
