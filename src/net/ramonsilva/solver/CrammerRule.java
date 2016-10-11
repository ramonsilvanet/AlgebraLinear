package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 10/10/16.
 */
public class CrammerRule implements MatrixSolver {

    @Override
    public double[] solve(Matrix m) {

        if(m.getLines() != 3 || m.getColumns() != 3){
            throw new RuntimeException("Invalid 3x3 matrix");
        }

        double determinant = MatrixUtil.getMatrixDeterminant(m.getData());

        double[][] mX = MatrixUtil.swapColumn(m.getData(), m.getIndependentTerms(), 0);
        double determinantMX = MatrixUtil.getMatrixDeterminant(mX);

        double[][] mY = MatrixUtil.swapColumn(m.getData(), m.getIndependentTerms(), 1);
        double determinantMY = MatrixUtil.getMatrixDeterminant(mY);

        double[][] mZ = MatrixUtil.swapColumn(m.getData(), m.getIndependentTerms(), 2);
        double determinantMZ = MatrixUtil.getMatrixDeterminant(mZ);

        double[] solutionSet = new double[m.getLines()];
        solutionSet[0] = determinantMX / determinant;
        solutionSet[1] = determinantMY / determinant;
        solutionSet[2] = determinantMZ / determinant;

        return solutionSet;
    }
}
