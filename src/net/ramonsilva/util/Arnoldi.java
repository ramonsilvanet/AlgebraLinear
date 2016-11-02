package net.ramonsilva.util;

import net.ramonsilva.Matrix;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class Arnoldi {

    public static void iterate(int n, double A[][], double[][] Q, double[][] H) {
        double[] v = MatrixUtil.multiplyMatrixByVector(A, MatrixUtil.getColumn(Q, n));

        for (int i = 0; i <= n; i++)
        {
            H[i][n] = VectorUtil.multiplyVectors(MatrixUtil.getColumn(Q, i), v);
            for (int j = 0; j < v.length; j++)
            {
                v[j] -= H[i][n] * Q[j][i];
            }
        }

        H[n + 1][n] = VectorUtil.normalize(v);

        for (int i = 0; i < v.length; i++) {
            Q[i][n + 1] = v[i];
        }

    }

}
