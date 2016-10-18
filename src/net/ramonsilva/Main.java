package net.ramonsilva;

import net.ramonsilva.solver.CrammerRule;
import net.ramonsilva.solver.GaussElimination;
import net.ramonsilva.solver.MatrixSolver;

public class Main {

    public static void main(String[] args) {
        double[][] ex3  = {{1,1,3}, {1,1,4}, {5,2,1}};
        double[] idex3 = {-2,-3,4};

        Matrix mEx3 = new Matrix(ex3, idex3);

        mEx3.print();

        MatrixSolver solver = new CrammerRule();
        double[] solution = solver.solve(mEx3);

        double[] s2 = new GaussElimination().solve(mEx3);

        System.out.printf(" Crammer : [%s,%s,%s]\n", solution[0], solution[1], solution[2]);
        System.out.printf(" Gauss : [%s,%s,%s]", s2[0], s2[1], s2[2]);

    }


}
