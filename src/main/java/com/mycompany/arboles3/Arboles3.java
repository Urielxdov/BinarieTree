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
        ArbolAVLCorreccion<Integer> arbol = new ArbolAVLCorreccion<>();
        
        for (int i = 1; i <= 10; i++) {
            arbol.add(i);
        }



        System.out.println("raiz: " + arbol.getRaiz());


        arbol.eleminarNodo(4);
        System.out.println("raiz: " + arbol.getRaiz());

        arbol.eleminarNodo(7);
        System.out.println("raiz: " + arbol.getRaiz());
        System.out.println(arbol.FE(arbol.getRaiz()));
        System.out.println("nivel: " + arbol.altura());
        System.out.println(arbol);
        System.out.println("Comprobando la raiz " + arbol.getRaiz() + " izquierda " + arbol.getRaiz().izq + " derecha " + arbol.getRaiz().der);
        System.out.println("Izquierda ");
        arbol.imprimirIzquierda();
    }

}
