package net.ramonsilva.solver;

import net.ramonsilva.util.MatrixUtil;

/**
 * Created by ramonsilva on 03/11/16.
 */
public class LeastSquares {

    public static void QRDecomposition(double[][] A, double[][] Q, double[][] R){
        int n = A.length;
        double[][] QR = new double[n][n];
        System.arraycopy(A, 0, QR, 0, n);

        double[] Rdiag = new double[n];

        for(int least = 0; least < n; least++){
            double norm = 0.0;

            for(int row = least; row < n; row++){
                norm += QR[row][least] * QR[row][least];
            }

            double a = Math.sqrt(norm);

            if(QR[least][least] > 0){
                a = -a;
            }

            Rdiag[least] = a;

            if(a != 0.0){
                QR[least][least] -= a;

                for(int col = least +1; col < n; col++){
                    double phi = 0.0;
                    for(int row = least; row < n; row++){
                        phi -= QR[row][col] * QR[row][least];
                    }

                    phi /= a * QR[least][least];

                    for(int row = least; row < n; row++){
                        QR[row][col] -= phi * QR[row][least];
                    }
                }
            }
        }

        //find r
        for(int row = n -1; row > -1; row--){
            R[row][row] = Rdiag[row];

            for(int col = row + 1; col < n; col++){
                R[row][col] = QR[row][col];
            }
        }

        //find Q
        for(int least = n-1; least > n - 1; least--){
            Q[least][least] = 1.0;
        }

        for(int least = n-1; least > -1; least--){
            Q[least][least] = 1.0;

            if(QR[least][least] != 0.0){
                for(int col = least ; col < n; col++){
                    double phi = 0.0;

                    for(int row = least; row < n; row++){
                        phi -= Q[row][col] * QR[row][least];
                    }

                    phi /= Rdiag[least] * QR[least][least];

                    for(int row = least; row < n; row++){
                        Q[row][col] -= phi * QR[row][least];
                    }
                }
            }
        }
    }

    public static double[] solve(double[][] A, double[] b, double[][] Q, double[][] R){

        int n = b.length;
        double[] x = new double[n];

        QRDecomposition(A, Q, R);


        // Step 1: Solve the system: y = Q^T b
        double[] y = MatrixUtil.multiplyMatrixByVector(MatrixUtil.transpose(Q), b);

        // Step 2: Solve the R system: Rx = y (regressive replacement)
        x[n - 1] = y[n - 1] / R[n - 1][n - 1];
        x[n - 2] = (y[n - 2] - R[n - 2][n - 1] * x[n - 1]) / (R[n - 2][n - 2]);

        for(int j = n - 3; j > -1; j--){

            double sum = 0;
            for(int k = j + 1; k < n; k++){
                sum += R[j][k] * x[k];
            }

            x[j] = (y[j] - sum) / R[j][j];
        }

        return x;
    }

}
