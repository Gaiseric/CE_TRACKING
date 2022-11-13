//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cositos.cetracking.Grafos;

public class CaminosMinimos {
    public CaminosMinimos() {
    }

    public String algoritmoFloyd(long[][] mAdy) {
        int vertices = mAdy.length;
        long[][] matrizAdyacencia = mAdy;
        String[][] caminos = new String[vertices][vertices];
        String[][] caminosAuxiliares = new String[vertices][vertices];
        String caminoRecorrido = "";
        String cadena = "";
        String caminitos = "";

        int i;
        int j;
        for(i = 0; i < vertices; ++i) {
            for(j = 0; j < vertices; ++j) {
                caminos[i][j] = "";
                caminosAuxiliares[i][j] = "";
            }
        }

        for(int k = 0; k < vertices; ++k) {
            for(i = 0; i < vertices; ++i) {
                for(j = 0; j < vertices; ++j) {
                    float temporal1 = (float)matrizAdyacencia[i][j];
                    float temporal2 = (float)matrizAdyacencia[i][k];
                    float temporal3 = (float)matrizAdyacencia[k][j];
                    float temporal4 = temporal2 + temporal3;
                    float minimo = Math.min(temporal1, temporal4);
                    if (temporal1 != temporal4 && minimo == temporal4) {
                        caminoRecorrido = "";
                        caminosAuxiliares[i][j] = "" + k;
                        String[] var10000 = caminos[i];
                        String var10002 = this.caminosR(i, k, caminosAuxiliares, caminoRecorrido);
                        var10000[j] = var10002 + (k + 1);
                    }

                    matrizAdyacencia[i][j] = (long)minimo;
                }
            }
        }

        for(i = 0; i < vertices; ++i) {
            for(j = 0; j < vertices; ++j) {
                cadena = cadena + "[" + matrizAdyacencia[i][j] + "]";
            }

            cadena = cadena + "\n";
        }

        for(i = 0; i < vertices; ++i) {
            for(j = 0; j < vertices; ++j) {
                if (matrizAdyacencia[i][j] != 100000000L && i != j) {
                    if (caminos[i][j].equals("")) {
                        caminitos = caminitos + "De (" + (i + 1) + "--->" + (j + 1) + ") Irse Por...(" + (i + 1) + "," + (j + 1) + ")\n";
                    } else {
                        caminitos = caminitos + "De (" + (i + 1) + "--->" + (j + 1) + ") Irse Por...(" + (i + 1) + "," + caminos[i][j] + "," + (j + 1) + ")\n";
                    }
                }
            }
        }

        return "La Matriz de Caminos Más Cortos entre los diferentes vértices es:\n" + cadena + "\nLos diferentes caminos más cortos entre vértices son:\n" + caminitos;
    }

    public String caminosR(int i, int k, String[][] caminosAuxiliares, String caminoRecorrido) {
        if (caminosAuxiliares[i][k].equals("")) {
            return "";
        } else {
            caminoRecorrido = caminoRecorrido + this.caminosR(i, Integer.parseInt(caminosAuxiliares[i][k].toString()), caminosAuxiliares, caminoRecorrido) + (Integer.parseInt(caminosAuxiliares[i][k].toString()) + 1) + ", ";
            return caminoRecorrido;
        }
    }
}