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

        arbol.eleminarNodo(16);

        System.out.println("nodo " + arbol.buscarNodo(7));

        System.out.println("raiz: " + arbol.raiz());


        arbol.eleminarNodo(4);
        System.out.println("raiz: " + arbol.raiz());

        arbol.eleminarNodo(7);
        System.out.println("raiz: " + arbol.raiz());
        arbol.eleminarNodo(2);
        System.out.println(arbol.FE(arbol.getRaiz()));
        System.out.println("nivel: " + arbol.altura());
        System.out.println(arbol);
        System.out.println("Comprobando la raiz " + arbol.getRaiz() + " izquierda " + arbol.getRaiz().izq + " derecha " + arbol.getRaiz().der);
        System.out.println("Izquierda ");
        arbol.imprimirIzquierda();
    }

}
