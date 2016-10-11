package net.ramonsilva;

/**
 * Created by ramonsilva on 03/10/16.
 */
public final class Matriz {

    private int LINHAS;
    private int COLUNAS;

    private final double[][] dados;

    public Matriz(int n, int m){
        this.LINHAS = n;
        this.COLUNAS = m;

        this.dados = new double[LINHAS][COLUNAS];
    }

    public Matriz(double[][] dados){
        LINHAS = dados.length;
        COLUNAS = dados[0].length;

        this.dados = new double[LINHAS][COLUNAS];
        for(int i = 0; i < LINHAS; i++){
            for(int j = 0; j < COLUNAS; j++){
                this.dados[i][j] = dados[i][j];
            }
        }
    }

    private Matriz(Matriz b){
        this(b.dados);
    }

    public static Matriz gerarMatrixAleatoria(int linhas, int colunas){
        Matriz matrixA = new Matriz(linhas, colunas);

        for(int i = 0 ; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                matrixA.dados[i][j] = Math.random();
            }
        }

        return matrixA;
    }

    public static Matriz matrizIdentidade(int dimensao){
        Matriz identidade = new Matriz(dimensao,dimensao);

        for (int i =0; i < dimensao; i++){
            identidade.dados[i][i] = 1;
        }

        return identidade;
    }

    private void trocarLinhas(int primeiraLinhas, int segundaLinha){
        double[] temp = dados[primeiraLinhas];
        dados[primeiraLinhas] = dados[segundaLinha];
        dados[segundaLinha] = temp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Matriz){

            Matriz b = (Matriz) obj;

            for (int i = 0; i < LINHAS; i++) {
                for (int j = 0; j < COLUNAS; j++){
                    if (this.dados[i][j] != b.dados[i][j]) return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public Matriz resolverPorEliminacaoGaussiana(Matriz rhs) {
        if (LINHAS != COLUNAS || rhs.LINHAS != COLUNAS || rhs.LINHAS != 1) {
            throw new RuntimeException("Matriz inválida.");
        }

        Matriz A = new Matriz(this);
        Matriz b = new Matriz(rhs);

        //Eliminação Guassiana
        for(int i = 0; i < COLUNAS; i++){
            int pivot = i;

            for(int j = i + 1 ; j < LINHAS; j++){
                if(Math.abs(A.dados[i][j]) > Math.abs(A.dados[pivot][j])){
                    pivot = j;
                }
            }

            A.trocarLinhas(i, pivot);
            b.trocarLinhas(i, pivot);

            //Verifica se a matriz é singular
            if(A.dados[i][i] == 0.0) throw new RuntimeException("Matriz singular");

            //Pivotando B
            for(int j = i + 1; i < LINHAS; j++){
                b.dados[j][0] -= b.dados[i][0] * A.dados[j][i] / A.dados[i][i];
            }

            //Pivotando A
            for (int j = i + 1; j < LINHAS; j++) {
                double m = A.dados[j][i] / A.dados[i][i];
                for (int k = i+1; k < LINHAS; k++) {
                    A.dados[j][k] -= A.dados[i][k] * m;
                }
                A.dados[j][i] = 0.0;
            }
        }

        Matriz solucao = new Matriz(LINHAS, 1);

        //Retrosubstituicao
        for (int j = LINHAS - 1; j >= 0; j--) {
            double t = 0.0;
            for (int k = j + 1; k < LINHAS; k++)
                t += A.dados[j][k] * solucao.dados[k][0];
            solucao.dados[j][0] = (b.dados[j][0] - t) / A.dados[j][j];
        }

        return solucao;
    }

    public void imprimir() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                System.out.printf("%9.4f ", dados[i][j]);
            }
            System.out.println();
        }
    }

}
