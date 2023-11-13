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
        
        arbol.add(40);
        arbol.add(30);
        arbol.add(50);
        arbol.add(20);
        arbol.add(35);
        arbol.add(45);
        arbol.add(55);
        arbol.add(10);
        arbol.add(25);
        arbol.add(32);
        arbol.add(42);
        arbol.add(5);
        arbol.eleminarNodo(55);
        // arbol.eleminarNodo(32);
        // arbol.eleminarNodo(40);
        // arbol.eleminarNodo(30);

        System.out.println(arbol.getRaiz());
    }

}
