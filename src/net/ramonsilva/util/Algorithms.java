package net.ramonsilva.util;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class Algorithms {
    public static double[] leastSquares(int n,double [] b, double[][] h, double[][] omega, double[][] r) {
        if (n == 0) {
            InitialQRDecomposition(h, omega, r);
        } else {
            UpdateQRDecomposition(n, h, omega, r);
        }

        double[] g = new double[n + 1];
        double bNorm = VectorUtil.Norm2(b);

        for (int i = 0; i < g.length; i++) {
            g[i] = bNorm * omega[i][0];
        }

        double[] y = Algorithms.backSubstitution(n, r, g);
        return y;
    }

    private static void InitialQRDecomposition(double[][] h, double[][] omega, double[][] r){
        double temp = Math.sqrt(h[0][0] * h[0][0] + h[1][0] * h[1][0]);

        r[0][0] = temp;

        omega[0][0] = h[0][0] / temp;
        omega[0][1] = h[1][0] / temp;
        omega[1][0] = -omega[0][1];
        omega[1][1] = omega[0][0];
    }

    private static void UpdateQRDecomposition(int n, double[][] h, double[][] omega, double[][] r) {
        double[] _r = new double[n + 1];

        for (int i = 0; i < n + 1; i++) {
            _r[i] = 0;

            for (int j = 0; j < n + 1; j++) {
                _r[i] += omega[i][j] * h[j][n];
            }
        }

        double rho = _r[n];
        double sigma = h[n + 1][n];

        double rLast = Math.sqrt(rho * rho + sigma * sigma);
        double c = rho / rLast;
        double s = sigma / rLast;

        for (int i = 0; i < n; i++) {
            r[i][n] = _r[i];
        }

        r[n][n] = rLast;

        for (int j = 0; j < n + 1; j++) {
            omega[n + 1][j] = -s * omega[n][j];
            omega[n][j] = c * omega[n][j];
        }

        omega[n][n + 1] = s;
        omega[n + 1][n + 1] = c;
    }

    public static double[] backSubstitution(int n, double[][] A, double[] b){
        double[] x = new double[n+1];

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }
}
