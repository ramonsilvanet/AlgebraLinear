package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 10/10/16.
 */
public class CrammerRule implements MatrixSolver {

    @Override
    public double[] solve(Matrix m) {

        if(m.getLines() != m.getColumns()) {
            throw new RuntimeException("Invalid Matrix ");
        }

        int order = m.getLines();
        double determinant = MatrixUtil.getMatrixDeterminant(m.getData());

        double[] solutionSet = new double[order];

        for(int o = 0; o < order; o++) {
            double[][] mX = MatrixUtil.swapColumn(m.getData(), m.getIndependentTerms(), o);
            solutionSet[o] = MatrixUtil.getMatrixDeterminant(mX) / determinant;
        }

        return solutionSet;
    }
}
