package net.ramonsilva.solver;

import net.ramonsilva.Matrix;
import net.ramonsilva.util.MatrixUtil;
import net.ramonsilva.util.VectorUtil;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class Arnoldi {

    public static void iterate(int s, double A[][], double[][] Q, double[][] H) {
        double[] v = MatrixUtil.multiplyMatrixByVector(A, MatrixUtil.getColumn(Q, s));

        for (int i = 0; i <= s; i++)
        {
            H[i][s] = VectorUtil.multiplyVectors(MatrixUtil.getColumn(Q, i), v);
            for (int j = 0; j < v.length; j++)
            {
                v[j] -= H[i][s] * Q[j][i];
            }
        }

        H[s + 1][s] = VectorUtil.normalize(v);

        for (int i = 0; i < v.length; i++) {
            Q[i][s + 1] = v[i];
        }

    }

}
