package net.ramonsilva.conditioner;

import net.ramonsilva.Matrix;

import java.util.Arrays;


/**
 * Created by ramonsilva on 08/01/17.
 */
public class CuthillMckee {

    private double[][] adjacent;
    private boolean[] visited;

    private Matrix _m;

    public CuthillMckee(Matrix m){
        this._m = m;
    }

    public double[] run(){
        int N = _m.getLines();
        adjacent = _m.getData();

        double[] toVisit = new double[N];
        visited = new boolean[N];

        int eol = 0;
        int ptr = 0;

        for(int i = 0; i < N; ++i) {
            if(visited[i]) {
                continue;
            }

            toVisit[eol++] = i;
            visited[i] = true;

            while (ptr < eol){
                double v = toVisit[ptr];
                double[] viz = adjacent[ptr];
                ptr++;

                Arrays.sort(viz);

                for(int j = 0 ; j < viz.length; j++){
                    double u = viz[j];

                    if(visited[j]){
                        continue;
                    }

                    visited[j] = true;
                    toVisit[ptr++] = u;
                }
            }
        }

        return toVisit;
    }

}
