package net.ramonsilva.solver;


import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;

public class GaussElimination implements MatrixSolver {

    @Override
    public double[] solve(Matrix m) {

        Matrix ma = MatrixUtil.getArgumentedMatrix(m);

        //TODO: Transformar em triagular superior

        //TODO: Resolver o sistema

        return new double[0];
    }

}
