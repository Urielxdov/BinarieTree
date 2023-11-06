/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.arboles3;

/**
 *
 * @author Zomber
 */
public class Arboles3 {

    public static void main(String[] args) {
        ArbolBinarioCoreccion arbol = new ArbolBinarioCoreccion();
        
        for (int i = 1; i <= 15; i++) {
            arbol.add(i);
        }
        System.out.println(arbol.nivelArbol());        
    }
}
