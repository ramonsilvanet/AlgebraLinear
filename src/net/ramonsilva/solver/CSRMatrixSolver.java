package net.ramonsilva.solver;

import net.ramonsilva.CSRMatrix;

/**
 * Created by ramonsilva on 08/01/17.
 */
public interface CSRMatrixSolver {

    double[] solve(CSRMatrix matrix);

}
