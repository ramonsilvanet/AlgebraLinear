package net.ramonsilva.solver;
import net.ramonsilva.CSRMatrix;

/**
 * Created by ramonsilva on 08/01/17.
 */
public class CholeskyWithCSR implements CSRMatrixSolver {
    @Override
    public double[] solve(CSRMatrix matrix) {

        if (matrix.getNumCols() != matrix.getNumRows()) {
            throw new RuntimeException("Matrix não é quadrada");
        }

        double[][] LOWER = getLower(matrix);

        return new double[0];
    }


    private double[][] getLower(CSRMatrix csr){
        int N = csr.getNumRows();

        double [][] LOWER = new double[N][N];

        for (Integer row : csr.getNZCols()) {

        }

        return LOWER;
    }
}
