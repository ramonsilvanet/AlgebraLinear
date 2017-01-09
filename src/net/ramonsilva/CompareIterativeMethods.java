package net.ramonsilva;

import net.ramonsilva.solver.GaussJacobi;
import net.ramonsilva.solver.GaussSiedel;
import net.ramonsilva.solver.SORMethod;
import net.ramonsilva.util.MatrixMarketReader;

/**
 * Created by ramonsilva on 06/01/17.
 */
public class CompareIterativeMethods {

    public static void main(String[] args) {
        MatrixMarketReader mmr =
                new MatrixMarketReader(
                        "matrices/nasa4704/nasa4704.mtx",
                        "matrices/nasa4704/nasa4704_b.mtx");

        Matrix m = mmr.getMatrix();

        GaussJacobi gaussJacobi = new GaussJacobi(100000000);
        gaussJacobi.solve(m);

        GaussSiedel gaussSiedel = new GaussSiedel(100000000);
        gaussSiedel.solve(m);

        SORMethod sor = new SORMethod(100000000);
        sor.solve(m);

    }

}
