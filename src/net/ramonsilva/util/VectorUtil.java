package net.ramonsilva.util;

/**
 * Created by ramonsilva on 02/11/16.
 */
public class VectorUtil {

    public static double[] addTwoVectors(double[] x, double[] y){
        int N = x.length;
        double[] z = new double[N];

        for(int i = 0; i < N; i++){
            z[i] = x[i] + y[i];
        }

        return z;
    }


    public static double Norm2(double[] b) {
        double norm = 0;

        for (int i = 0; i < b.length; i++) {
            norm += b[i] * b[i];
        }

        return Math.sqrt(norm);
    }

    public static double multiplyVectors(double[] u, double[] v) {
        double sum = 0;
        for (int i = 0; i < u.length; i++) {
            sum += u[i] * v[i];
        }

        return sum;
    }

    public static double normalize(double[] v){
        double norm = Norm2(v);

         if(norm != 0) {
            for (int i = 0; i < v.length; i++) {
                v[i] /= norm;
            }

        }

        return norm;
    }


    public static double[] minus(double[] a, double[] b) {

        int N = a.length;
        double[] c = new double[N];

        for (int i = 0; i < N; i++){
            c[i] = a[i] - b[i];
        }

        return c;
    }

    public static void print(double[] A) {

        int numLines = A.length;

        System.out.print("[");
        for (int i = 0; i < numLines; i++) {
            System.out.printf("%6.2f ", A[i]);
        }
        System.out.print("]");
    }

    public static void print(int[] A) {

        int numLines = A.length;

        System.out.print("[");
        for (int i = 0; i < numLines; i++) {
            System.out.printf("%d ", A[i]);
        }
        System.out.print("]");
    }
}

