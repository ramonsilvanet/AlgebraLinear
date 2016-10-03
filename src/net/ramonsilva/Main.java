package net.ramonsilva;

public class Main {

    public static void main(String[] args) {
        double[][] d = { { 1, 2, 3 }, { 4, 5, 6 }, { 9, 1, 3} };
        Matriz D = new Matriz(d);
        D.imprimir();
        System.out.println();

        Matriz A = Matriz.gerarMatrixAleatoria(5, 5);
        Matriz b = A.transpose();

        Matriz x = A.resolverPorEliminacaoGaussiana(b);
        x.imprimir();
        System.out.println();
    }
}
