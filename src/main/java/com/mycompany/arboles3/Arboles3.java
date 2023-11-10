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
        ArbolAVL<Integer> arbol = new ArbolAVL<>();
        
        for (int i = 1; i <= 10; i++) {
            arbol.add(i);
        }

        arbol.eleminarNodo(8);
        System.out.println(arbol.buscarNodo(30));
        System.out.println("raiz: " + arbol.raiz());
        System.out.println("nivel: " + arbol.altura());
        System.out.println(arbol);
        System.out.println("Comprobando la raiz " + arbol.getRaiz() + " izquierda " + arbol.getRaiz().izq + " derecha " + arbol.getRaiz().der);
       
    }

}
