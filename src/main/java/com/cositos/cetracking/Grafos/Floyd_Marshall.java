//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.cositos.cetracking.Grafos;

public class Floyd_Marshall {
    public Floyd_Marshall() {
    }

    public static void main(String[] args) {
        long[][] matrizA = new long[][]{{0L, 3L, 4L, 999999999L, 8L, 999999999L}, {999999999L, 0L, 999999999L, 999999999L, 5L, 999999999L}, {999999999L, 999999999L, 0L, 999999999L, 3L, 999999999L}, {999999999L, 999999999L, 999999999L, 0L, 999999999L, 999999999L}, {999999999L, 999999999L, 999999999L, 7L, 0L, 3L}, {999999999L, 999999999L, 999999999L, 2L, 999999999L, 0L}};
        CaminosMinimos ruta = new CaminosMinimos();
        System.out.println(ruta.algoritmoFloyd(matrizA));
    }
}
