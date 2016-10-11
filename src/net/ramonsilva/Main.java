package net.ramonsilva;

public class Main {

    public static void main(String[] args) {
        double[][] d = { { 1, 2, 1 }, { 2, -1, 1 }, { 3, 1, -1} };
        double[] indepentendTerms = {8,3,2};

        Matrix matrix = new Matrix(d, indepentendTerms);
        matrix.print();


    }


}
